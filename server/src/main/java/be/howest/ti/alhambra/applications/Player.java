package be.howest.ti.alhambra.applications;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Player {
    private final String nickname;
    private final String token;
    private List<Coin> playerDeck;
    private final List<Building> buildingReserve;
    private final Board playerAlhambra;
    private int actions;
    private boolean ready;

    public Player(String nickname, String gameID) {
        this.nickname = nickname;
        this.token = gameID + "+" + nickname; //example playerId: group12-001+John
        this.buildingReserve = new ArrayList<>();
        this.playerDeck = new ArrayList<>();
        this.playerAlhambra = new Board();
        this.actions = 0;
        this.ready = false;
    }

    public String getNickname() {
        return nickname;
    }

    public String getToken() {
        return token;
    }

    public List<Coin> getPlayerDeck() {
        return playerDeck;
    }

    public void setPlayerDeck(List<Coin> playerDeck) {
        this.playerDeck = playerDeck;
    }

    public Board getPlayerAlhambra() {
        return playerAlhambra;
    }

    public void addToBuildingReserve(Building building) { buildingReserve.add(building); }

    public void removeFromBuildingReserve(Building building) { buildingReserve.remove(building); }

    public List<Building> getBuildingReserve() {
        return buildingReserve;
    }

    public void buyBuilding(Currency currency, Building building, List<Coin> coins) {
        int amount = 0;
        for (Coin coin : coins) {
            if (coin.getCurrency().toString().equals(currency.toString())) {
                amount += coin.getAmount();
            }
        }

        if (amount == building.getCost()) {
            buildingReserve.add(building);
            removeMoneyFromPlayersMoneyBase(coins);
        }

        else if(amount > building.getCost()) {
            buildingReserve.add(building);
            removeMoneyFromPlayersMoneyBase(coins);
            useAction();
        }
        else{
            throw new IllegalArgumentException("Player doesn't have enough money");
        }
    }

    public void removeMoneyFromPlayersMoneyBase(List<Coin> coins){
        for (Coin usedCoin: coins){
            playerDeck.removeIf(usedCoinFromBase -> usedCoinFromBase.equals(usedCoin));
        }
    }

    public void addBuildingFromReserve(Location location, Building building) {
        playerAlhambra.addBuilding(location, building);
        removeFromBuildingReserve(building);
        useAction();
    }

    public void removeBuildingFromBoardToReserve(Location location, Building building) {
        playerAlhambra.removeBuilding(location);
        addToBuildingReserve(building);
    }

    public void startTurn(){
        actions = 1;
    }

    public void endTurn(){
        actions = 0;
    }

    public void useAction(){
        if(actions > 0){
            actions--;
        }
    }

    public int getActions() {
        return actions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(nickname, player.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nickname);
    }

    @Override
    public String toString() {
        return "Player{" +
                "nickname='" + nickname + '\'' +
                ", token='" + token + '\'' +
                ", buildingReserve=" + buildingReserve +
                '}';
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public int getSumOfPlayerDeck(){
        int amount = 0;
        for(Coin coin: playerDeck){
            amount += coin.getAmount();
        }
        return amount;
    }

}
