package cainongmod.cards;

import CaiNongMod.helpers.ModHelper;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.SearingBlowEffect;

import java.util.ArrayList;

import static CaiNongMod.Characters.CaiNong.Enums.CAINONG_CARD;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.miscRng;
import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public class NongSearing extends CustomCard {
    public static final String ID = ModHelper.MakePath("NongSearing");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    // private static final String NAME = "打击";
    private static final String NAME = CARD_STRINGS.NAME; // 读取本地化的名字
    private static final String IMG_PATH = "CaiNongModResources/img/cards/NongSearing.png";
    private static final int COST = 3;
    // private static final String DESCRIPTION = "造成 !D! 点伤害。";
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION; // 读取本地化的描述
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = CAINONG_CARD;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private ArrayList<Integer> RNGS = new ArrayList<>();

    public NongSearing() {
        this(0);
    }

    public NongSearing(int upgrades) {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.damage = this.baseDamage = 6;
        this.magicNumber = this.baseMagicNumber = 3;
        this.timesUpgraded = upgrades;
    }

    @Override
    public void upgrade() {
        if (player != null || !RNGS.isEmpty()) {
            if (player != null) {
                int rng;
                if (cost == 0) {
                    rng = miscRng.random(1);
                } else {
                    rng = miscRng.random(2);
                }

                if (timesUpgraded == RNGS.size()) {
                    RNGS.add(rng);
                }
            }

            switch (RNGS.get(timesUpgraded)) {
                case 0:
                    this.upgradeDamage(3);
                    break;
                case 1:
                    this.upgradeMagicNumber(1);
                    break;
                case 2:
                    this.upgradeBaseCost(cost - 1);
            }

            ++this.timesUpgraded;
            this.upgraded = true;
            this.name = NAME + "+" + this.timesUpgraded;
            this.initializeTitle();
        }else {
            this.upgradeBaseCost(COST - 1);
            this.upgradeDamage(3);
            this.upgradeMagicNumber(1);
        }
    }

    public boolean canUpgrade() {
        return true;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < this.magicNumber; i++) {
            if (m != null) {
                this.addToBot(new VFXAction(new SearingBlowEffect(m.hb.cX, m.hb.cY, this.timesUpgraded), 0.2F));
            }

            this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        }
    }
}