package be.howest.ti.alhambra.applications;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.Json;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Building {
    @JsonProperty("type") String buildingType;
    @JsonProperty("cost") int cost;
    @JsonProperty("walls") Walls walls;

    @JsonCreator
    public Building(@JsonProperty("type") String buildingType, @JsonProperty("cost") int cost, @JsonProperty("walls") Walls walls) {
        this.buildingType = buildingType;
        this.cost = cost;
        this.walls = walls;
    }

    public static List<Building> allBuildings() {
        //abstract all data out of 'buildings.json' file.
        try (InputStream input = Building.class.getResourceAsStream("/buildings.json")) {
            return Arrays.asList(
                    Json.decodeValue(Buffer.buffer(input.readAllBytes()),
                            Building[].class)
            );
        } catch (IOException ex) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Failed loading buildings", ex);
            return Collections.emptyList();
        }
    }

    public String getBuildingType() {
        return buildingType;
    }

    public int getCost() {
        return cost;
    }

    public Walls getWalls() {
        return walls;
    }

    @Override
    public String toString() {
        return "Building{" +
                "buildingType=" + buildingType +
                ", cost=" + cost +
                ", walls=" + walls +
                '}';
    }
}
