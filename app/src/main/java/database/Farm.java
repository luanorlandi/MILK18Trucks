package database;

/**
 * Created by Orlandi on 27/11/2016.
 */

public class Farm {
    private Coordinate coordinate;
    private String name;
    private String description;

    public Farm() {
        coordinate = new Coordinate(0, 0);
        name = "";
        description = "";
    }

    public Farm(Coordinate coordinate, String name, String description) {
        this.coordinate = coordinate;
        this.name = name;
        this.description = description;
    }

    public void update(Farm farm) {
        this.coordinate.setLongitude(farm.getCoordinate().getLongitude());
        this.coordinate.setLatitude(farm.getCoordinate().getLatitude());

        this.name = farm.getName();
        this.description = farm.getDescription();
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
