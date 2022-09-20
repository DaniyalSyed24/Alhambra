package be.howest.ti.alhambra.applications;

public class Board {
    private Building[][] alhambraBoard;

    public Board() {
        int size = 11;
        this.alhambraBoard = new Building[size][size];

        for(int row = 0; row < 11; row ++){
            for(int col = 0; col < 11; col ++){
                if(row == 6 && col == 6){
                    alhambraBoard[row][col] = new Building("fountain", 0, new Walls(false, false, false, false));
                }
            }
        }
    }

    public void addBuilding(Location location, Building building){
        int row = location.getRow();
        int col = location.getCol();
        if(alhambraBoard[row][col] == null ){
                alhambraBoard[row][col] = building;
        }
        else{
            throw new IllegalArgumentException("There already is a building on this place!");
        }
    }

    public void moveBuilding(Location oldLocation, Location newLocation){
        Building building = findBuildingOnPlace(oldLocation);
        removeBuilding(oldLocation);
        addBuilding(newLocation, building);
    }

    public void removeBuilding(Location location) {
        alhambraBoard[location.getRow()][location.getCol()] = null;
    }

    public Building findBuildingOnPlace(Location location) {
        return alhambraBoard[location.getRow()][location.getCol()];
    }

    public Location findLocationOfBuilding(Building building) {
        Location location = null;
        for (int row = 0; row < 11; row++) {
            for (int col = 0; col < 11; col++) {
                if (alhambraBoard[row][col].equals(building)) {
                    location = new Location(row, col);
                }
            }
        }
        return location;
    }

    public boolean checkIfPlaceIsEmpty(Location location){
        return alhambraBoard[location.getRow()][location.getCol()] == null;
    }
}
