package cainongmod.cards;


import cainongmod.helpers.ModHelper;import static cainongmod.cainongmod.getResourcePath;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.android.mods.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.MasterRealityPower;

import static cainongmod.enums.CardColorEnum.CAINONG_CARD;

public class LookForUncleOfChild extends CustomCard {
    public static final String ID = ModHelper.MakePath("LookForUncleOfChild");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    // private static final String NAME = "打击";
    private static final String NAME = CARD_STRINGS.NAME; // 读取本地化的名字
    private static final String IMG_PATH = "cainongmodResources/img/cards/LookForUncleOfChild.png";
    private static final int COST = 2;
    // private static final String DESCRIPTION = "造成 !D! 点伤害。";
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION; // 读取本地化的描述
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = CAINONG_CARD;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public LookForUncleOfChild() {
        super(ID, NAME, getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 1;
        this.exhaust = true;
        AbstractCard card = new UncleOfChild().makeCopy();
        if (this.upgraded) card.upgrade();
        this.cardsToPreview = card;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();

            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.upgraded || p.hasPower(MasterRealityPower.POWER_ID)) {
            AbstractCard c = new UncleOfChild().makeCopy();
            c.upgrade();
            this.addToBot(new MakeTempCardInDrawPileAction(c, this.magicNumber, false,false,false));
        }else {
            this.addToBot(new MakeTempCardInDrawPileAction(new UncleOfChild().makeCopy(), this.magicNumber, false,false,false));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new LookForUncleOfChild();
    }
}
