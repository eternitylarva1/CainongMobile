package cainongmod.actions;

import CaiNongMod.helpers.ModHelper;
import CaiNongMod.relics.NaiLong;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EntanglePower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import java.util.ArrayList;

public class PlayRandomCardAction extends AbstractGameAction {
    private boolean exhaustCards;
    private boolean isAutoPlay;
    private final AbstractPlayer p;
    private boolean isTang = false;

    public PlayRandomCardAction(AbstractCreature target, boolean exhausts, boolean isAutoPlay) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.WAIT;
        this.source = AbstractDungeon.player;
        this.target = target;
        this.exhaustCards = exhausts;
        this.isAutoPlay = isAutoPlay;
        this.p = AbstractDungeon.player;
    }

    public PlayRandomCardAction(AbstractCreature target, boolean exhausts, boolean isAutoPlay, boolean Tang) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.WAIT;
        this.source = AbstractDungeon.player;
        this.target = target;
        this.exhaustCards = exhausts;
        this.isAutoPlay = isAutoPlay;
        this.p = AbstractDungeon.player;
        this.isTang = Tang;
    }

    @Override
    public void update() {
        if (AbstractDungeon.player.hand.isEmpty()) {
            this.isDone = true;
            return;
        }

        if (!AbstractDungeon.player.hand.isEmpty()) {
            AbstractCard card;
            ArrayList<AbstractCard> cardGroup = new ArrayList<>(AbstractDungeon.player.hand.group);
            if (!isAutoPlay) {
                cardGroup.removeIf(c -> c.cost > EnergyPanel.getCurrentEnergy() && !c.freeToPlay() || canUseCard(c));
            } else  {
                cardGroup.removeIf(c -> canUseCard(c));
            }

            if (p.hasPower(EntanglePower.POWER_ID)) {
                cardGroup.removeIf(c -> c.type == AbstractCard.CardType.ATTACK);
            }

            if (cardGroup.isEmpty()) {
                this.isDone = true;
                return;
            }

            card = cardGroup.get(AbstractDungeon.cardRandomRng.random(cardGroup.size() - 1));

            AbstractDungeon.player.hand.group.remove(card);
            AbstractDungeon.getCurrRoom().souls.remove(card);
            card.exhaustOnUseOnce = this.exhaustCards;
            AbstractDungeon.player.limbo.group.add(card);
            card.current_y = -200.0F * Settings.scale;
            card.target_x = (float)Settings.WIDTH / 2.0F + 200.0F * Settings.xScale;
            card.target_y = (float)Settings.HEIGHT / 2.0F;
            card.targetAngle = 0.0F;
            card.lighten(false);
            card.drawScale = 0.12F;
            card.targetDrawScale = 0.75F;
            card.applyPowers();
            this.addToTop(new NewQueueCardAction(card, this.target, false, isAutoPlay));
            this.addToTop(new UnlimboAction(card));
            this.addToTop(new WaitAction(Settings.ACTION_DUR_FASTER));
            this.addToTop(new AbstractGameAction() {
                @Override
                public void update() {
                    if (p.hasRelic(NaiLong.ID) && isTang && !card.purgeOnUse) {
                        if (ModHelper.naiLong) {
                            p.getRelic(NaiLong.ID).flash();

                            AbstractMonster m = AbstractDungeon.getRandomMonster();

                            AbstractCard tmp = card.makeSameInstanceOf();
                            AbstractDungeon.player.limbo.addToBottom(tmp);
                            tmp.current_x = card.current_x;
                            tmp.current_y = card.current_y;
                            tmp.target_x = (float) Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
                            tmp.target_y = (float) Settings.HEIGHT / 2.0F;
                            if (m != null) {
                                tmp.calculateCardDamage(m);
                            }

                            tmp.purgeOnUse = true;
                            AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(tmp, m, card.energyOnUse, true, true), true);
                            this.addToTop(new WaitAction(Settings.ACTION_DUR_FASTER));
                            ModHelper.naiLong = false;
                        }
                    }
                    this.isDone = true;
                }
            });
        }
        this.isDone = true;
    }

    private boolean canUseCard(AbstractCard c) {
        if (c.type == AbstractCard.CardType.STATUS && c.costForTurn < -1 && !AbstractDungeon.player.hasRelic("Medical Kit")) {
            return true;
        } else return c.type == AbstractCard.CardType.CURSE && c.costForTurn < -1 && !AbstractDungeon.player.hasRelic("Blue Candle");
    }
}
