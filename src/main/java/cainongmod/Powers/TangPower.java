package cainongmod.Powers;

import cainongmod.actions.PlayRandomCardAction;
import cainongmod.cainongmod;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.android.mods.AssetLoader;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.EntanglePower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import java.util.ArrayList;

public class TangPower extends AbstractPower {
    // 能力的ID
    public static final String POWER_ID =  cainongmod.makeId("TangPower");
    // 能力的本地化字段
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    // 能力的名称
    private static final String NAME = powerStrings.NAME;
    // 能力的描述
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private boolean turnStart = false;
    private int numOfUseCard;
    private boolean isAutoPlay;

    public TangPower(AbstractCreature owner, int Amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;

        if (owner.hasPower(CandyBallPower.POWER_ID)) {
            this.type = PowerType.BUFF;
            this.isAutoPlay = true;
        }else {
            this.type = PowerType.DEBUFF;
            this.isAutoPlay = false;
        }

        // 如果需要不能叠加的能力，只需将上面的Amount参数删掉，并把下面的Amount改成-1就行
        this.amount = Amount;

        // 添加一大一小两张能力图
        String path128 = "powers/TangPower84.png";
        String path48 = "powers/TangPower32.png";
        this.region128 = new TextureAtlas.AtlasRegion(AssetLoader.getTexture(cainongmod.MOD_ID,cainongmod.getResourcePath(path128)), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(AssetLoader.getTexture(cainongmod.MOD_ID,cainongmod.getResourcePath(path48)), 0, 0, 32, 32);

        // 首次添加能力更新描述
        this.updateDescription();
    }

    // 能力在更新时如何修改描述
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    @Override
    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        super.onAfterUseCard(card, action);
        if (owner.hasPower(CandyBallPower.POWER_ID)) {
            this.type = PowerType.BUFF;
            this.isAutoPlay = true;
        }else {
            this.type = PowerType.DEBUFF;
            this.isAutoPlay = false;
        }
        if (turnStart && numOfUseCard >= 0 && !card.purgeOnUse) {
            this.addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    ArrayList<AbstractCard> cardGroup = new ArrayList<>(AbstractDungeon.player.hand.group);
                 if (isAutoPlay) {
    for (int i = cardGroup.size() - 1; i >= 0; i--) {
        AbstractCard c = cardGroup.get(i);
        if (canUseCard(c)) {
            cardGroup.remove(i);
        }
    }
} else {
    for (int i = cardGroup.size() - 1; i >= 0; i--) {
        AbstractCard c = cardGroup.get(i);
        if (c.cost > EnergyPanel.getCurrentEnergy() && !c.freeToPlay() || canUseCard(c)) {
            cardGroup.remove(i);
        }
    }
}

if (owner.hasPower(EntanglePower.POWER_ID)) {
    for (int i = cardGroup.size() - 1; i >= 0; i--) {
        AbstractCard c = cardGroup.get(i);
        if (c.type == AbstractCard.CardType.ATTACK) {
            cardGroup.remove(i);
        }
    }
}


                    if (!cardGroup.isEmpty()) {
                        this.addToTop(new PlayRandomCardAction(AbstractDungeon.getCurrRoom().monsters.getRandomMonster(null, true, AbstractDungeon.cardRandomRng), false, isAutoPlay,true));
                        numOfUseCard--;
                        TangPower.this.amount--;
                        if (TangPower.this.amount <= 0) {
                            this.addToTop(new RemoveSpecificPowerAction(AbstractDungeon.player,AbstractDungeon.player,TangPower.POWER_ID));
                            turnStart = false;
                        }
                    }else turnStart = false;
                    this.isDone = true;
                }
            });
        }
    }

    @Override
    public void atStartOfTurnPostDraw() {
        super.atStartOfTurnPostDraw();
        this.flash();
        this.turnStart = true;
        this.numOfUseCard = this.amount - 1;
        if (owner.hasPower(CandyBallPower.POWER_ID)) {
            this.type = PowerType.BUFF;
            this.isAutoPlay = true;
        }else {
            this.type = PowerType.DEBUFF;
            this.isAutoPlay = false;
        }
        this.addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                this.addToTop(new PlayRandomCardAction(AbstractDungeon.getCurrRoom().monsters.getRandomMonster(null, true, AbstractDungeon.cardRandomRng), false, isAutoPlay, true));
                TangPower.this.amount--;
                if (TangPower.this.amount <= 0) {
                    this.addToTop(new RemoveSpecificPowerAction(AbstractDungeon.player,AbstractDungeon.player,TangPower.POWER_ID));
                }
                this.isDone = true;
            }
        });
    }



    @Override
    public void onRemove() {
        super.onRemove();
    }

    private boolean canUseCard(AbstractCard c) {
        if (c.type == AbstractCard.CardType.STATUS && c.costForTurn < -1 && !AbstractDungeon.player.hasRelic("Medical Kit")) {
            return true;
        } else return c.type == AbstractCard.CardType.CURSE && c.costForTurn < -1 && !AbstractDungeon.player.hasRelic("Blue Candle");
    }
}