package cainongmod.cards;

import CaiNongMod.helpers.ModHelper;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static CaiNongMod.Characters.CaiNong.Enums.CAINONG_CARD;

public class DuckGuide extends CustomCard {
    public static final String ID = ModHelper.MakePath("DuckGuide");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    // private static final String NAME = "打击";
    private static final String NAME = CARD_STRINGS.NAME; // 读取本地化的名字
    private static final String IMG_PATH = "CaiNongModResources/img/cards/DuckGuide.png";
    private static final int COST = 1;
    // private static final String DESCRIPTION = "造成 !D! 点伤害。";
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION; // 读取本地化的描述
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = CAINONG_CARD;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public DuckGuide() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 5;
        this.block = this.baseBlock = 0;
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
        this.rawDescription = CARD_STRINGS.DESCRIPTION;
        this.addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                this.addToBot(new GainBlockAction(AbstractDungeon.player, DuckGuide.this.block));

                int times = DuckGuide.this.magicNumber;
                for (int i = AbstractDungeon.player.drawPile.group.size() - 1;i >= 0;i--) {
                    AbstractCard card = AbstractDungeon.player.drawPile.group.get(i);
                    if (card.canUpgrade() && times != 0 && card.type != CardType.STATUS) {
                        card.upgrade();
                        times--;
                    }
                }

                for (AbstractCard card : AbstractDungeon.player.discardPile.group) {
                    if (card.canUpgrade() && times != 0) {
                        card.upgrade();
                        times--;
                    }
                }

                this.isDone = true;
            }
        });
    }


    public void applyPowers() {
        int num = 0;
        AbstractPlayer p = AbstractDungeon.player;
        for (AbstractCard card : p.drawPile.group) {
            if (card.upgraded) {
                num++;
            }
        }

        for (AbstractCard card : p.discardPile.group) {
            if (card.upgraded) {
                num++;
            }
        }

        for (AbstractCard card : p.hand.group) {
            if (card.upgraded) {
                num++;
            }
        }

        this.baseBlock = num;

        super.applyPowers();
        this.rawDescription = CARD_STRINGS.DESCRIPTION;
        this.rawDescription = this.rawDescription + CARD_STRINGS.UPGRADE_DESCRIPTION;
        this.initializeDescription();
    }

    public void onMoveToDiscard() {
        this.rawDescription = CARD_STRINGS.DESCRIPTION;
        this.initializeDescription();
    }

    @Override
    public AbstractCard makeCopy() {
        return new DuckGuide();
    }
}
