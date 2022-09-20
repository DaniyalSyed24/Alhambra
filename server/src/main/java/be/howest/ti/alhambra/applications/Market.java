package be.howest.ti.alhambra.applications;

public class Market {
    private Building blue;
    private Building yellow;
    private Building orange;
    private Building green;


    public Market(){

    }
    
    public Market(Building blue, Building yellow, Building orange, Building green) {
        this.blue = blue;
        this.yellow = yellow;
        this.orange = orange;
        this.green = green;
    }

    public Building getBlue() {
        return blue;
    }

    public void setBlue(Building blue) {
        this.blue = blue;
    }

    public Building getYellow() {
        return yellow;
    }

    public void setYellow(Building yellow) {
        this.yellow = yellow;
    }

    public Building getOrange() {
        return orange;
    }

    public void setOrange(Building orange) {
        this.orange = orange;
    }

    public Building getGreen() {
        return green;
    }

    public void setGreen(Building green) {
        this.green = green;
    }

    public void addCard(Building building, Currency currency){
            switch (currency){
                case BLUE:
                    blue = building;
                    break;
                case GREEN:
                    green = building;
                    break;
                case ORANGE:
                    orange = building;
                    break;
                case YELLOW:
                    yellow = building;
                    break;
                default:
                    throw new IllegalArgumentException("There are 4 building tiles in the shop");
            }
    }


    @Override
    public String toString() {
        return "Market{" +
                "blue=" + blue +
                ", yellow=" + yellow +
                ", orange=" + orange +
                ", green=" + green +
                '}';
    }
}
