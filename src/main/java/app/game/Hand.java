package app.game;

import app.card.Card;
import app.card.Rank;

import java.util.ArrayList;

public class Hand {
    private final ArrayList<Card> cards;

    public Hand() {
        cards = new ArrayList<>();
    }

    public void flip() {
        cards.get(cards.size() - 1).flip();
    }

    public boolean isBlackjack() {
        for (Card card : cards) {
            Rank rank = card.getRank();
            if (rank != Rank.ACE && rank != Rank.TEN) {
                return false;
            }
        }

        return true;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public int getValue() {
        int value = 0;
        int numAces = 0;

        for (Card card : cards) {
            if (!card.isFaceUp()) { continue; }
            Rank rank = card.getRank();

            if (rank == Rank.ACE) { numAces++; }
            value += rank.getValue();
        }

        while (value > 21 && numAces > 0) {
            value -= 10;
            numAces--;
        }

        return value;
    }
}
