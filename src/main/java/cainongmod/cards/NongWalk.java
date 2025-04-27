package cainongmod.cards;


import cainongmod.helpers.ModHelper;import static cainongmod.cainongmod.getResourcePath;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.android.mods.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.Iterator;

import static cainongmod.enums.CardColorEnum.CAINONG_CARD;

public class NongWalk extends CustomCard {
    public static final String ID = ModHelper.MakePath("NongWalk");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    // private static final String NAME = "打击";
    private static final String NAME = CARD_STRINGS.NAME; // 读取本地化的名字
    private static final String IMG_PATH = "cainongmodResources/img/cards/NongWalk.png";
    private static final int COST = 1;
    // private static final String DESCRIPTION = "造成 !D! 点伤害。";
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION; // 读取本地化的描述
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = CAINONG_CARD;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public NongWalk() {
        super(ID, NAME, getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.damage = this.baseDamage = 8;
        this.magicNumber = this.baseMagicNumber = 5;

    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(3);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), ModHelper.AtkSound(this)));
        this.addToBot(new AbstractGameAction() {
            private AbstractCard card = NongWalk.this;
            public void update() {
                AbstractCard var10000 = this.card;
                var10000.baseDamage += NongWalk.this.baseMagicNumber;
                this.card.applyPowers();
                Iterator<AbstractCard> var1 = AbstractDungeon.player.discardPile.group.iterator();

                AbstractCard c;
                while(var1.hasNext()) {
                    c = var1.next();
                    if (c instanceof NongWalk) {
                        c.baseDamage += NongWalk.this.baseMagicNumber;
                        c.applyPowers();
                    }
                }

                var1 = AbstractDungeon.player.drawPile.group.iterator();

                while(var1.hasNext()) {
                    c = var1.next();
                    if (c instanceof NongWalk) {
                        c.baseDamage += NongWalk.this.baseMagicNumber;
                        c.applyPowers();
                    }
                }

                var1 = AbstractDungeon.player.hand.group.iterator();

                while(var1.hasNext()) {
                    c = var1.next();
                    if (c instanceof NongWalk) {
                        c.baseDamage += NongWalk.this.baseMagicNumber;
                        c.applyPowers();
                    }
                }

                this.isDone = true;
            }
        });
    }
}