package cainongmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;

import java.util.ArrayList;

import static CaiNongMod.Characters.CaiNong.Enums.CAINONG_LIBRARY;

public class NongBoxAction extends AbstractGameAction {

    public NongBoxAction() {
        this.duration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        if (!p.drawPile.isEmpty()) {
            int num = p.drawPile.size();
            int count = 0;
            for (int i = 0; i < num; i++) {
                AbstractCard card = p.drawPile.group.get(i);
                if (card.tags.contains(AbstractCard.CardTags.STARTER_DEFEND) || card.tags.contains(AbstractCard.CardTags.STARTER_STRIKE)) {
                    AbstractDungeon.player.drawPile.group.remove(card);
                    exhaustCard(card);
                    count++;
                    i--;
                    num--;
                    Wait();
                }
            }
            for (int i = 0; i < count; i++) {
                this.addToBot(new MakeTempCardInDrawPileAction(getRandomCard(), 1, false, false, false));
            }
        }

        if(!p.hand.isEmpty()) {
            for (int i = 0; i < p.hand.size(); i++) {
                AbstractCard c = p.hand.group.get(i);
                if (c.tags.contains(AbstractCard.CardTags.STARTER_DEFEND) || c.tags.contains(AbstractCard.CardTags.STARTER_STRIKE)) {
                    this.addToBot(new ExhaustSpecificCardAction(c, p.hand));
                    this.addToBot(new MakeTempCardInHandAction(getRandomCard()));
                }
            }
        }

        if (!p.discardPile.isEmpty()) {
            int num = p.discardPile.size();
            int count = 0;
            for (int i = 0; i < num; i++) {
                AbstractCard card = AbstractDungeon.player.discardPile.group.get(i);
                if (card.tags.contains(AbstractCard.CardTags.STARTER_DEFEND) || card.tags.contains(AbstractCard.CardTags.STARTER_STRIKE)) {
                    AbstractDungeon.player.discardPile.group.remove(card);
                    exhaustCard(card);
                    count++;
                    num--;
                    i--;
                    Wait();
                }
            }
            for (int i = 0; i < count; i++) {
                this.addToBot(new MakeTempCardInDiscardAction(getRandomCard(),1));
            }
        }
        this.isDone = true;
    }

    private void exhaustCard(AbstractCard card) {
        AbstractDungeon.getCurrRoom().souls.remove(card);
        card.exhaustOnUseOnce = true;
        AbstractDungeon.player.limbo.group.add(card);
        card.current_y = -200.0F * Settings.scale;
        card.target_x = (float) Settings.WIDTH / 2.0F + 100.0F * Settings.xScale;
        card.target_y = (float) Settings.HEIGHT / 2.0F;
        card.targetAngle = 0.0F;
        card.lighten(false);
        card.drawScale = 0.12F;
        card.targetDrawScale = 0.75F;
        card.applyPowers();
        this.addToTop(new UnlimboAction(card, true));
        AbstractDungeon.player.hand.moveToExhaustPile(card);
        Wait();
    }

    private AbstractCard getRandomCard() {
        ArrayList<AbstractCard> CaiNong = CardLibrary.getCardList(CAINONG_LIBRARY);
        int roll = AbstractDungeon.cardRandomRng.random(99);
        AbstractCard.CardRarity cardRarity;
        if (roll < 55) {
            cardRarity = AbstractCard.CardRarity.COMMON;
        } else if (roll < 85) {
            cardRarity = AbstractCard.CardRarity.UNCOMMON;
        } else {
            cardRarity = AbstractCard.CardRarity.RARE;
        }

        AbstractCard tmp = CaiNong.get(AbstractDungeon.cardRandomRng.random(CaiNong.size() - 1));
        while (tmp.rarity != cardRarity) {
            tmp = CaiNong.get(AbstractDungeon.cardRandomRng.random(CaiNong.size() - 1));
        }

        return tmp;
    }

    private void Wait() {
        if (!Settings.FAST_MODE) {
            this.addToTop(new WaitAction(Settings.ACTION_DUR_MED));
        } else {
            this.addToTop(new WaitAction(Settings.ACTION_DUR_FASTER));
        }
    }
}
