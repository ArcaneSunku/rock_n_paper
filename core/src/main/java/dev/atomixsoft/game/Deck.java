package dev.atomixsoft.game;

import dev.atomixsoft.game.players.Player;
import dev.atomixsoft.game.utils.GameHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
        this(25);
    }

    public Deck(int deckSize) {
        m_Deck = new ArrayList<>();
        m_Discard = new ArrayList<>();
        m_Spent = new ArrayList<>();

        m_Size = deckSize;
    }

    public void addCard(Card card) {
        m_Deck.add(card);

        if(m_Deck.size() > m_Size)
            m_Size = m_Deck.size();
    }

    public void reset() {
        GameHandler.Reshuffle(this, false);

        m_Deck.addAll(m_Spent);
        m_Spent.clear();
    }

    public Card spendCard(Player player, Card card) {
        player.getHand().remove(card);
        m_Spent.add(card);

        return card;
    }

    public int getMaxDeckSize() {
        return m_Size;
    }

    public ArrayList<Card> getDeck() {
        return (ArrayList<Card>) m_Deck;
    }

    public ArrayList<Card> getSpent() {
        return (ArrayList<Card>) m_Spent;
    }

    public ArrayList<Card> getDiscard() {
        return (ArrayList<Card>) m_Discard;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Deck deck = (Deck) o;
        return m_Size == deck.m_Size && Objects.equals(m_Deck, deck.m_Deck) && Objects.equals(m_Discard, deck.m_Discard) && Objects.equals(m_Spent, deck.m_Spent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(m_Deck, m_Discard, m_Spent, m_Size);
    }
}
