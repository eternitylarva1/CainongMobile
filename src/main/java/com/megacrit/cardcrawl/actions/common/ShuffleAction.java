//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.actions.common;

import cainongmod.Powers.NongAbacusPower;
import cainongmod.Powers.NongdialPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.android.mods.utils.ReflectionHacks;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.Iterator;

public class ShuffleAction extends AbstractGameAction {
    private CardGroup group;
    private boolean triggerRelics;

    public ShuffleAction(CardGroup theGroup) {
        this(theGroup, false);
    }

    public ShuffleAction(CardGroup theGroup, boolean trigger) {
        this.setValues((AbstractCreature)null, (AbstractCreature)null, 0);
        this.duration = 0.0F;
        this.actionType = ActionType.SHUFFLE;
        this.group = theGroup;
        this.triggerRelics = trigger;
    }

    public void update() {
        if (this.triggerRelics) {
            Iterator var1 = AbstractDungeon.player.relics.iterator();

            while(var1.hasNext()) {
                AbstractRelic r = (AbstractRelic)var1.next();
                r.onShuffle();
            }
        }

        this.group.shuffle();
        this.isDone = true;
        boolean b = ((Boolean) ReflectionHacks.getPrivate(this, ShuffleAction.class, "triggerRelics")).booleanValue();
        if (b) {
            if (AbstractDungeon.player.hasPower(NongdialPower.POWER_ID)) {
                ((NongdialPower) AbstractDungeon.player.getPower(NongdialPower.POWER_ID)).onShuffle();
            }
            if (AbstractDungeon.player.hasPower(NongAbacusPower.POWER_ID)) {
                ((NongAbacusPower) AbstractDungeon.player.getPower(NongAbacusPower.POWER_ID)).onShuffle();
            }
        }
    }
}
