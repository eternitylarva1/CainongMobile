package cainongmod.actions;


import cainongmod.Powers.NongHeiPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class FunkYouAction extends AbstractGameAction {
    private DamageInfo info;
    private static final float DURATION = 0.01F;
    private static final float POST_ATTACK_WAIT_DUR = 0.2F;
    private int numTimes;

    public FunkYouAction(AbstractCreature target, DamageInfo info, int numTimes) {
        this.info = info;
        this.target = target;
        this.actionType = ActionType.DAMAGE;
        this.attackEffect = AttackEffect.SLASH_HEAVY;
        this.duration = 0.01F;
        this.numTimes = numTimes;
    }

    public FunkYouAction(DamageInfo info, int numTimes) {
        this.info = info;
        this.actionType = ActionType.DAMAGE;
        this.attackEffect = AttackEffect.SLASH_HEAVY;
        this.duration = 0.01F;
        this.numTimes = numTimes;
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.addToTop(new FunkYouAction(AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng), this.info, numTimes));
        }
    }

    @Override
    public void update() {
        if (this.target == null) {
            this.isDone = true;
        } else if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
            AbstractDungeon.actionManager.clearPostCombatActions();
            this.isDone = true;
        } else {
            if (this.target.currentHealth > 0) {
                AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, this.attackEffect));
                this.info.applyPowers(this.info.owner, this.target);
                this.target.damage(this.info);
                this.addToTop(new ApplyPowerAction(this.target,AbstractDungeon.player,new NongHeiPower(this.target, 1)));
                if (this.numTimes > 1 && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                    --this.numTimes;
                    this.addToTop(new FunkYouAction(AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng), this.info, this.numTimes));
                }
                this.addToTop(new WaitAction(0.2F));
            } else {
                this.addToTop(new FunkYouAction(AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng), this.info, this.numTimes));
            }
            this.isDone = true;
        }
    }
}
