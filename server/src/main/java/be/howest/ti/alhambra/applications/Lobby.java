package be.howest.ti.alhambra.applications;

import java.util.ArrayList;
import java.util.List;

public class Lobby {

    private List<Game> games;
    private  int gameNumber;

    public Lobby(){
        gameNumber = 0;
        games= new ArrayList<>();
    }

    public List<Game> getGames() {
        return games;
    }

    public String createGame() {
        gameNumber++;
        String id= ""+gameNumber;
        games.add(new Game(id));
        return id;
    }

    public String joinGame(String name, String gameId) {
        String token = gameId + "+" + name;

        Player speler = new Player(name, gameId);
        for(Game game : games){
            if(game.getGameId().equals(gameId)){
                game.addPlayer(speler);
            }
        }
        return token;
    }

    public void leaveGame (String gameId, String playerName) {
        for (Game game : games) {
            if (game.getGameId().equals(gameId)) {
                Player player = game.getPlayerByName(playerName);
                game.leaveGame(player);
            }
        }
    }

    public void clearGames() {
        gameNumber = 0;
        for(Game game : games){
            games.remove(game);
        }
    }

    public void setReady(String gameId, String playerName) {
        for(Game game : games){
            if(game.getGameId().equals(gameId)){
                game.setReady(playerName);
                game.checkStart();
            }
        }
    }

    public void setNotReady(String gameId, String playerName) {
        for(Game game : games){
            if(game.getGameId().equals(gameId)){
                game.setNotReady(playerName);
            }
        }
    }
}
