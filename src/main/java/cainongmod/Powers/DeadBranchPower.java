package cainongmod.Powers;

import cainongmod.cainongmod;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.android.mods.AssetLoader;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.watcher.MasterRealityPower;

import java.util.ArrayList;

public class DeadBranchPower extends AbstractPower {
    // 能力的ID
    public static final String POWER_ID =  cainongmod.makeId("DeadBranchPower");
    // 能力的本地化字段
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    // 能力的名称
    private static final String NAME = powerStrings.NAME;
    // 能力的描述
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public DeadBranchPower(AbstractCreature owner, int Amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;

        // 如果需要不能叠加的能力，只需将上面的Amount参数删掉，并把下面的Amount改成-1就行
        this.amount = Amount;

        // 添加一大一小两张能力图
        String path128 = "cainongmodResources/img/powers/DeadBranchPower84.png";
        String path48 = "cainongmodResources/img/powers/DeadBranchPower32.png";
        this.region128 = new TextureAtlas.AtlasRegion(AssetLoader.getTexture(cainongmod.MOD_ID,path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(AssetLoader.getTexture(cainongmod.MOD_ID,path48), 0, 0, 32, 32);

        // 首次添加能力更新描述
        this.updateDescription();
    }

    @Override
    public void onExhaust(AbstractCard card) {
        super.onExhaust(card);
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.flash();
            for (int i = 0; i < this.amount; i++) {
                this.addToBot(new AbstractGameAction() {
                    @Override
                    public void update() {
                        ArrayList<AbstractCard> cards = CardLibrary.getCardList(CardLibrary.LibraryType.RED);

                        AbstractCard tmp = cards.get(AbstractDungeon.cardRandomRng.random(cards.size() - 1));
                        while (tmp.rarity == AbstractCard.CardRarity.BASIC || tmp.hasTag(AbstractCard.CardTags.HEALING)) {
                            tmp = cards.get(AbstractDungeon.cardRandomRng.random(cards.size() - 1));
                        }

                        if (AbstractDungeon.player.hasPower(MasterRealityPower.POWER_ID)){
                            tmp.upgrade();
                        }

                        this.addToBot(new MakeTempCardInHandAction(tmp.makeCopy(), 1));

                        this.isDone = true;
                    }
                });
            }
        }
    }

    // 能力在更新时如何修改描述
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
}