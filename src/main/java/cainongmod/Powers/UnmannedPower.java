package cainongmod.Powers;

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
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;

public class UnmannedPower extends AbstractPower {
    // 能力的ID
    public static final String POWER_ID =  cainongmod.makeId("UnmannedPower");
    // 能力的本地化字段
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    // 能力的名称
    private static final String NAME = powerStrings.NAME;
    // 能力的描述
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public UnmannedPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.DEBUFF;

        // 如果需要不能叠加的能力，只需将上面的Amount参数删掉，并把下面的Amount改成-1就行
        this.amount = -1;

        // 添加一大一小两张能力图
        String path128 = "powers/UnmannedPower84.png";
        String path48 = "powers/UnmannedPower32.png";
        this.region128 = new TextureAtlas.AtlasRegion(AssetLoader.getTexture(cainongmod.MOD_ID,cainongmod.getResourcePath(path128)), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(AssetLoader.getTexture(cainongmod.MOD_ID,cainongmod.getResourcePath(path48)), 0, 0, 32, 32);

        // 首次添加能力更新描述
        this.updateDescription();
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        super.onUseCard(card, action);

        if (card.type == AbstractCard.CardType.ATTACK) {
            this.flash();

            this.addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    this.addToBot(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, IntangiblePlayerPower.POWER_ID));
                    this.addToBot(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, UnmannedPower.POWER_ID));
                    this.isDone = true;
                }
            });
        }

    }

    // 能力在更新时如何修改描述
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}