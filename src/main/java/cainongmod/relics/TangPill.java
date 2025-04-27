package cainongmod.relics;


import cainongmod.Powers.TangPower;
import cainongmod.cainongmod;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.android.mods.AssetLoader;
import com.megacrit.cardcrawl.android.mods.abstracts.CustomRelic;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class TangPill extends CustomRelic {
    // 遗物ID（此处的ModHelper在“04 - 本地化”中提到）
    public static final String ID =  cainongmod.makeId("TangPill");
    // 图片路径
    private static final String IMG_PATH = cainongmod.getResourcePath("relics/TangPill.png");
    // 遗物类型
    private static final RelicTier RELIC_TIER = RelicTier.BOSS;
    // 点击音效
    private static final LandingSound LANDING_SOUND = LandingSound.FLAT;

    public TangPill() {
        super(ID, AssetLoader.getTexture(cainongmod.MOD_ID,IMG_PATH), RELIC_TIER, LANDING_SOUND);
        this.counter = 0;
    }

    // 获取遗物描述，但原版游戏只在初始化和获取遗物时调用，故该方法等于初始描述
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void onPlayerEndTurn() {
        super.onPlayerEndTurn();
        this.counter++;
        if (this.counter == 3) {
            this.addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new TangPower(AbstractDungeon.player, 1)));
            this.flash();
            this.counter = 0;
        }
    }

    @Override
    public void onEquip() {
        super.onEquip();
        AbstractDungeon.player.energy.energyMaster++;
    }

    public AbstractRelic makeCopy() {
        return new TangPill();
    }
}