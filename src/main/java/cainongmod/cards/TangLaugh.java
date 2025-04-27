package cainongmod.cards;

import CaiNongMod.Powers.NongHeiPower;
import CaiNongMod.helpers.ModHelper;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static CaiNongMod.Characters.CaiNong.Enums.CAINONG_CARD;

public class TangLaugh extends CustomCard {
    public static final String ID = ModHelper.MakePath("TangLaugh");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    // private static final String NAME = "打击";
    private static final String NAME = CARD_STRINGS.NAME; // 读取本地化的名字
    private static final String IMG_PATH = "CaiNongModResources/img/cards/TangLaugh.png";
    private static final int COST = 1;
    // private static final String DESCRIPTION = "造成 !D! 点伤害。";
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION; // 读取本地化的描述
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = CAINONG_CARD;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public TangLaugh() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 2;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToTop(new ApplyPowerAction(m, p, new NongHeiPower(m, 1)));
        this.addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                int drawNum = TangLaugh.this.magicNumber;
                for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
                    if (mo.hasPower(NongHeiPower.POWER_ID)) {
                        drawNum += mo.getPower(NongHeiPower.POWER_ID).amount;
                    }
                }
                this.addToTop(new DrawCardAction(drawNum));
                this.isDone = true;
            }
        });

    }

    @Override
    public AbstractCard makeCopy() {
        return new TangLaugh();
    }
}
