package be.howest.ti.alhambra.applications;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class CardDeck {
    private List<Coin> deck;

    public CardDeck(){
        deck = new LinkedList<>();
    }

    //initialize the deck and add all the cards and shuffle them

    private void initializeDeck(){
        deck.addAll( Coin.allCoins());
    }

    private List<Coin> shuffleDeck(){
        // Shuffle the deck so that it is in a random order
        Collections.shuffle(deck);
        return deck;
    }

    public List<Coin> createDeck(){
        initializeDeck();
        return shuffleDeck();
    }
}
