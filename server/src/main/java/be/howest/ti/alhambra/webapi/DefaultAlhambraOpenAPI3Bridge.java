package be.howest.ti.alhambra.webapi;

import be.howest.ti.alhambra.applications.*;
import be.howest.ti.alhambra.webapi.exceptions.UrlTokenFormatException;
import io.vertx.core.json.Json;
import be.howest.ti.alhambra.applications.AlhambraController;
import be.howest.ti.alhambra.applications.Building;
import be.howest.ti.alhambra.applications.Game;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.RoutingContext;

import java.util.List;
import java.util.Objects;


public class DefaultAlhambraOpenAPI3Bridge implements AlhambraOpenAPI3Bridge {


    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultAlhambraOpenAPI3Bridge.class);
    private final AlhambraController controller;
    private static final String GAMEIDENTIFIER = "gameId";
    private static final String PLAYERNAME = "playerName";


    public DefaultAlhambraOpenAPI3Bridge(){
        this.controller = new AlhambraController();
    }

    public boolean verifyAdminToken(String token) {
        LOGGER.info("verifyPlayerToken");
        return true;
    }

    public boolean verifyPlayerToken(String token, String gameId, String playerName) {
        LOGGER.info("verifyPlayerToken");

        if (!Objects.equals(playerName, playerName.toLowerCase())) {
            throw new UrlTokenFormatException("Invalid player name");
        }
            return true;
    }

    public List<Building> getBuildings(RoutingContext ctx) {
        LOGGER.info("getBuildings");
        return controller.getBuildings();
    }

    public Object getAvailableBuildLocations(RoutingContext ctx) {
        LOGGER.info("getAvailableBuildLocations");
        return null;
    }

    public Object getBuildingTypes(RoutingContext ctx) {
        LOGGER.info("getBuildingTypes");
        return controller.getBuildingTypes();
    }

    public Object getCurrencies(RoutingContext ctx) {
        LOGGER.info("getCurrencies");
        return controller.getCurrencies();
    }

    public Object getScoringTable(RoutingContext ctx) {
        LOGGER.info("getScoringTable");
        return null;
    }

    public Object getGames(RoutingContext ctx) {
        LOGGER.info("getGames");
        return controller.getGames();
    }

    public Object createGame(RoutingContext ctx) {
        LOGGER.info("createGame");
        return controller.createGame();
    }

    public Object clearGames(RoutingContext ctx) {
        LOGGER.info("clearGames");
        return null;
    }

    public Object joinGame(RoutingContext ctx) {
        LOGGER.info("joinGame");
        String gameId = ctx.request().getParam(GAMEIDENTIFIER);
        String body = ctx.getBodyAsString();
        return controller.joinGame(gameId, body);

    }

    public Object leaveGame(RoutingContext ctx) {
        LOGGER.info("leaveGame");
        String gameId = ctx.request().getParam(GAMEIDENTIFIER);
        String playerName = ctx.request().getParam(PLAYERNAME);
        controller.leaveGame(gameId, playerName);
        return null;
    }


    public Object setReady(RoutingContext ctx) {
        LOGGER.info("setReady");
        String gameId = ctx.request().getParam(GAMEIDENTIFIER);
        String playerName = ctx.request().getParam(PLAYERNAME);
        controller.setReady(gameId, playerName);

        if (Objects.equals(playerName, playerName.toLowerCase())) {
            throw new UrlTokenFormatException("Invalid player name");
        }

        return null;
    }

        public Object setNotReady(RoutingContext ctx) {
        LOGGER.info("setNotReady");
        String gameId = ctx.request().getParam(GAMEIDENTIFIER);
        String playerName = ctx.request().getParam(PLAYERNAME);
        controller.setNotReady(gameId, playerName);
        return null;
    }

    public Object takeMoney(RoutingContext ctx) {
        String stringOfCoin = ctx.getBodyAsString();
        String gameId = ctx.request().getParam(GAMEIDENTIFIER);
        String playerName = ctx.request().getParam(PLAYERNAME);
        Coin coin = Json.decodeValue(stringOfCoin, Coin.class);

        LOGGER.info("takeMoney");


        controller.takeCard(gameId, playerName, coin);
        controller.finishTurn(gameId);

        return controller.getGame(gameId);
    }

    public Object buyBuilding(RoutingContext ctx) {
        String gameId = ctx.request().getParam(GAMEIDENTIFIER);
        String playerName = ctx.request().getParam(PLAYERNAME);

        String body = ctx.getBodyAsString();
        Coin[] coins = Json.decodeValue(body, Coin[].class);

        List<Coin> chosenCards = controller.convertToCoinsToList(coins);
        Currency currency = Json.decodeValue(body, Currency.class);

        controller.buyBuilding(gameId, playerName, currency, chosenCards);
        controller.finishTurn(gameId);

        LOGGER.info("buyBuilding");

        return controller.getGame(gameId);
    }


    public Object redesign(RoutingContext ctx) {
        String body = ctx.getBodyAsString();
        Location location = Json.decodeValue(body, Location.class);
        Building building = Json.decodeValue(body, Building.class);

        String gameId = ctx.request().getParam(GAMEIDENTIFIER);
        String playerName = ctx.request().getParam(PLAYERNAME);

        controller.moveBuildingOnBoard(gameId, playerName, location, building);
        LOGGER.info("redesign");

        return controller.getGame(gameId);
    }

    public Object build(RoutingContext ctx) {
        String gameId = ctx.request().getParam(GAMEIDENTIFIER);
        String playerName = ctx.request().getParam(PLAYERNAME);

        String body = ctx.getBodyAsString();
        Location location = Json.decodeValue(body, Location.class);
        Building building = Json.decodeValue(body, Building.class);

        controller.buildBuilding(gameId, playerName, location, building);

        LOGGER.info("build");

        return controller.getGame(gameId);
    }

    public Game getGame(RoutingContext ctx) {
        LOGGER.info("getGame");
        String gameId = ctx.request().getParam(GAMEIDENTIFIER);
        return controller.getGame(gameId);
    }

}
