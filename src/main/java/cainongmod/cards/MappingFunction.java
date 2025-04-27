package cainongmod.cards;


import cainongmod.actions.CardToDrawPileAction;
import cainongmod.helpers.ModHelper;import static cainongmod.cainongmod.getResourcePath;
import com.megacrit.cardcrawl.android.mods.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static cainongmod.enums.CardColorEnum.CAINONG_CARD;

public class MappingFunction extends CustomCard {
    public static final String ID = ModHelper.MakePath("MappingFunction");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    // private static final String NAME = "打击";
    private static final String NAME = CARD_STRINGS.NAME; // 读取本地化的名字
    private static final String IMG_PATH = "cainongmodResources/img/cards/MappingFunction.png";
    private static final int COST = 3;
    // private static final String DESCRIPTION = "造成 !D! 点伤害。";
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION; // 读取本地化的描述
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = CAINONG_CARD;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;

    public MappingFunction() {
        super(ID, NAME, getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 3;
        this.exhaust = true;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(2);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new CardToDrawPileAction(p, p ,this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new MappingFunction();
    }
}
