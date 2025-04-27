package cainongmod.cards;


import cainongmod.helpers.ModHelper;import static cainongmod.cainongmod.getResourcePath;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.android.mods.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlickCoinEffect;

import static cainongmod.enums.CardColorEnum.CAINONG_CARD;

public class JoeyWheelerTheSpire extends CustomCard {
    public static final String ID = ModHelper.MakePath("JoeyWheelerTheSpire");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    // private static final String NAME = "打击";
    private static final String NAME = CARD_STRINGS.NAME; // 读取本地化的名字
    private static final String IMG_PATH ="cards/JoeyWheelerTheSpire.png";
    private static final int COST = 1;
    // private static final String DESCRIPTION = "造成 !D! 点伤害。";
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION; // 读取本地化的描述
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = CAINONG_CARD;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public JoeyWheelerTheSpire() {
        super(ID, NAME, getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 4;
        this.exhaust = false;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(2);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < this.magicNumber; i++) {
            if (AbstractDungeon.cardRandomRng.random(1) == 0) {
                this.addToBot(new VFXAction(new FlickCoinEffect(p.hb.cX, p.hb.cY, p.hb.cX, p.hb.cY), 0.3F));
                this.addToBot(new GainEnergyAction(1));
            }else {
                m = AbstractDungeon.getRandomMonster();
                this.addToBot(new VFXAction(new FlickCoinEffect(p.hb.cX, p.hb.cY, m.hb.cX, m.hb.cY), 0.3F));
            }
        }
        if (AbstractDungeon.cardRandomRng.random(1) == 0) {
            this.exhaust = true;
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new JoeyWheelerTheSpire();
    }
}
