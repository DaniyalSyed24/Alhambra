package be.howest.ti.alhambra.logic;

import be.howest.ti.alhambra.applications.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JsonTest {

    @Test
    void coin() {
        // Create a coin ...
        Coin coin = new Coin(Currency.ORANGE, 10);

        // Turn it into a JsonObject
        JsonObject coinAsJsonObject = JsonObject.mapFrom(coin);

        // Assert that this object has the expected properties
        assertTrue(coinAsJsonObject.containsKey("currency"));
        assertTrue(coinAsJsonObject.containsKey("amount"));

        // Assert that you can convert it back to the same coin.
        assertEquals(coin, coinAsJsonObject.mapTo(Coin.class));

        // Assert that you can go back and forth between Java-objects and Json (strings)
        assertEquals(coin, Json.decodeValue(Json.encode(coin), Coin.class));
    }

    @Test
    void building() {
        Walls walls = new Walls(true, true, true, false);
        Building building = new Building("pavilion", 2, walls);

        JsonObject buildingAsJsonObject = JsonObject.mapFrom(building);

        assertTrue(buildingAsJsonObject.containsKey("type"));
        assertTrue(buildingAsJsonObject.containsKey("cost"));

        assertEquals(building.getBuildingType(), buildingAsJsonObject.mapTo(Building.class).getBuildingType());
        assertEquals(building.getCost(), buildingAsJsonObject.mapTo(Building.class).getCost());

        assertEquals(building.getBuildingType(), Json.decodeValue(Json.encode(building), Building.class).getBuildingType());
        assertEquals(building.getCost(), Json.decodeValue(Json.encode(building), Building.class).getCost());
    }
 }