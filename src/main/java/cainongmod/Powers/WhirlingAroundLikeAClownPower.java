package cainongmod.Powers;


import cainongmod.actions.DiscoverGreenCardAction;
import cainongmod.cainongmod;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.android.mods.AssetLoader;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;

public class WhirlingAroundLikeAClownPower extends AbstractPower {
    // 能力的ID
    public static final String POWER_ID =  cainongmod.makeId("WhirlingAroundLikeAClownPower");
    // 能力的本地化字段
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    // 能力的名称
    private static final String NAME = powerStrings.NAME;
    // 能力的描述
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private ArrayList<Boolean> upgradeList = new ArrayList<>();

    public WhirlingAroundLikeAClownPower(AbstractCreature owner, int Amount, boolean isUpgraded) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        // 如果需要不能叠加的能力，只需将上面的Amount参数删掉，并把下面的Amount改成-1就行
        this.amount = Amount;
        this.upgradeList.add(isUpgraded);

        // 添加一大一小两张能力图
        String path128 = "powers/WhirlingAroundLikeAClownPower84.png";
        String path48 = "powers/WhirlingAroundLikeAClownPower32.png";
        this.region128 = new TextureAtlas.AtlasRegion(AssetLoader.getTexture(cainongmod.MOD_ID,cainongmod.getResourcePath(path128)), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(AssetLoader.getTexture(cainongmod.MOD_ID,cainongmod.getResourcePath(path48)), 0, 0, 32, 32);

        // 首次添加能力更新描述
        this.updateDescription();
    }

    public void atStartOfTurn() {
        this.flash();

        for(int i = 0; i < this.upgradeList.size(); ++i) {
            int finalI = i;
            this.addToBot(new AbstractGameAction() {
                public void update() {
                    this.addToBot(new DiscoverGreenCardAction(WhirlingAroundLikeAClownPower.this.upgradeList.get(finalI)));
                    this.isDone = true;
                }
            });
        }
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        super.onPlayCard(card, m);
        if (card.cardID.equals( cainongmod.makeId("WhirlingAroundLikeAClown")))
            this.upgradeList.add(card.upgraded);

    }

    // 能力在更新时如何修改描述
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
}
