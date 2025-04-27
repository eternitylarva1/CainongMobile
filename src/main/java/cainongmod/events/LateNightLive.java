package cainongmod.events;

import CaiNongMod.helpers.ModHelper;
import basemod.abstracts.events.PhasedEvent;
import basemod.abstracts.events.phases.TextPhase;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.vfx.GainGoldTextEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

import java.util.ArrayList;

public class LateNightLive extends PhasedEvent {
    public static final String ID = ModHelper.MakePath("LateNightLive");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private static final String title = eventStrings.NAME;
    private static final String imgUrl = "CaiNongModResources/img/events/LateNightLive.png";
    private static final String leave = "leave";
    private static final String start = "start";
    private boolean canUpgrade = false;
    private final int heal = AbstractDungeon.player.maxHealth * 3 / 10;
    private final int maxHpLoss = AbstractDungeon.player.maxHealth / 10;
    private int damage = 6;
    private int stage = 1;
    private TextPhase.OptionInfo update;
    private TextPhase.OptionInfo win;

    public LateNightLive() {
        super(ID, title, imgUrl);
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c.canUpgrade()) {
                canUpgrade = true;
                break;
            }
        }
        update = new TextPhase.OptionInfo(canUpgrade ? OPTIONS[2] + this.damage + OPTIONS[3] : OPTIONS[6]);
        update.enabledCondition(() -> canUpgrade);

        AbstractPlayer p = AbstractDungeon.player;
        boolean hasSmoke = p.hasAnyPotions() && p.hasPotion("SmokeBomb");
        win = new TextPhase.OptionInfo(hasSmoke ? OPTIONS[8] : OPTIONS[9]);
        win.enabledCondition(() -> hasSmoke);

        registerPhase(start, new TextPhase(DESCRIPTIONS[0])
                .addOption(OPTIONS[0] + this.heal + OPTIONS[1] + this.maxHpLoss + OPTIONS[10], Integer -> healing())
                .addOption(OPTIONS[4], integer -> transitionKey("continue")));
        registerPhase("continue", new TextPhase(DESCRIPTIONS[2] + STAGE())
                .addOption(update, this::upgrade)
                .addOption(OPTIONS[5], Integer -> transitionKey("die")));
        registerPhase(leave, new TextPhase(DESCRIPTIONS[1])
                .addOption(OPTIONS[7], Integer -> openMap()));
        registerPhase("die", new TextPhase(DESCRIPTIONS[stage + 2])
                .addOption(OPTIONS[7], Integer -> openMap()));
        registerPhase("win", new TextPhase(DESCRIPTIONS[7])
                .addOption(OPTIONS[7], Integer -> openMap()));
        transitionKey(start);
    }

    private void upgrade(Integer integer) {
        ArrayList<AbstractCard> cards = new ArrayList<>(AbstractDungeon.player.masterDeck.group);
        cards.removeIf(c -> !c.canUpgrade());
        if (!cards.isEmpty()) {
            AbstractCard c = cards.get(AbstractDungeon.miscRng.random(cards.size() - 1));

            AbstractDungeon.player.damage(new DamageInfo(AbstractDungeon.player, this.damage));
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, AbstractGameAction.AttackEffect.LIGHTNING));

            c.upgrade();
            AbstractDungeon.player.bottledCardUpgradeCheck(c);
            AbstractDungeon.effectList.add(new ShowCardBrieflyEffect(c.makeStatEquivalentCopy()));
        }
        this.damage++;
        this.stage++;
        canUpgrade = cards.size() > 1;
        if (stage < 4) {
            update = new TextPhase.OptionInfo(canUpgrade ? OPTIONS[2] + this.damage + OPTIONS[3] : OPTIONS[6]);
            update.enabledCondition(() -> canUpgrade);
            registerPhase("continue", new TextPhase(DESCRIPTIONS[2] + STAGE())
                    .addOption(update, this::upgrade)
                    .addOption(OPTIONS[5], Integer -> transitionKey("die")));
        }else {
            registerPhase("continue", new TextPhase(DESCRIPTIONS[2] + STAGE())
                    .addOption(win, Integer -> gainGold())
                    .addOption(OPTIONS[5], Integer -> transitionKey("die")));
        }
        registerPhase("die", new TextPhase(DESCRIPTIONS[stage + 2])
                .addOption(OPTIONS[7], Integer -> openMap()));
        transitionKey("continue");
    }

    private String STAGE() {
        switch (stage) {
            case 1: return "塔底";
            case 2: return "城市";
            case 3: return "深处";
            default: return "终幕";
        }
    }

    private void gainGold() {
        AbstractDungeon.effectList.add(new GainGoldTextEffect(300));
        AbstractDungeon.player.gold += 300;
        ArrayList<AbstractPotion> potions = AbstractDungeon.player.potions;
        for (AbstractPotion potion : potions) {
            if (potion.ID.equals("SmokeBomb")) {
                AbstractDungeon.player.removePotion(potion);
                break;
            }
        }
        transitionKey("win");
    }

    private void healing() {
        AbstractDungeon.player.heal(heal);
        AbstractDungeon.player.decreaseMaxHealth(maxHpLoss);;
        transitionKey(leave);
    }
}
