package cainongmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class HuiShiAction extends AbstractGameAction {
    private int damage;
    private AbstractMonster m;

    public HuiShiAction(int damage,AbstractMonster m) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.damage = damage;
        this.m = m;
    }

    @Override
    public void update() {
        for (AbstractCard c : DrawCardAction.drawnCards) {
            if (c.type == AbstractCard.CardType.SKILL) {
                this.addToBot(new DrawCardAction(1));
                this.addToBot(new DamageAction(m, new DamageInfo(AbstractDungeon.player, damage, DamageInfo.DamageType.NORMAL), AttackEffect.SLASH_DIAGONAL));
                this.addToBot(new HuiShiAction(damage, m));
            }
        }
        this.isDone = true;
    }
}
