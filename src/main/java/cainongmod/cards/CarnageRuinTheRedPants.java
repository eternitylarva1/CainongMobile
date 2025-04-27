package cainongmod.cards;

import cainongmod.helpers.ModHelper;import static cainongmod.cainongmod.getResourcePath;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.android.mods.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static cainongmod.enums.CardColorEnum.CAINONG_CARD;

public class CarnageRuinTheRedPants extends CustomCard {
    public static final String ID = ModHelper.MakePath("CarnageRuinTheRedPants");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    // private static final String NAME = "打击";
    private static final String NAME = CARD_STRINGS.NAME; // 读取本地化的名字
    private static final String IMG_PATH = "cainongmodResources/img/cards/CarnageRuinTheRedPants.png";
    private static final int COST = 2;
    // private static final String DESCRIPTION = "造成 !D! 点伤害。";
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION; // 读取本地化的描述
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = CAINONG_CARD;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public CarnageRuinTheRedPants() {
        super(ID, NAME, getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.damage = this.baseDamage = 20;
        this.isEthereal = true;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(8);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), ModHelper.AtkSound(this)));
    }

    @Override
    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();

        this.addToTop(new AbstractGameAction() {
            @Override
            public void update() {
                AbstractPlayer p = AbstractDungeon.player;
                for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
                    if (c.type == CardType.ATTACK && c.cost >= 2 && p.hand.size() <= BaseMod.MAX_HAND_SIZE - 1) {
                        p.drawPile.removeCard(c);
                        p.drawPile.addToTop(c);
                        this.addToBot(new DrawCardAction(1));
                        break;
                    }
                }
                this.isDone = true;
            }
        });
    }
}