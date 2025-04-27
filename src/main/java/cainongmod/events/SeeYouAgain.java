package cainongmod.events;

import CaiNongMod.helpers.ModHelper;
import CaiNongMod.relics.NeowsEnjoying;
import basemod.abstracts.events.PhasedEvent;
import basemod.abstracts.events.phases.TextPhase;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class SeeYouAgain extends PhasedEvent {
    public static final String ID = ModHelper.MakePath("SeeYouAgain");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private static final String title = eventStrings.NAME;
    private static final String imgUrl = "CaiNongModResources/img/events/SeeYouAgain.png";
    private static final String leave = "leave";
    private static final String start = "start";

    public SeeYouAgain() {
        super(ID, title, imgUrl);
        registerPhase(start, new TextPhase(DESCRIPTIONS[0])
                .addOption(OPTIONS[0], new NeowsEnjoying(), this::receive)
                .addOption(OPTIONS[1], this::exchange)
                .addOption(OPTIONS[2], Integer -> openMap()));
        registerPhase(leave, new TextPhase(DESCRIPTIONS[1])
                .addOption(OPTIONS[2], Integer -> openMap()));
        transitionKey(start);
    }

    private void receive(Integer integer) {
        AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2), new NeowsEnjoying());
        transitionKey(leave);
    }

    private void exchange(Integer integer) {
        AbstractDungeon.player.loseRelic(AbstractDungeon.player.relics.get(0).relicId);
        AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2), AbstractDungeon.returnRandomRelic(AbstractRelic.RelicTier.BOSS));
        transitionKey(leave);
    }
}
