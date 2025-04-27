package cainongmod.helpers;

import basemod.BaseMod;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.EndTurnAction;

public class OnTurnStartAndEndActionPatch {
    @SpirePatch(clz = BaseMod.class, method = "publishOnPlayerTurnStart")
    public static class TurnStart {
        public static void Postfix() {
            ModHelper.isUseRush = false;
            ModHelper.naiLong = true;
        }
    }

    @SpirePatch(clz = EndTurnAction.class, method = "update")
    public static class TurnEnd {
        public static void Postfix() {
            if (ModHelper.isUseRush) {
                ModHelper.RushNum++;
            }else {
                ModHelper.RushNum = 1;
            }
        }
    }

    @SpirePatch(clz = BaseMod.class, method = "publishStartBattle")
    public static class BattleStart {
        public static void Postfix() {
            ModHelper.clearInsideCards();
            ModHelper.RushNum = 1;
        }
    }
}