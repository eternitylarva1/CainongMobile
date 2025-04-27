package cainongmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.Strike_Green;
import com.megacrit.cardcrawl.cards.red.Strike_Red;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ExchangeCardsOfTwoPilesAction extends AbstractGameAction {
    private final AbstractPlayer p;
    private int chooseTimes = 0;
    private AbstractCard c1 = new Strike_Red();
    private AbstractCard c2 = new Strike_Green();

    public ExchangeCardsOfTwoPilesAction(AbstractCreature source) {
        this.setValues(null, source, this.amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FASTER;
        this.p = AbstractDungeon.player;
    }
    
    public void update() {
        if (AbstractDungeon.getCurrRoom().isBattleEnding()) {
            this.isDone = true;
        } else {
            if (this.p.discardPile.isEmpty() || this.p.drawPile.isEmpty()) {
                this.isDone = true;
                return;
            }

            if (this.duration == Settings.ACTION_DUR_FASTER) {
                switch (chooseTimes) {
                    case 0: AbstractDungeon.gridSelectScreen.open(this.p.discardPile, 1, "选择一张牌交换", false, false, false, false);
                            chooseTimes++;
                            this.tickDuration();
                            break;

                    case 1: AbstractDungeon.gridSelectScreen.open(this.p.drawPile, 1, "选择一张牌交换", false, false, false, false);
                            chooseTimes++;
                            this.tickDuration();
                            break;
                    default:
                        return;
                }
            }


            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                if (chooseTimes == 1) {
                    for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                        c1 = c;
                    }
                }else {
                    for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                        c2 = c;
                    }
                }

                if (chooseTimes < 2) {
                    this.duration = Settings.ACTION_DUR_FASTER;
                    return;
                }else {

                    this.p.discardPile.removeCard(c1);
                    this.p.hand.moveToDeck(c1, true);
                    this.p.drawPile.removeCard(c2);
                    this.p.hand.moveToDiscardPile(c2);

                    AbstractDungeon.gridSelectScreen.selectedCards.clear();
                    AbstractDungeon.player.hand.refreshHandLayout();
                }
            }

            this.tickDuration();
        }
    }
}
