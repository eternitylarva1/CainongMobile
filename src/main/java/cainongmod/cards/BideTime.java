package cainongmod.cards;


import cainongmod.Powers.BideTimePower;
import cainongmod.helpers.ModHelper;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.android.mods.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static cainongmod.cainongmod.getResourcePath;
import static cainongmod.enums.CardColorEnum.CAINONG_CARD;

public class BideTime extends CustomCard {
    public static final String ID = ModHelper.MakePath("BideTime");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    // private static final String NAME = "打击";
    private static final String NAME = CARD_STRINGS.NAME; // 读取本地化的名字
    private static final String IMG_PATH ="cards/BideTime.png";
    private static final int COST = 1;
    // private static final String DESCRIPTION = "造成 !D! 点伤害。";
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION; // 读取本地化的描述
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = CAINONG_CARD;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static boolean isUseAttack = false;

    public BideTime() {
        super(ID, NAME, getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.selfRetain = true;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(0);
        }
    }

    public void triggerOnCardPlayed(AbstractCard c) {
        if (c.type == CardType.ATTACK) {
            isUseAttack = true;
        }
    }

    public void triggerOnGlowCheck() {
        if (!isUseAttack) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return !isUseAttack;
    }

    public void atTurnStart() {
        isUseAttack = false;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new PressEndTurnButtonAction());
        if (!isUseAttack) {
            this.addToBot(new ApplyPowerAction(p, p, new BideTimePower(p, 1)));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new BideTime();
    }
}
