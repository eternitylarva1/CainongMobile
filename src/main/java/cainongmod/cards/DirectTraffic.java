package cainongmod.cards;


import cainongmod.helpers.ModHelper;import static cainongmod.cainongmod.getResourcePath;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.android.mods.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import static cainongmod.enums.CardColorEnum.CAINONG_CARD;

public class DirectTraffic extends CustomCard {
    public static final String ID = ModHelper.MakePath("DirectTraffic");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    // private static final String NAME = "打击";
    private static final String NAME = CARD_STRINGS.NAME; // 读取本地化的名字
    private static final String IMG_PATH = "cainongmodResources/img/cards/DirectTraffic.png";
    private static final int COST = 1;
    // private static final String DESCRIPTION = "造成 !D! 点伤害。";
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION; // 读取本地化的描述
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = CAINONG_CARD;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public DirectTraffic() {
        super(ID, NAME, getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 3;
        this.block = this.baseBlock = 6;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(2);
            this.upgradeBlock(2);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new GainBlockAction(p, this.block));
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (mo.hasPower(NongBaiPower.POWER_ID)) {
                this.addToBot(new ApplyPowerAction(mo, p, new WeakPower(mo, this.magicNumber, false)));
                this.addToBot(new ApplyPowerAction(mo, p, new NongHeiPower(mo, 1), this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
            }else if (mo.hasPower(NongHeiPower.POWER_ID)) {
                this.addToBot(new ApplyPowerAction(mo, p, new VulnerablePower(mo, this.magicNumber, false)));
                this.addToBot(new ApplyPowerAction(mo, p, new NongBaiPower(mo), this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));

            }
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new DirectTraffic();
    }
}
