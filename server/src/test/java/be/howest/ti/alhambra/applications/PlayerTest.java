package be.howest.ti.alhambra.applications;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    private final Player player = new Player("John", "Group12-123");
    private final Building building1 = new Building("pavilion", 6, new Walls(true, false, true, false));
    private final Location location1 = new Location(9,1);
    private final Location location2 = new Location(5, 5);


    @Test
    void addToBuildingReserve() {
        player.addToBuildingReserve(building1);
        assertFalse(player.getBuildingReserve().isEmpty());
        System.out.println(player.getBuildingReserve().size());
    }

    @Test
    void removeFromBuildingReserve() {
        player.addToBuildingReserve(building1);
        player.removeFromBuildingReserve(building1);
        assertTrue(player.getBuildingReserve().isEmpty());
    }


    @Test
    void addBuildingFromReserve() {
        player.addToBuildingReserve(building1);
        assertFalse(player.getBuildingReserve().isEmpty());
        player.addBuildingFromReserve(location2, building1);
        assertTrue(player.getBuildingReserve().isEmpty());
        assertEquals(building1, player.getPlayerAlhambra().findBuildingOnPlace(location2));
    }

    @Test
    void removeBuildingFromBoardToReserve() {
        player.getPlayerAlhambra().addBuilding(location1, building1);
        assertTrue(player.getBuildingReserve().isEmpty());
        player.removeBuildingFromBoardToReserve(location1, building1);
        assertFalse(player.getBuildingReserve().isEmpty());
        assertNull(player.getPlayerAlhambra().findBuildingOnPlace(location1));
        assertEquals(building1, player.getBuildingReserve().get(0));
    }
}