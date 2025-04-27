package cainongmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class JammedStartAction extends AbstractGameAction {
    private AbstractPlayer player;
    private int numCards;
    private AbstractPlayer p;
    private AbstractMonster m;
    private int damage;
    private boolean isRandom;

    public JammedStartAction(int numberOfCards, AbstractPlayer p, AbstractMonster m, int damage, boolean isRandom) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.player = AbstractDungeon.player;
        this.numCards = numberOfCards;
        this.p = p;
        this.m = m;
        this.damage = damage;
        this.isRandom = isRandom;
    }

    public void update() {
        if (isRandom) {
            if (AbstractDungeon.player.drawPile.size() + AbstractDungeon.player.discardPile.size() == 0) {
                this.isDone = true;
                return;
            }

            if (AbstractDungeon.player.drawPile.isEmpty()) {
                this.addToTop(new JammedStartAction(this.numCards, this.p, this.m, this.damage, this.isRandom));
                this.addToTop(new EmptyDeckShuffleAction());
                this.isDone = true;
                return;
            }

            AbstractCard card = AbstractDungeon.player.drawPile.getTopCard();
            AbstractDungeon.player.drawPile.group.remove(card);
            AbstractDungeon.getCurrRoom().souls.remove(card);
            AbstractDungeon.player.limbo.group.add(card);
            card.current_y = -200.0F * Settings.scale;
            card.target_x = (float) Settings.WIDTH / 2.0F + 200.0F * Settings.xScale;
            card.target_y = (float) Settings.HEIGHT / 2.0F;
            card.targetAngle = 0.0F;
            card.lighten(false);
            card.drawScale = 0.12F;
            card.targetDrawScale = 0.75F;
            this.addToTop(new UnlimboAction(card, true));
            this.p.hand.moveToExhaustPile(card);

            for (int i = 0; i < card.cost; i++) {
                this.addToTop(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AttackEffect.SLASH_DIAGONAL));
            }

            this.addToTop(new WaitAction(Settings.ACTION_DUR_FASTER));

            this.isDone = true;
        }else if (this.duration == this.startDuration) {
            if (AbstractDungeon.player.drawPile.size() + AbstractDungeon.player.discardPile.size() == 0) {
                this.isDone = true;
                return;
            }

            if (AbstractDungeon.player.drawPile.isEmpty()) {
                this.addToTop(new JammedStartAction(this.numCards, this.p, this.m, this.damage, this.isRandom));
                this.addToTop(new EmptyDeckShuffleAction());
                this.isDone = true;
                return;
            }
            CardGroup temp = new CardGroup(CardGroupType.UNSPECIFIED);

            for (AbstractCard c : this.player.drawPile.group) {
                temp.addToTop(c);
            }

            temp.sortAlphabetically(true);
            temp.sortByRarityPlusStatusCardType(false);
            AbstractDungeon.gridSelectScreen.open(temp, numCards, "消耗", false);
            this.tickDuration();
        } else {
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {

                for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                    AbstractDungeon.player.limbo.group.add(c);
                    c.exhaust = true;
                    AbstractDungeon.player.drawPile.group.remove(c);
                    AbstractDungeon.getCurrRoom().souls.remove(c);
                    this.addToTop(new UnlimboAction(c, true));
                    this.p.hand.moveToExhaustPile(c);

                    for (int i = 0; i < c.cost; i++) {
                        this.addToTop(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AttackEffect.SLASH_DIAGONAL));
                    }
                }

                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                AbstractDungeon.player.hand.refreshHandLayout();
            }

            this.tickDuration();
        }
    }
}
