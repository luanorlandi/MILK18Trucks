package database;

/**
 * Created by Orlandi on 27/11/2016.
 */

public class Farm {
    private Coordinate coordinate;

    public Farm() {
        coordinate = new Coordinate(0, 0);
    }

    public Farm(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public void update(Farm farm) {
        this.coordinate.setLongitude(farm.getCoordinate().getLongitude());
        this.coordinate.setLatitude(farm.getCoordinate().getLatitude());
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }
}
