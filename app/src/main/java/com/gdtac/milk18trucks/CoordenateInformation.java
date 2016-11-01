package com.gdtac.milk18trucks;

/**
 * Created by Orlandi on 31-Oct-16.
 */

public class CoordenateInformation {
    private float coordX;
    private float coordY;

    CoordenateInformation(float x, float y) {
        this.coordX = x;
        this.coordY = y;
    }

    public float getCoordX() {
        return coordX;
    }

    public void setCoordX(float coordX) {
        this.coordX = coordX;
    }

    public float getCoordY() {
        return coordY;
    }

    public void setCoordY(float coordY) {
        this.coordY = coordY;
    }
}
