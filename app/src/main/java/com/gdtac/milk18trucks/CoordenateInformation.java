package com.gdtac.milk18trucks;

/**
 * Created by Orlandi on 31-Oct-16.
 */

public class CoordenateInformation {

    private float coordX;
    private float coordY;

    private TestClass test;
    private TestClass test2;

    CoordenateInformation(float x, float y) {
        this.coordX = x;
        this.coordY = y;

        this.test = new TestClass("Luan", 22);
        this.test2 = new TestClass("Bruno", 23);
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

    public TestClass getTest() {
        return test;
    }

    public void setTest(TestClass test) {
        this.test = test;
    }

    public TestClass getTest2() {
        return test2;
    }

    public void setTest2(TestClass test2) {
        this.test2 = test2;
    }
}
