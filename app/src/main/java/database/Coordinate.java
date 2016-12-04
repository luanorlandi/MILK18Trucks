package database;

/**
 * Created by Orlandi on 31-Oct-16.
 */

public class Coordinate {

    private double latitude;
    private double longitude;

    public Coordinate() {

    }

    public Coordinate(double longitude, double latitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
