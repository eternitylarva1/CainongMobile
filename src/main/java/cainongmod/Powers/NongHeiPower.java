package cainongmod.Powers;

import cainongmod.relics.Factory;
import cainongmod.cainongmod;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.android.mods.AssetLoader;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class NongHeiPower extends AbstractPower {
    // 能力的ID
    public static final String POWER_ID =  cainongmod.makeId("NongHeiPower");
    // 能力的本地化字段
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    // 能力的名称
    private static final String NAME = powerStrings.NAME;
    // 能力的描述
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public NongHeiPower(AbstractCreature owner, int Amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;

        // 如果需要不能叠加的能力，只需将上面的Amount参数删掉，并把下面的Amount改成-1就行
        this.amount = Amount;

        // 添加一大一小两张能力图
        String path128 = "cainongmodResources/img/powers/NongHeiPower84.png";
        String path48 = "cainongmodResources/img/powers/NongHeiPower32.png";
        this.region128 = new TextureAtlas.AtlasRegion(AssetLoader.getTexture(cainongmod.MOD_ID,cainongmod.getResourcePath(path128)), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(AssetLoader.getTexture(cainongmod.MOD_ID,cainongmod.getResourcePath(path48)), 0, 0, 32, 32);

        // 首次添加能力更新描述
        this.updateDescription();
    }

    @Override
    public float atDamageFinalGive(float damage, DamageInfo.DamageType type) {
        if (!AbstractDungeon.player.hasRelic(Factory.ID)) {
            return type == DamageInfo.DamageType.NORMAL ? damage * (1.0F + 0.25F * this.amount) : damage;
        }else {
            return damage;
        }
    }

    @Override
    public float atDamageFinalReceive(float damage, DamageInfo.DamageType type) {
        if (AbstractDungeon.player.hasRelic(Factory.ID)) {
            return type == DamageInfo.DamageType.NORMAL ? damage * 1.5F : damage;
        }else {
            return damage;
        }
    }

    @Override
    public void playApplyPowerSfx() {
        super.playApplyPowerSfx();
        if (owner.hasPower(NongBaiPower.POWER_ID)) {
            this.addToBot(new RemoveSpecificPowerAction(owner, owner, NongBaiPower.POWER_ID));
        }
    }

    // 能力在更新时如何修改描述
    public void updateDescription() {
        if (AbstractDungeon.player != null && AbstractDungeon.player.hasRelic(Factory.ID)) {
            this.description = DESCRIPTIONS[1] + 50 + "%";
        }else {
            this.description = DESCRIPTIONS[0] + this.amount * 25 + "%";
        }
    }
}