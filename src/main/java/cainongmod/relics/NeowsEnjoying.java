package cainongmod.relics;


import cainongmod.cainongmod;
import cainongmod.cards.ROSL;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.android.mods.AssetLoader;
import com.megacrit.cardcrawl.android.mods.abstracts.CustomRelic;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.NeowsLament;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

public class NeowsEnjoying extends CustomRelic {
    // 遗物ID（此处的ModHelper在“04 - 本地化”中提到）
    public static final String ID =  cainongmod.makeId("NeowsEnjoying");
    // 图片路径
    private static final String IMG_PATH = "cainongmodResources/img/relics/NeowsEnjoying.png";
    // 遗物类型
    private static final RelicTier RELIC_TIER = RelicTier.SPECIAL;
    // 点击音效
    private static final LandingSound LANDING_SOUND = LandingSound.FLAT;

    public NeowsEnjoying() {
        super(ID, AssetLoader.getTexture(cainongmod.MOD_ID,IMG_PATH), RELIC_TIER, LANDING_SOUND);
        if (AbstractDungeon.player != null && AbstractDungeon.player.hasRelic(NeowsLament.ID) && AbstractDungeon.player.getRelic(NeowsLament.ID).counter > 0)
            this.counter = 3 + AbstractDungeon.player.getRelic(NeowsLament.ID).counter;
        else this.counter = 3;
    }

    // 获取遗物描述，但原版游戏只在初始化和获取遗物时调用，故该方法等于初始描述
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public void atBattleStart() {
        if (this.counter > 0) {
            --this.counter;
            if (this.counter == 0) {
                this.setCounter(-2);
                this.description = this.DESCRIPTIONS[1];
                this.tips.clear();
                this.tips.add(new PowerTip(this.name, this.description));
                this.initializeTips();
            }

            this.flash();

            for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                m.currentHealth = 1;
                m.healthBarUpdatedEvent();
                if (m.type.equals(AbstractMonster.EnemyType.BOSS)) {
                    CardCrawlGame.sound.play("PiaoBoss");
                }
            }

            this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        }

    }

    public void setCounter(int setCounter) {
        this.counter = setCounter;
        if (setCounter <= 0) {
            this.usedUp();
        }
    }

    @Override
    public void obtain() {
        AbstractPlayer player = AbstractDungeon.player;
        if (player.hasRelic(NeowsLament.ID)) {
            player.relics.stream()
                    .filter(r -> r instanceof NeowsLament)
                    .findFirst()
                    .map(r -> player.relics.indexOf(r))
                    .ifPresent(index
                            -> instantObtain(player, index, false));
            this.flash();
        }else super.obtain();
    }

    @Override
    public void onEquip() {
        super.onEquip();
        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new ROSL(), (float) Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
    }

    public AbstractRelic makeCopy() {
        return new NeowsEnjoying();
    }
}