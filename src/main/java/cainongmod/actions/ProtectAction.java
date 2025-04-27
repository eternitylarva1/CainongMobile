package cainongmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ProtectAction extends AbstractGameAction {
    private AbstractPlayer p;
    private boolean isRandom;
    private boolean anyNumber;
    private boolean canPickZero;
    public static int numPlayed;

    public ProtectAction(int amount, boolean isRandom, boolean anyNumber, boolean canPickZero) {
        this.anyNumber = anyNumber;
        this.p = AbstractDungeon.player;
        this.canPickZero = canPickZero;
        this.isRandom = isRandom;
        this.amount = amount;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.EXHAUST;
    }

    @Override
    public void update() {
        if (this.duration == this.startDuration) {
            if (this.p.hand.isEmpty()) {
                this.isDone = true;
                return;
            }

            int i;
            if (!this.anyNumber && this.p.hand.size() <= this.amount) {
                this.amount = this.p.hand.size();
                numPlayed = this.amount;
                i = this.p.hand.size();

                for(int j = 0; j < i; ++j) {
                    AbstractCard card = this.p.hand.getTopCard();
                    if (card.cost >= 0) card.updateCost(1);
                }
                CardCrawlGame.dungeon.checkForPactAchievement();
                this.isDone = true;
                return;
            }

            if (!this.isRandom) {
                numPlayed = this.amount;
                AbstractDungeon.handCardSelectScreen.open("保护", this.amount, this.anyNumber, this.canPickZero);
                this.tickDuration();
                return;
            }

            for(i = 0; i < this.amount; ++i) {
                AbstractCard card = AbstractDungeon.player.hand.getRandomCard(true);
                playCards(card);
            }

            CardCrawlGame.dungeon.checkForPactAchievement();
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {

            for (AbstractCard card : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                playCards(card);
            }

            CardCrawlGame.dungeon.checkForPactAchievement();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }

        this.tickDuration();
    }

    private void playCards(AbstractCard card) {
        if (card.cost >= 0) {
            card.updateCost(1);
        }
        this.p.hand.moveToHand(card);
    }
}
