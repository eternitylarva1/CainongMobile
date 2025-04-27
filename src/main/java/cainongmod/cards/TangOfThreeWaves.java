package cainongmod.cards;


import cainongmod.Powers.NongHeiPower;
import cainongmod.helpers.ModHelper;import static cainongmod.cainongmod.getResourcePath;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.android.mods.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import static cainongmod.enums.CardColorEnum.CAINONG_CARD;

public class TangOfThreeWaves extends CustomCard {
    public static final String ID = ModHelper.MakePath("TangOfThreeWaves");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    // private static final String NAME = "打击";
    private static final String NAME = CARD_STRINGS.NAME; // 读取本地化的名字
    private static final String IMG_PATH = "cainongmodResources/img/cards/TangOfThreeWaves.png";
    private static final int COST = 0;
    // private static final String DESCRIPTION = "造成 !D! 点伤害。";
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION; // 读取本地化的描述
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = CAINONG_CARD;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private int useTimes = 0;

    public TangOfThreeWaves() {
        super(ID, NAME, getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 2;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
        }
    }

    public void triggerOnGlowCheck() {
        if (useTimes == 2) {
            this.glowColor = AbstractCard.GREEN_BORDER_GLOW_COLOR.cpy();
        }else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        useTimes++;
        if (useTimes == 3) {
            this.exhaust = true;
        }
        this.addToBot(new ApplyPowerAction(m, p, new NongHeiPower(m, 1), 1, true, AbstractGameAction.AttackEffect.NONE));
        this.addToBot(new ApplyPowerAction(m, p, new VulnerablePower(m, this.magicNumber, false)));
        this.addToBot(new ApplyPowerAction(m, p, new WeakPower(m, this.magicNumber, false)));
    }
}