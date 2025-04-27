package cainongmod.actions;

import CaiNongMod.helpers.ModHelper;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class GetInsideAction extends AbstractGameAction {
    private AbstractPlayer p;
    private boolean anyNumber;
    private boolean canPickZero;
    public static int numPlayed;
    private boolean play = false;


    public GetInsideAction(int amount, boolean anyNumber, boolean canPickZero) {
        this.anyNumber = anyNumber;
        this.p = AbstractDungeon.player;
        this.canPickZero = canPickZero;
        this.amount = amount;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.EXHAUST;
    }

    @Override
    public void update() {
        if (ModHelper.getCards_Inside().isEmpty()) {
            if (this.duration == this.startDuration) {
                if (this.p.hand.isEmpty()) {
                    this.isDone = true;
                    return;
                }

                numPlayed = this.amount;
                if (this.p.hand.size() < this.amount) {
                    this.amount = this.p.hand.size();
                }
                AbstractDungeon.handCardSelectScreen.open("搞里头", this.amount, this.anyNumber, this.canPickZero);
                this.tickDuration();
                return;

            }

            if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {

                for (AbstractCard card : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                    playCards(card);
                }

                CardCrawlGame.dungeon.checkForPactAchievement();
                AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            }
            this.tickDuration();
        }else {
            ArrayList<AbstractCard> cards_Inside = ModHelper.getCards_Inside();
            for (AbstractCard card : cards_Inside) {
                AbstractDungeon.player.limbo.group.add(card);
                card.current_y = -200.0F * Settings.scale;
                card.target_x = (float) Settings.WIDTH / 2.0F + 200.0F * Settings.xScale;
                card.target_y = (float) Settings.HEIGHT / 2.0F;
                card.targetAngle = 0.0F;
                card.lighten(false);
                card.drawScale = 0.12F;
                card.targetDrawScale = 0.75F;
                card.applyPowers();
                this.addToTop(new NewQueueCardAction(card, true, false, true));
                this.addToTop(new UnlimboAction(card));
                this.addToTop(new WaitAction(Settings.ACTION_DUR_FASTER));
            }
            ModHelper.clearInsideCards();
        }
        this.isDone = true;
    }

    private void playCards(AbstractCard card) {
        ModHelper.addInsideCards(card);
    }
}
