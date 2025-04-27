package cainongmod.helpers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.ArrayList;

public class ModHelper {
    private static final ArrayList<AbstractCard> cards_Inside = new ArrayList<>();
    public static int RushNum = 1;
    public static boolean isUseRush = false;
    public static boolean naiLong = true;

    public static String MakePath (String id) {
        return "cainongmod:" + id;
    }

    public static AbstractGameAction.AttackEffect AtkSound (AbstractCard card) {
        if (card.damage < 20) {
            return AbstractGameAction.AttackEffect.SLASH_DIAGONAL;
        }else if (card.damage < 40) {
            return AbstractGameAction.AttackEffect.SLASH_HEAVY;
        }else {
            return AbstractGameAction.AttackEffect.BLUNT_HEAVY;
        }
    }

    public static ArrayList<AbstractCard> getCards_Inside() {
        return cards_Inside;
    }

    public static void addInsideCards(AbstractCard c) {
        cards_Inside.add(c);
    }

    public static void clearInsideCards() {
        cards_Inside.clear();
    }
}
