package cainongmod.relics;


import cainongmod.Powers.NongHeiPower;
import cainongmod.cainongmod;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.android.mods.abstracts.CustomRelic;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class Factory extends CustomRelic {
    // 遗物ID（此处的ModHelper在“04 - 本地化”中提到）
    public static final String ID =  cainongmod.makeId("Factory");
    // 图片路径
    private static final String IMG_PATH = "CaiNongModResources/img/relics/Factory.png";
    // 遗物类型
    private static final RelicTier RELIC_TIER = RelicTier.BOSS;
    // 点击音效
    private static final LandingSound LANDING_SOUND = LandingSound.FLAT;

    public Factory() {
        super(ID, ImageMaster.loadImage(IMG_PATH), RELIC_TIER, LANDING_SOUND);
    }

    // 获取遗物描述，但原版游戏只在初始化和获取遗物时调用，故该方法等于初始描述
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic(OldComputer.ID);
    }


    @Override
    public void obtain() {
        AbstractPlayer player = AbstractDungeon.player;
        if (player.hasRelic(OldComputer.ID)) {
            player.relics.stream()
                    .filter(r -> r instanceof OldComputer)
                    .findFirst()
                    .map(r -> player.relics.indexOf(r))
                    .ifPresent(index
                            -> instantObtain(player, index, true));
            this.flash();
        }else super.obtain();
    }

    @Override
    public void atBattleStart() {
        super.atBattleStart();
        this.flash();
        AbstractMonster m = AbstractDungeon.getRandomMonster();
        this.addToBot(new ApplyPowerAction(m, AbstractDungeon.player, new NongHeiPower(m, 1)));
    }

    public AbstractRelic makeCopy() {
        return new Factory();
    }
}