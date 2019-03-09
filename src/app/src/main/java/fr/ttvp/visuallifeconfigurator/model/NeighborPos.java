package fr.ttvp.visuallifeconfigurator.model;

import java.io.Serializable;
import java.util.List;

public class NeighborPos implements Serializable {

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

    public boolean equals(NeighborPos other) {
        return other.getDeltaX() == this.deltaX && other.getDeltaY() == this.deltaY;
    }

    public boolean presentInList(List<NeighborPos> list) {
        for(NeighborPos pos : list) {
            if(this.equals(pos))
                return true;
        }
        return false;
    }
}
