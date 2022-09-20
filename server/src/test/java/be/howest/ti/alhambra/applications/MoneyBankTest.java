package be.howest.ti.alhambra.applications;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class MoneyBankTest {
    private final MoneyBank moneyBank = new MoneyBank();
    private final Player player = new Player("John", "Group12-123");

    @Test
    public void makeDeck() {
        assertEquals(104, moneyBank.getDeck().size());
    }

    @Test
    public void GivePlayersStartingCards(){

        moneyBank.startDeck();
        moneyBank.startMoneyShop();

        player.setPlayerDeck(moneyBank.givePlayerStartingCards());
        int startamount = 0;

        for (Coin coin : player.getPlayerDeck()){
            startamount += coin.getAmount();
        }

        assertTrue(startamount >= 20);
    }

    @Test
    public void RemoveCardFromMoneyShop(){
        //test of de kaart uit de shop wordt verwijderd en teruggegeven voor de speler, alsook dat er een nieuwe kaart wordt toegevoegd aan de shop.
        moneyBank.startDeck();
        moneyBank.startMoneyShop();

        StringBuilder firstDeck = new StringBuilder();
        StringBuilder secondDeck = new StringBuilder();
        int i = 0;

        while (i < moneyBank.getShopCards().size()){
            firstDeck.append(moneyBank.getShopCards().get(i).toString());
            i++;
        }

        System.out.println(moneyBank.getShopCards());
        moneyBank.removeCardFromMoneyShop(player, moneyBank.getShopCards().get(2));

        while (i < moneyBank.getShopCards().size()){
            secondDeck.append(moneyBank.getShopCards().get(i).toString());
            i++;
        }

        System.out.println(moneyBank.getShopCards());
        assertEquals(4, moneyBank.getShopCards().size());
        assertNotEquals(firstDeck, secondDeck);
    }
}