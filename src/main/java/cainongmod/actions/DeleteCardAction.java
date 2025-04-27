package cainongmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

import java.util.ArrayList;

public class DeleteCardAction extends AbstractGameAction {
    private DamageInfo info;
    private AbstractCard theCard = null;

    public DeleteCardAction(AbstractCreature target, DamageInfo info) {
        this.duration = Settings.ACTION_DUR_FAST;

        this.info = info;
        this.setValues(target, info);
        this.actionType = ActionType.DAMAGE;
        this.duration = Settings.ACTION_DUR_MED;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_MED && this.target != null) {
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AttackEffect.SLASH_DIAGONAL));
            this.target.damage(this.info);
            if ((this.target.isDying || this.target.currentHealth <= 0) && !this.target.halfDead && !this.target.hasPower("Minion") && AbstractDungeon.player.gold >= 75) {
                AbstractDungeon.player.loseGold(75);
                AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_LOOTER_1A"));
                ArrayList<AbstractCard> possibleCards = new ArrayList<>(AbstractDungeon.player.masterDeck.group);
                int i = 0;
                while(i < 4) {
                    AbstractCard.CardRarity finalCardRarity = getCardRarity(i);
                    possibleCards.removeIf(c -> !c.rarity.equals(finalCardRarity) || c.cardID.equals("AscendersBane") || c.cardID.equals("CurseOfTheBell") || c.cardID.equals("Necronomicurse"));
                    if (!possibleCards.isEmpty()) {
                        break;
                    }
                    possibleCards = new ArrayList<>(AbstractDungeon.player.masterDeck.group);
                    i++;
                }

                if (!possibleCards.isEmpty()) {
                    this.theCard = possibleCards.get(AbstractDungeon.miscRng.random(0, possibleCards.size() - 1));
                    theCard.untip();
                    theCard.unhover();
                    AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(theCard, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                    AbstractDungeon.player.masterDeck.removeCard(theCard);
                }
            }

            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
        }

        this.tickDuration();
    }

    private static AbstractCard.CardRarity getCardRarity(int i) {
        AbstractCard.CardRarity cardRarity = AbstractCard.CardRarity.SPECIAL;
        switch (i) {
            case 0: cardRarity = AbstractCard.CardRarity.CURSE;
                break;
            case 1: cardRarity = AbstractCard.CardRarity.BASIC;
                break;
            case 2: cardRarity = AbstractCard.CardRarity.COMMON;
                break;
            case 3: cardRarity = AbstractCard.CardRarity.UNCOMMON;
        }
        return cardRarity;
    }
}
