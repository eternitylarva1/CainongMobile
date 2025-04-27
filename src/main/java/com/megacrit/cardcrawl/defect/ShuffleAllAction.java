//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.defect;

import cainongmod.Powers.NongAbacusPower;
import cainongmod.Powers.NongdialPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.PutOnDeckAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.TipTracker;
import com.megacrit.cardcrawl.localization.TutorialStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.ui.FtueTip;
import com.megacrit.cardcrawl.ui.FtueTip.TipType;

import java.util.Iterator;

public class ShuffleAllAction extends AbstractGameAction {
    private static final TutorialStrings tutorialStrings;
    public static final String[] MSG;
    public static final String[] LABEL;
    private boolean shuffled = false;
    private boolean vfxDone = false;
    private int count = 0;

    public ShuffleAllAction() {
        this.setValues((AbstractCreature)null, (AbstractCreature)null, 0);
        this.actionType = ActionType.SHUFFLE;
        if (!(Boolean)TipTracker.tips.get("SHUFFLE_TIP")) {
            AbstractDungeon.ftue = new FtueTip(LABEL[0], MSG[0], (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F, TipType.SHUFFLE);
            TipTracker.neverShowAgain("SHUFFLE_TIP");
        }

        Iterator var1 = AbstractDungeon.player.relics.iterator();

        while(var1.hasNext()) {
            AbstractRelic r = (AbstractRelic)var1.next();
            r.onShuffle();
        }
        if (AbstractDungeon.player.hasPower(NongdialPower.POWER_ID)) {
            ((NongdialPower) AbstractDungeon.player.getPower(NongdialPower.POWER_ID)).onShuffle();
        }
        if (AbstractDungeon.player.hasPower(NongAbacusPower.POWER_ID)) {
            ((NongAbacusPower) AbstractDungeon.player.getPower(NongAbacusPower.POWER_ID)).onShuffle();
        }
    }

    public void update() {
        if (!this.shuffled) {
            this.shuffled = true;
            AbstractPlayer p = AbstractDungeon.player;
            this.addToTop(new PutOnDeckAction(p, p, 99, true));
            p.discardPile.shuffle(AbstractDungeon.shuffleRng);
        }

        if (!this.vfxDone) {
            Iterator<AbstractCard> c = AbstractDungeon.player.discardPile.group.iterator();
            if (c.hasNext()) {
                ++this.count;
                AbstractCard e = (AbstractCard)c.next();
                c.remove();
                if (this.count < 11) {
                    AbstractDungeon.getCurrRoom().souls.shuffle(e, false);
                } else {
                    AbstractDungeon.getCurrRoom().souls.shuffle(e, true);
                }

                return;
            }

            this.vfxDone = true;
        }

        this.isDone = true;
    }

    static {
        tutorialStrings = CardCrawlGame.languagePack.getTutorialString("Shuffle Tip");
        MSG = tutorialStrings.TEXT;
        LABEL = tutorialStrings.LABEL;
    }
}
