package be.howest.ti.alhambra.applications;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    private Board board = new Board();
    private Building building1 = new Building("pavilion", 6, new Walls(true, true, true, false));
    private Building building2 = new Building("fountain", 0, new Walls(false, false, false, false));
    private Location location1 = new Location(9,1);
    private Location location2 = new Location(5,2);

    @Test
    public void addPawn() {
        board.addBuilding(location1, building1);
        assertEquals(building1, board.findBuildingOnPlace(location1));
        assertEquals(building2.getBuildingType(), board.findBuildingOnPlace(new Location(6,6)).getBuildingType());
        assertEquals(building2.getCost(), board.findBuildingOnPlace(new Location(6,6)).getCost());

    }

    @Test
    public void movePawn() {
        board.addBuilding(location1, building1);
        board.moveBuilding(location1, location2);
        assertEquals(building1, board.findBuildingOnPlace(location2));
    }
}