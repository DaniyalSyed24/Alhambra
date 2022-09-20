package be.howest.ti.alhambra.applications;

public enum BuildingType {
    EMPTY,
    PAVILION,
    SERAGLIO,
    ARCADES,
    CHAMBERS,
    GARDEN,
    TOWER,
    FOUNTAIN;

    public static BuildingType getType(String type) {
        if (type.equals(TOWER.toString())) {
            return TOWER;
        }
        else if (type.equals(PAVILION.toString())) {
            return PAVILION;
        }
        else if(type.equals(SERAGLIO.toString())){
            return SERAGLIO;
        }
        else if(type.equals(ARCADES.toString())){
            return ARCADES;
        }
        else if(type.equals(CHAMBERS.toString())){
            return CHAMBERS;
        }
        else if(type.equals(GARDEN.toString())){
            return GARDEN;
        }
        else if(type.equals(FOUNTAIN.toString())){
            return FOUNTAIN;
        }
        else{
            return EMPTY;
        }
    }

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}


