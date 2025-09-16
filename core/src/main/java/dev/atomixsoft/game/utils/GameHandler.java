package dev.atomixsoft.game.utils;

import dev.atomixsoft.game.Card;
import dev.atomixsoft.game.Deck;
import dev.atomixsoft.game.players.Player;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class GameHandler {

    /**
     * <p>Reshuffles every card from our Discard pile into our Deck.</br>
     * If the player is in a Match, it will only do this if the deck is empty.</p>
     *
     * @param deck the {@link Deck} that we are shuffling the discard pile of
     * @param inMatch whether a {@link Player} is in a match or not
     */
    public static void Reshuffle(Deck deck, boolean inMatch) {
        ArrayList<Card> cardDeck = deck.getDeck();
        ArrayList<Card> discard = deck.getDiscard();

        if(!cardDeck.isEmpty() && inMatch) return;

        do {
            int size = discard.size();
            int selected = size <= 1 ? size : ThreadLocalRandom.current().nextInt(size + 1);

            cardDeck.add(discard.get(selected));
            discard.remove(selected);
        } while(!discard.isEmpty());
    }

    public static Card Spend_Card(Player player, Card card) {
        player.getHand().remove(card);
        player.getDeck().getSpent().add(card);

        return card;
    }

    public static Card Draw_Card(Player player, Card card) {
        Deck deck = player.getDeck();

        Card result = null;
        for(Card crd : deck.getDeck()) {
            if (crd.equals(card)) {
                deck.getDeck().remove(crd);
                result = card;
            }
        }

        if(result == null)
            throw new IllegalStateException("Card not found: " + card.toString());

        player.getHand().add(result);
        if(player.getHand().size() > player.getMaxHandSize())
            player.setMaxHandSize(player.getHand().size());

        return result;
    }

    public static Deck Create_Deck(Player player, Card.CardType type) {
        Deck deck = new Deck();

        int halfDeck = deck.getMaxDeckSize() / 2;
        int quarterDeck = halfDeck / 2;

        for(var i = 0; i < halfDeck; i++)
            deck.addCard(new Card(1, type));

        switch (type) {
            case ROCK -> {
                for(var i = 0; i < quarterDeck; i++) {
                    deck.addCard(new Card(1, Card.CardType.PAPER));
                    deck.addCard(new Card(1, Card.CardType.SCISSORS));
                }
            }

            case PAPER -> {
                for(var i = 0; i < quarterDeck; i++) {
                    deck.addCard(new Card(1, Card.CardType.ROCK));
                    deck.addCard(new Card(1, Card.CardType.SCISSORS));
                }
            }

            case SCISSORS -> {
                for(var i = 0; i < quarterDeck; i++) {
                    deck.addCard(new Card(1, Card.CardType.ROCK));
                    deck.addCard(new Card(1, Card.CardType.PAPER));
                }
            }
        }

        return deck;
    }

}
