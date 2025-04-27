package cainongmod.cards;


import cainongmod.helpers.ModHelper;import static cainongmod.cainongmod.getResourcePath;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.android.mods.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static cainongmod.enums.CardColorEnum.CAINONG_CARD;

public class AreYouTwoLoong extends CustomCard {
    public static final String ID = ModHelper.MakePath("AreYouTwoLoong");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    // private static final String NAME = "打击";
    private static final String NAME = CARD_STRINGS.NAME; // 读取本地化的名字
    private static final String IMG_PATH ="cards/AreYouTwoLoong.png";
    private static final int COST = 1;
    // private static final String DESCRIPTION = "造成 !D! 点伤害。";
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION; // 读取本地化的描述
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = CAINONG_CARD;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;

    public AreYouTwoLoong() {
        super(ID, NAME, getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.damage = this.baseDamage = 6;
        this.isMultiDamage = true;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(2);
        }
    }

    private boolean isUseAttack = false;
    public void triggerOnOtherCardPlayed(AbstractCard c) {
        if (c.type == CardType.ATTACK) {
            if (!isUseAttack) {
                this.baseDamage = this.baseDamage * 2;
                isUseAttack = true;
            }
        }else {
            if (isUseAttack) {
                this.baseDamage = this.baseDamage / 2;
                isUseAttack = false;
            }
        }

        this.freeToPlayOnce = c.type == CardType.SKILL;

        this.returnToHand = c.type == CardType.POWER;
    }

    public void triggerOnGlowCheck() {
        if (returnToHand) {
            this.glowColor = AbstractCard.GREEN_BORDER_GLOW_COLOR.cpy();
        }else if (isUseAttack){
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_HEAVY));
    }

    public AbstractCard makeCopy () {
        return new AreYouTwoLoong();
    }
}