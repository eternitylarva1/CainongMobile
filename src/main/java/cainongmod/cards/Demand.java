package cainongmod.cards;

import cainongmod.Powers.BadSeedPower;
import cainongmod.Powers.DoNothingPower;
import cainongmod.Powers.EpilepsyPower;
import cainongmod.Powers.GoodSeedPower;
import cainongmod.helpers.ModHelper;
import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.android.mods.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import com.megacrit.cardcrawl.vfx.SpotlightPlayerEffect;

import java.util.ArrayList;

import static cainongmod.cainongmod.getResourcePath;
import static cainongmod.enums.CardColorEnum.CAINONG_CARD;

public class Demand extends CustomCard {
    public static final String ID = ModHelper.MakePath("Demand");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    // private static final String NAME = "打击";
    private static final String NAME = CARD_STRINGS.NAME; // 读取本地化的名字
    private static final String IMG_PATH ="cards/Demand.png";
    private static final int COST = 2;
    // private static final String DESCRIPTION = "造成 !D! 点伤害。";
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION; // 读取本地化的描述
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = CAINONG_CARD;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;

    public Demand() {
        super(ID, NAME, getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 50;
        this.exhaust = true;
        this.isEthereal = true;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(1);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!p.hasPower(GoodSeedPower.POWER_ID) || !p.hasPower(BadSeedPower.POWER_ID) || !p.hasPower(DoNothingPower.POWER_ID) || !p.hasPower(EpilepsyPower.POWER_ID)) {
            AbstractDungeon.effectList.add(new RainingGoldEffect(this.magicNumber, true));
            AbstractDungeon.effectsQueue.add(new SpotlightPlayerEffect());
            this.addToBot(new GainGoldAction(this.magicNumber));
            ArrayList<AbstractCard> stanceChoices = new ArrayList<>();
            if (!p.hasPower(GoodSeedPower.POWER_ID)) {
                stanceChoices.add(new GoodSeed());
            }
            if (!p.hasPower(BadSeedPower.POWER_ID)) {
                stanceChoices.add(new BadSeed());
            }
            if (!p.hasPower(DoNothingPower.POWER_ID)) {
                stanceChoices.add(new DoNothing());
            }
            if (!p.hasPower(EpilepsyPower.POWER_ID)) {
                stanceChoices.add(new Epilepsy());
            }
            this.addToBot(new ChooseOneAction(stanceChoices));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Demand();
    }
}
