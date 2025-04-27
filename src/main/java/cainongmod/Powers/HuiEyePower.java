package cainongmod.Powers;

import cainongmod.cainongmod;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class HuiEyePower extends AbstractPower {
    // 能力的ID
    public static final String POWER_ID =  cainongmod.makeId("HuiEyePower");
    // 能力的本地化字段
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    // 能力的名称
    private static final String NAME = powerStrings.NAME;
    // 能力的描述
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private boolean once = true;

    public HuiEyePower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;

        // 如果需要不能叠加的能力，只需将上面的Amount参数删掉，并把下面的Amount改成-1就行
        this.amount = -1;
        this.loadRegion("mantra");

        // 首次添加能力更新描述
        this.updateDescription();
    }

    public void onDrawCardAction() {
        if (once) {
            this.flash();
            this.addToBot(new RollMoveAction((AbstractMonster) this.owner));
            once = false;
        }
    }

    @Override
    public void atStartOfTurn() {
        once = true;
    }

    // 能力在更新时如何修改描述
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}