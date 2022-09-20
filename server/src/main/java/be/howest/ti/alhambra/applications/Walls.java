package be.howest.ti.alhambra.applications;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Walls {
    @JsonProperty("north") final boolean north;
    @JsonProperty("east") final boolean east;
    @JsonProperty("south") final boolean south;
    @JsonProperty("west") final boolean west;


    @JsonCreator
    public Walls(@JsonProperty("north") boolean north,@JsonProperty("east") boolean east,@JsonProperty("south") boolean south,@JsonProperty("west") boolean west) {
        this.north = north;
        this.east = east;
        this.south = south;
        this.west = west;
    }

    public boolean isNorth() {
        return north;
    }

    public boolean isEast() {
        return east;
    }

    public boolean isSouth() {
        return south;
    }

    public boolean isWest() {
        return west;
    }
}
