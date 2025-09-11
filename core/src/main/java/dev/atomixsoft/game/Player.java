package dev.atomixsoft.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>Represents either Player of the match, their Deck, and their Hand. </br></br>
 *
 * Note: </br>
 * A Handler will be created to manage how cards are added to the Deck.</p>
 */
public class Player {

    private final List<Card> m_Hand;

    private final Deck m_Deck;
    private final int m_MaxHandSize;

    private boolean m_CPU;

    /**
     * Creates a {@link Player} with a maxHandSize of 5.
     */
    public Player() {
        this(5);
    }

    /**
     * Creates a Player with an empty hand and deck.
     *
     * @param maxHandSize the max amount of cards allowed in the hand before the player incurs a penalty
     */
    public Player(int maxHandSize) {
        m_Hand = new ArrayList<>();

        m_Deck = new Deck();
        m_MaxHandSize = maxHandSize;

        m_CPU = false;
    }

    public void setCPU(boolean isCPU) {
        m_CPU = isCPU;
    }

    public boolean isCPU() {
        return m_CPU;
    }

    public int getMaxHandSize() {
        return m_MaxHandSize;
    }

    public ArrayList<Card> getHand() {
        return (ArrayList<Card>) m_Hand;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return m_MaxHandSize == player.m_MaxHandSize && m_CPU == player.m_CPU && Objects.equals(m_Hand, player.m_Hand) && Objects.equals(m_Deck, player.m_Deck);
    }

    @Override
    public int hashCode() {
        return Objects.hash(m_Hand, m_Deck, m_MaxHandSize, m_CPU);
    }
}
