package be.howest.ti.alhambra.applications;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AlhambraController {
    Lobby lobby = new Lobby();

    public Currency[] getCurrencies() {
        return Currency.values();
    }

    public List<Building> getBuildings() {
        return Building.allBuildings();
    }

    public BuildingType[] getBuildingTypes() {
        return BuildingType.values();
    }

    public List<Coin> convertToCoinsToList(Coin[] coins){
        return new ArrayList<>(Arrays.asList(coins));

    }

    public Object getGames(){
        return lobby.getGames();
    }

    public String createGame(){
        return lobby.createGame();
    }

    public void clearGames(){lobby.clearGames();}


    public String joinGame(String gameId,String playerName){return lobby.joinGame(gameId, playerName);}

    public void leaveGame(String gameId,String  playerName){
        lobby.leaveGame(gameId, playerName);
        Player playerInQuestion = getGame(gameId).getPlayerByName(playerName);
        getGame(gameId).getPlayersTurn().leaveListOfPlayers(playerInQuestion.getToken());
    }

    public void setReady(String gameId,String  playerName){lobby.setReady(gameId, playerName);}

    public void setNotReady(String gameId,String  playerName){lobby.setNotReady(gameId, playerName);}

    public Game getGame(String gameID) {
        Game tempGame = null;

        for (Game game : lobby.getGames()) {
            if (game.getGameId().equals(gameID)){
                tempGame = game;
            }
        }
        return tempGame;
    }

    public void finishTurn(String gameId) {
        if(getGame(gameId).getCurrentPlayer().getActions() ==0){
            getGame(gameId).endTurnCurrentPlayer();
    }
    }

    public void takeCard(String gameId, String playerName, Coin coin) {
        if(getGame(gameId).getPlayerByName(playerName).getSumOfPlayerDeck()> 1500 ){
            getGame(gameId).getMoneyBank().removeCardFromMoneyShop(getGame(gameId).getPlayerByName(playerName), coin);
        }
    }


    public void buyBuilding(String gameId, String playerName, Currency currency, List<Coin> coins) {
        Building building = null;
        switch (currency){
            case YELLOW:
                building = getGame(gameId).getMarket().getYellow();
                break;
            case ORANGE:
                building = getGame(gameId).getMarket().getOrange();
                break;
            case GREEN:
                building = getGame(gameId).getMarket().getGreen();
                break;
            case BLUE:
                building = getGame(gameId).getMarket().getBlue();
                break;
        }
        getGame(gameId).getPlayerByName(playerName).buyBuilding(currency, building, coins);
    }

    public void buildBuilding(String gameId, String playerName, Location location, Building building) {
        Game game = getGame(gameId);
        Player player = game.getPlayerByName(playerName);
        player.addBuildingFromReserve(location, building);
    }

    public void moveBuildingOnBoard(String gameId, String playerName, Location location, Building building) {
        Game game = getGame(gameId);
        Player player = game.getPlayerByName(playerName);
        Location oldLocation = player.getPlayerAlhambra().findLocationOfBuilding(building);
        player.getPlayerAlhambra().moveBuilding(oldLocation, location);
    }
}
