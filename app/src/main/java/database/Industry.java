package database;

/**
 * Created by Orlandi on 27/11/2016.
 */

public class Industry {
    private Coordinate coordinate;

    public Industry() {
        coordinate = new Coordinate(0, 0);
    }

    public Industry(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public void update(Industry industry) {
        this.coordinate.setLongitude(industry.getCoordinate().getLongitude());
        this.coordinate.setLatitude(industry.getCoordinate().getLatitude());
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }
}
