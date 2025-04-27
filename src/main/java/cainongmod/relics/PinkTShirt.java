package cainongmod.relics;

import cainongmod.cainongmod;
import com.megacrit.cardcrawl.android.mods.abstracts.CustomRelic;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class PinkTShirt extends CustomRelic {
    // 遗物ID（此处的ModHelper在“04 - 本地化”中提到）
    public static final String ID =  cainongmod.makeId("PinkTShirt");
    // 图片路径
    private static final String IMG_PATH = "CaiNongModResources/img/relics/PinkTShirt.png";
    // 遗物类型
    private static final RelicTier RELIC_TIER = RelicTier.UNCOMMON;
    // 点击音效
    private static final LandingSound LANDING_SOUND = LandingSound.FLAT;

    public PinkTShirt() {
        super(ID, ImageMaster.loadImage(IMG_PATH), RELIC_TIER, LANDING_SOUND);
    }

    // 获取遗物描述，但原版游戏只在初始化和获取遗物时调用，故该方法等于初始描述
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    private boolean flag = true;

    @Override
    public void atTurnStart() {
        super.atTurnStart();
        flag = true;
    }

    @Override
    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        if (damageAmount > 0 && flag) {
            this.flash();
            flag = false;
            return damageAmount - 2;
        } else {
            return damageAmount;
        }
    }

    public AbstractRelic makeCopy() {
        return new PinkTShirt();
    }
}