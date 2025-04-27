package cainongmod.cards;

import CaiNongMod.helpers.ModHelper;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.red.Strike_Red;
import com.megacrit.cardcrawl.cards.tempCards.Expunger;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.MasterRealityPower;

import java.util.ArrayList;

import static CaiNongMod.Characters.CaiNong.Enums.CAINONG_CARD;

public class WarMachine extends CustomCard {
    public static final String ID = ModHelper.MakePath("WarMachine");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    // private static final String NAME = "打击";
    private static final String NAME = CARD_STRINGS.NAME; // 读取本地化的名字
    private static final String IMG_PATH = "CaiNongModResources/img/cards/WarMachine.png";
    private static final int COST = -2;
    // private static final String DESCRIPTION = "造成 !D! 点伤害。";
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION; // 读取本地化的描述
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = CAINONG_CARD;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.NONE;

    public WarMachine() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 2;
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
        triggerWhenDrawn();
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return false;
    }

    @Override
    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();
        ArrayList<AbstractCard> cards = generateCards();
        for (AbstractCard card : cards) {
            if (!this.upgraded || AbstractDungeon.player.hasPower(MasterRealityPower.POWER_ID)) {
                this.addToBot(new MakeTempCardInHandAction(card));
            } else {
                card.upgrade();
                this.addToBot(new MakeTempCardInHandAction(card));
            }
        }

        this.addToBot(new ExhaustSpecificCardAction(this, AbstractDungeon.player.hand));
    }

    private ArrayList<AbstractCard> generateCards() {
        ArrayList<AbstractCard> derp = new ArrayList<>();
        ArrayList<AbstractCard> colorless = CardLibrary.getCardList(CardLibrary.LibraryType.COLORLESS);

        while(derp.size() != this.magicNumber) {
            boolean dupe = false;

            AbstractCard tmp = new Strike_Red();
            while (!tmp.type.equals(CardType.ATTACK) || !tmp.rarity.equals(CardRarity.SPECIAL)) {
                tmp = colorless.get(AbstractDungeon.cardRandomRng.random(colorless.size() - 1));
                if (tmp.cardID.equals(TortureAudience.ID) && AbstractDungeon.cardRandomRng.random(5) > 0) {
                    tmp = new Strike_Red();
                }
            }

            for (AbstractCard c : derp) {
                if (c.cardID.equals(tmp.cardID)) {
                    dupe = true;
                    break;
                }
            }

            if (!dupe) {
                AbstractCard c = tmp.makeCopy();

                if (c.cardID.equals(Expunger.ID)) {
                    ((Expunger)c).setX(1);
                }

                derp.add(c);
            }
        }

        return derp;
    }

    @Override
    public AbstractCard makeCopy() {
        return new WarMachine();
    }
}