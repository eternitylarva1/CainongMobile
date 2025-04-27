package cainongmod.cards;


import cainongmod.helpers.ModHelper;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.android.mods.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static cainongmod.cainongmod.getResourcePath;
import static cainongmod.enums.CardColorEnum.CAINONG_CARD;

public class OffItShrug extends CustomCard {
    public static final String ID = ModHelper.MakePath("OffItShrug");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    // private static final String NAME = "打击";
    private static final String NAME = CARD_STRINGS.NAME; // 读取本地化的名字
    private static final String IMG_PATH ="cards/OffItShrug_Skill.png";
    private static final int COST = 1;
    // private static final String DESCRIPTION = "造成 !D! 点伤害。";
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION; // 读取本地化的描述
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = CAINONG_CARD;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    private boolean isUsed = false;

    public OffItShrug() {
        super(ID, NAME, getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.block = this.baseBlock = 8;
        this.damage = this.baseDamage = 9;
        this.magicNumber = this.baseMagicNumber = 1;
    }

    public void triggerOnGlowCheck() {
        if (isUsed) {
            this.glowColor = AbstractCard.GREEN_BORDER_GLOW_COLOR.cpy();
        }else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(3);
            this.upgradeDamage(1);
            if (this.type.equals(CardType.ATTACK)) {
                this.upgradeMagicNumber(1);
            }
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        isUsed = true;
        if (this.type.equals(CardType.SKILL)) {
            this.addToBot(new GainBlockAction(p, this.block));
            this.addToBot(new DrawCardAction(this.magicNumber));
        }else if (this.type.equals(CardType.ATTACK)){
            this.addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), ModHelper.AtkSound(this)));
            this.addToBot(new DrawCardAction(this.magicNumber));
        }
    }

    @Override
    public void onMoveToDiscard() {
        super.onMoveToDiscard();
        if (isUsed) {
            if (this.type.equals(CardType.SKILL)) {
                this.type = CardType.ATTACK;
                this.target = CardTarget.ENEMY;
                this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
                this.initializeDescription();
                this.loadCardImage(getResourcePath("cards/OffItShrug_Attack.png"));
                if (upgraded) {
                    this.upgradeMagicNumber(1);
                }
            } else if (this.type.equals(CardType.ATTACK)) {
                this.type = CardType.SKILL;
                this.target = CardTarget.SELF;
                this.rawDescription = CARD_STRINGS.DESCRIPTION;
                this.initializeDescription();
                this.loadCardImage(getResourcePath("cards/OffItShrug_Skill.png"));
                if (upgraded) {
                    this.upgradeMagicNumber(-1);
                }
            }
        }
        isUsed = false;
    }

    @Override
    public AbstractCard makeCopy() {
        return new OffItShrug();
    }
}
