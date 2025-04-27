package cainongmod.Powers;

import cainongmod.cainongmod;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.android.mods.AssetLoader;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class WhiteLotusPower extends AbstractPower {
    // 能力的ID
    public static final String POWER_ID =  cainongmod.makeId("WhiteLotusPower");
    // 能力的本地化字段
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    // 能力的名称
    private static final String NAME = powerStrings.NAME;
    // 能力的描述
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private boolean trigger = false;

    public WhiteLotusPower(AbstractCreature owner, int Amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;

        // 如果需要不能叠加的能力，只需将上面的Amount参数删掉，并把下面的Amount改成-1就行
        this.amount = Amount;

        // 添加一大一小两张能力图
        String path128 = "cainongmodResources/img/powers/WhiteLotusPower84.png";
        String path48 = "cainongmodResources/img/powers/WhiteLotusPower32.png";
        this.region128 = new TextureAtlas.AtlasRegion(AssetLoader.getTexture(cainongmod.MOD_ID,path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(AssetLoader.getTexture(cainongmod.MOD_ID,path48), 0, 0, 32, 32);

        // 首次添加能力更新描述
        this.updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        super.atStartOfTurn();
        this.trigger = false;
    }

    @Override
    public int onLoseHp(int damageAmount) {
        if (!trigger) {
            this.flash();
            this.addToTop(new DrawCardAction(this.amount));
            this.trigger = true;
        }
        return damageAmount;
    }

    // 能力在更新时如何修改描述
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
}