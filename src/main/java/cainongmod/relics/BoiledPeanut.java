package cainongmod.relics;

import cainongmod.cainongmod;
import com.megacrit.cardcrawl.android.mods.AssetLoader;
import com.megacrit.cardcrawl.android.mods.abstracts.CustomRelic;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class BoiledPeanut extends CustomRelic {
    // 遗物ID（此处的ModHelper在“04 - 本地化”中提到）
    public static final String ID =  cainongmod.makeId("BoiledPeanut");
    // 图片路径
    private static final String IMG_PATH = "cainongmodResources/img/relics/BoiledPeanut.png";
    // 遗物类型
    private static final RelicTier RELIC_TIER = RelicTier.RARE;
    // 点击音效
    private static final LandingSound LANDING_SOUND = LandingSound.FLAT;

    public BoiledPeanut() {
        super(ID, AssetLoader.getTexture(cainongmod.MOD_ID,IMG_PATH), RELIC_TIER, LANDING_SOUND);
    }

    // 获取遗物描述，但原版游戏只在初始化和获取遗物时调用，故该方法等于初始描述
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void onExhaust(AbstractCard card) {
        super.onExhaust(card);
        AbstractDungeon.player.addBlock(3);
        this.flash();
    }

    public AbstractRelic makeCopy() {
        return new BoiledPeanut();
    }
}