package database;

/**
 * Created by Orlandi on 27/11/2016.
 */

public class User {
    private Coordinate coordinate;
    private String name;
    private String description;

    public User() {
        coordinate = new Coordinate(0, 0);
    }

    public User(Coordinate coordinate, String name, String description) {
        this.coordinate = coordinate;
        this.name = name;
        this.description = description;
    }

    public void update(User user) {
        this.coordinate.setLongitude(user.getCoordinate().getLongitude());
        this.coordinate.setLatitude(user.getCoordinate().getLatitude());

        this.name = user.getName();
        this.description = user.getDescription();
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
