package dev.atomixsoft.game;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


/**
 * <p>This class handles the Deck of {@link Card}s as well as the Discard pile and keeping track of what cards are
 * spent by each {@link Player}.</p>
 */
public class Deck {
    private final List<Card> m_Deck;
    private final List<Card> m_Discard, m_Spent;

    private int m_Size;

    public Deck() {
        this(15);
    }

    public Deck(int deckSize) {
        m_Deck = new ArrayList<>();
        m_Discard = new ArrayList<>();
        m_Spent = new ArrayList<>();

        m_Size = deckSize;
    }

    public void addCard(Card card) {
        m_Size++;
        m_Deck.add(card);
    }

    /**
     * <p>Reshuffles every card from our Discard pile into our Deck.</br>
     * If the player is in a Match, it will only do this if the deck is empty.</p>
     *
     * @param inMatch whether a {@link Player} is in a match or not
     */
    public void reshuffle(boolean inMatch) {
        if(!m_Deck.isEmpty() && inMatch) return;

        do {
            int size = m_Discard.size();
            int selected = size <= 1 ? size : ThreadLocalRandom.current().nextInt(size + 1);

            m_Deck.add(m_Discard.get(selected));
            m_Discard.remove(selected);
        } while(!m_Discard.isEmpty());
    }

    public void reset() {
        reshuffle(false);

        m_Deck.addAll(m_Spent);
        m_Spent.clear();
    }

    public Card spendCard(Player player, Card card) {
        player.getHand().remove(card);
        m_Spent.add(card);

        return card;
    }

    public ArrayList<Card> getDeck() {
        return (ArrayList<Card>) m_Deck;
    }

    public ArrayList<Card> getDiscard() {
        return (ArrayList<Card>) m_Discard;
    }

}
