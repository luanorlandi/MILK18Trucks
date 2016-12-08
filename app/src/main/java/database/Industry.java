package database;

/**
 * Created by Orlandi on 27/11/2016.
 */

public class Industry {
    private Coordinate coordinate;
    private String name;
    private String description;

    public Industry() {
        coordinate = new Coordinate(0, 0);
        name = "";
        description = "";
    }

    public Industry(Coordinate coordinate, String name, String description) {
        this.coordinate = coordinate;
        this.name = name;
        this.description = description;
    }

    public void update(Industry industry) {
        this.coordinate.setLongitude(industry.getCoordinate().getLongitude());
        this.coordinate.setLatitude(industry.getCoordinate().getLatitude());

        this.name = industry.getName();
        this.description = industry.getDescription();
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
