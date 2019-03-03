package fr.ttvp.visuallifeconfigurator.model;

import java.io.Serializable;

class NeighborPos implements Serializable {

    private int deltaX;
    private int deltaY;

    public NeighborPos(int deltaX, int deltaY) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }

    public String toString() {
        return "dX = " + deltaX + " | dY = " + deltaY;
    }

    public int getDeltaY() {
        return deltaY;
    }

    public int getDeltaX() {
        return deltaX;
    }
}
