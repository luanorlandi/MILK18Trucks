package database;

/**
 * Created by Orlandi on 27/11/2016.
 */

public class User {
    private Coordinate coordinate;

    public User() {
        coordinate = new Coordinate(0, 0);
    }

    public User(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public void update(User user) {
        this.coordinate.setLongitude(user.getCoordinate().getLongitude());
        this.coordinate.setLatitude(user.getCoordinate().getLatitude());
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }
}
