package cainongmod.helpers;

import CaiNongMod.Powers.HuiEyePower;
import CaiNongMod.Powers.NongAbacusPower;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class OnDrawCardActionPowerPatch {
    @SpirePatch(clz = DrawCardAction.class, method = "endActionWithFollowUp")
    public static class DrawCardAction1 {
        public static void Postfix(DrawCardAction __instance) {
            //农盘
            AbstractPlayer p = AbstractDungeon.player;
            if (p.hasPower(NongAbacusPower.POWER_ID) &&!(p.drawPile.isEmpty() && p.discardPile.isEmpty())) {
                ((NongAbacusPower) AbstractDungeon.player.getPower(NongAbacusPower.POWER_ID)).onDrawCardAction();
            }
            //辉眼
            ArrayList<AbstractMonster> monsters = AbstractDungeon.getCurrRoom().monsters.monsters;
            for (AbstractMonster m : monsters) {
                if (m.hasPower(HuiEyePower.POWER_ID) &&!(p.drawPile.isEmpty() && p.discardPile.isEmpty())) {
                    ((HuiEyePower) m.getPower(HuiEyePower.POWER_ID)).onDrawCardAction();
                }
            }
        }
    }
}