package cainongmod.Powers;

import cainongmod.cainongmod;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.android.mods.AssetLoader;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class FireRaidPower extends AbstractPower {
    // 能力的ID
    public static final String POWER_ID =  cainongmod.makeId("FireRaidPower");
    // 能力的本地化字段
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    // 能力的名称
    private static final String NAME = powerStrings.NAME;
    // 能力的描述
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public FireRaidPower(AbstractCreature owner, int Amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;

        // 如果需要不能叠加的能力，只需将上面的Amount参数删掉，并把下面的Amount改成-1就行
        this.amount = Amount;

        // 添加一大一小两张能力图
        String path128 = "powers/FireRaidPower84.png";
        String path48 = "powers/FireRaidPower32.png";
        this.region128 = new TextureAtlas.AtlasRegion(AssetLoader.getTexture(cainongmod.MOD_ID,cainongmod.getResourcePath(path128)), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(AssetLoader.getTexture(cainongmod.MOD_ID,cainongmod.getResourcePath(path48)), 0, 0, 32, 32);

        // 首次添加能力更新描述
        this.updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        super.atStartOfTurn();
        this.addToBot(new MakeTempCardInDrawPileAction(new Burn(), this.amount, true, false, false));
    }

    // 能力在更新时如何修改描述
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
}