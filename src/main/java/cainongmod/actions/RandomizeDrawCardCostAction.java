package cainongmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class RandomizeDrawCardCostAction extends AbstractGameAction {

    public RandomizeDrawCardCostAction() {
        this.duration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {

        for (AbstractCard c : DrawCardAction.drawnCards) {
            if (c.cost >= 0) {
                int newCost = AbstractDungeon.cardRandomRng.random(3);
                if (c.cost != newCost) {
                    c.cost = newCost;
                    c.costForTurn = c.cost;
                    c.isCostModified = true;
                }
            }
        }
        this.isDone = true;
    }
}
