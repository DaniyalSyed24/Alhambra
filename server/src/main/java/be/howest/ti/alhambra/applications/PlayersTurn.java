package be.howest.ti.alhambra.applications;

import java.util.ArrayList;
import java.util.List;

public class PlayersTurn {
    private final List<String> listOfPlayers;
    private int currentPlayer = 0;

    public PlayersTurn(List<Player> listOfPlayers){
        this.listOfPlayers = convertToTokens(listOfPlayers);
    }

    public List<String> convertToTokens(List<Player> players){
        List<String> playerTokens = new ArrayList<>();
        for(Player player: players){
            playerTokens.add(player.getToken());
        }

        return playerTokens;
    }

    public String getCurrentPlayer(){
        return listOfPlayers.get(currentPlayer);
    }

    public int setNextPlayer(int round, String startPersonToken){
        if (getCurrentPlayer().equals(startPersonToken)){
            round++;
        }

        if (currentPlayer < listOfPlayers.size()){
            currentPlayer++;
        }
        else{
            currentPlayer = 0;
        }
        return round;
    }

    public void setStart(String token){
        currentPlayer = listOfPlayers.indexOf(token);
    }


    public void leaveListOfPlayers(String token){
        listOfPlayers.removeIf(tokenInList -> tokenInList.equals(token));
    }
}
