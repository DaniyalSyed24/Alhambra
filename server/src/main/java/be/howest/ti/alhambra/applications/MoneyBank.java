package be.howest.ti.alhambra.applications;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class MoneyBank {
    private final CardDeck cardDeck = new CardDeck();
    private LinkedList<Coin> deck;
    private List<Coin> shopCards;

    public List<Coin> getShopCards(){
        return shopCards;
    }

    public List<Coin> getDeck(){
        return deck;
    }

    public MoneyBank() {
        startDeck();
        startMoneyShop();
    }

    public void startDeck(){
        deck = (LinkedList<Coin>) cardDeck.createDeck();
    }

    public void startMoneyShop(){
        // initialize with four cards
        Coin firstCard = deck.get(deck.size() - 1);
        deck.remove(deck.size() - 1);
        Coin secondCard = deck.get(deck.size() - 1);
        deck.remove(deck.size() - 1);
        Coin thirdCard = deck.get(deck.size() - 1);
        deck.remove(deck.size() - 1);
        Coin fourthCard = deck.get(deck.size() - 1);
        deck.remove(deck.size() - 1);

        shopCards = Arrays.asList(firstCard, secondCard, thirdCard, fourthCard);
    }

    public void removeCardFromMoneyShop(Player player, Coin chosenCard){
        // remove card from money shop to put into players hand
        if (shopCards.contains(chosenCard)){
            player.getPlayerDeck().add(chosenCard);
            for (int i = 0; i < shopCards.size(); i++){
                if (shopCards.get(i) == chosenCard){
                    shopCards.set(i, deck.get(deck.size() - 1));
                    deck.remove(deck.size() - 1);
                    break;
                }
            }
            player.useAction();
        }
        else throw new IllegalArgumentException("Card is not in shop");

    }

    public List<Coin>  givePlayerStartingCards(){
        // Gives a player his starting cards, meaning they get cards until the total value is over 20
        int i = 0;
        Coin tempCard;
        List<Coin> playerDeck = new ArrayList<>();
        while (i <= 20){
            tempCard = deck.getLast();
            deck.remove(deck.getLast());
            i += tempCard.getAmount();
            playerDeck.add(tempCard);
        }

        return playerDeck;
    }

}

