package be.howest.ti.alhambra.applications;

import be.howest.ti.alhambra.logic.exceptions.AlhambraEntityNotFoundException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;


    public class Game {

        @JsonProperty("gameId") final String gameId;
        private List<Player> players;
        private Market market;
        private MoneyBank moneybank;
        private int round;
        private PlayersTurn playersTurn;
        private String startPlayerToken;
        private boolean started;
        private boolean ended;

        @JsonCreator
        public Game(@JsonProperty("gameId") String gameId) {
            this.market = new Market();
            this.moneybank = new MoneyBank();
            this.gameId = gameId;
            this.players = new ArrayList<>();
            this.round = 0;
            this.playersTurn = new PlayersTurn(players);

            if (!gameId.equals("123")) {
                throw new AlhambraEntityNotFoundException("This game does not exist!");
            }
        }

        public List<Player> getPlayers() {
            return players;
        }

        @JsonProperty("gameId")
        public String getGameId() {
            return gameId;
        }

        public Market getMarket() {
            return market;
        }

        public MoneyBank getMoneyBank() {
            return moneybank;
        }

        public PlayersTurn getPlayersTurn() {
            return playersTurn;
        }

        public Player getPlayerByName(String name) {
            Player result = null;
            for (Player player : players) {
                if (player.getNickname().equals(name)) {
                    result = player;
                }
            }
            return result;
        }

        public Player getPlayerByToken(String token) {
            Player result = null;
            for (Player player : players) {
                if (player.getToken().equals(token)) {
                    result = player;
                }
            }
            return result;
        }

        public Player getCurrentPlayer() {
            return getPlayerByToken(playersTurn.getCurrentPlayer());
        }

        public void endTurnCurrentPlayer() {
            getCurrentPlayer().endTurn();
            round = playersTurn.setNextPlayer(round, startPlayerToken);
        }

        private void setupPlayers() {
            int lowestAmountOfCards = 1000;
            String token = "";
            for (Player player : players) {
                if (player.getPlayerDeck().size() < lowestAmountOfCards) {
                    token = player.getToken();
                }
            }
            playersTurn.setStart(token);
            startPlayerToken = token;
            getCurrentPlayer().startTurn();
        }

        public void leaveGame(Player player) {
            players.removeIf(playerInList -> playerInList.equals(player));
        }

        public void checkStart() {
            boolean check = true;
            for (Player player : players) {
                if (!player.isReady()) {
                    check = false;
                    break;
                }
            }
            if (check) {
                started = true;
                setupPlayers();
            }
        }

        public boolean isStarted() {
            return started;
        }

        public boolean isEnded() {
            return ended;
        }

        public void setReady(String playerName) {
            for (Player player : players) {
                if (player.getNickname().equals(playerName)) {
                    player.setReady(true);
                }
            }
        }

        public void setNotReady(String playerName) {
            for (Player player : players) {
                if (player.getNickname().equals(playerName)) {
                    player.setReady(false);
                }
            }
        }

        public void addPlayer(Player speler) {
            players.add(speler);
        }
    }
