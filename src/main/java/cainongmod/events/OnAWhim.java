package cainongmod.events;

import CaiNongMod.helpers.ModHelper;
import CaiNongMod.monsters.ChillGuy;
import basemod.abstracts.events.PhasedEvent;
import basemod.abstracts.events.phases.CombatPhase;
import basemod.abstracts.events.phases.TextPhase;
import com.megacrit.cardcrawl.cards.curses.Normality;
import com.megacrit.cardcrawl.cards.curses.Regret;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Circlet;
import com.megacrit.cardcrawl.relics.FrozenEye;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.GainGoldTextEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import java.util.ArrayList;

public class OnAWhim extends PhasedEvent {
    public static final String ID = ModHelper.MakePath("OnAWhim");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private static final String title = eventStrings.NAME;
    private static final String imgUrl = "CaiNongModResources/img/events/OnAWhim.png";
    private static final String leave = "leave";
    private static final String start = "start";
    private static final String fight = "fight";

    public OnAWhim() {
        super(ID, title, imgUrl);
        registerPhase(start, new TextPhase(DESCRIPTIONS[0])
                .addOption(OPTIONS[0], this::give1)
                .addOption(OPTIONS[1], this::give2)
                .addOption(OPTIONS[2], this::fight));
        registerPhase(fight, new CombatPhase(ModHelper.MakePath("ChillGuy"))
                .addRewards(true, (room)->room.addRelicToRewards(new FrozenEye())));
        registerPhase(leave, new TextPhase(DESCRIPTIONS[3])
                .addOption(OPTIONS[3], Integer -> openMap()));
        transitionKey(start);
    }

    private void give1(Integer integer) {
        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Regret(), (float) Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
        AbstractDungeon.effectList.add(new GainGoldTextEffect(0x080));
        AbstractDungeon.player.gainGold(0x080);

        registerPhase(leave, new TextPhase(DESCRIPTIONS[1])
                .addOption(OPTIONS[3], Integer -> openMap()));
        transitionKey(leave);
    }

    private void give2(Integer integer) {
        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Normality(), (float) Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
        ArrayList<AbstractRelic> relics = RelicLibrary.shopList;
        relics.removeIf(relic -> AbstractDungeon.player.hasRelic(relic.relicId));
        AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2), relics.isEmpty() ? new Circlet() : relics.get(AbstractDungeon.miscRng.random(relics.size()-1)));

        registerPhase(leave, new TextPhase(DESCRIPTIONS[2])
                .addOption(OPTIONS[3], Integer -> openMap()));
        transitionKey(leave);
    }

    private void fight(Integer integer) {
        ChillGuy.slNum++;
        this.transitionKey(fight);
        addGoldToRewards(11);
        addGoldToRewards(23);
        addGoldToRewards(45);
    }

    public void addGoldToRewards(int gold) {
        AbstractRoom room = AbstractDungeon.getCurrRoom();
        room.rewards.add(new RewardItem(gold));
    }
}
