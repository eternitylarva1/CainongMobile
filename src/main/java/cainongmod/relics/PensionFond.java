package cainongmod.relics;

import cainongmod.cainongmod;
import com.megacrit.cardcrawl.android.mods.AssetLoader;
import com.megacrit.cardcrawl.android.mods.abstracts.CustomRelic;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.ShopRoom;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import com.megacrit.cardcrawl.vfx.SpotlightPlayerEffect;

public class PensionFond extends CustomRelic {
    // 遗物ID（此处的ModHelper在“04 - 本地化”中提到）
    public static final String ID =  cainongmod.makeId("PensionFond");
    // 图片路径
    private static final String IMG_PATH = cainongmod.getResourcePath("relics/PensionFond.png");
    // 遗物类型
    private static final RelicTier RELIC_TIER = RelicTier.UNCOMMON;
    // 点击音效
    private static final LandingSound LANDING_SOUND = LandingSound.FLAT;

    public PensionFond() {
        super(ID, AssetLoader.getTexture(cainongmod.MOD_ID,IMG_PATH), RELIC_TIER, LANDING_SOUND);
    }

    @Override
    public void onVictory() {
        super.onVictory();
        if (!usedUp) {
            this.flash();
            AbstractDungeon.player.gainGold(AbstractDungeon.player.gold / 10);
            AbstractDungeon.effectList.add(new RainingGoldEffect(AbstractDungeon.player.gold / 10, true));
            AbstractDungeon.effectsQueue.add(new SpotlightPlayerEffect());
        }
    }

    @Override
    public void onSpendGold() {
        if (!this.usedUp) {
            this.flash();
            this.setCounter(-2);
        }
    }

    public void setCounter(int setCounter) {
        if (setCounter == -2) {
            this.usedUp();
            this.counter = -2;
        }
    }

    // 获取遗物描述，但原版游戏只在初始化和获取遗物时调用，故该方法等于初始描述
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy() {
        return new PensionFond();
    }

    public boolean canSpawn() {
        return (Settings.isEndless || AbstractDungeon.floorNum <= 48) && !(AbstractDungeon.getCurrRoom() instanceof ShopRoom);
    }
}