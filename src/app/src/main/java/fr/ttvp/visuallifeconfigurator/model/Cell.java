package fr.ttvp.visuallifeconfigurator.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cell implements Serializable {

    private int id;
    private String name;
    private String color;
    private List<NeighborPos> neighbours;
    private List<Cell> cellsToCount;
    private Cell[] transitions;
    private boolean defaultCell;

    public Cell() {
        this.name  = "Undefined";
        this.color = "#ffffff";
        this.defaultCell  = false;
        this.neighbours   = new ArrayList<>();
        this.cellsToCount = new ArrayList<>();
    }

    public String toString() {
        return name + ": " + color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<NeighborPos> getNeighbours() {
        return neighbours;
    }

    public List<Cell> getCellsToCount() {
        return cellsToCount;
    }

    public Cell[] getTransitions() {
        return transitions;
    }

    public void setTransitions(Cell[] transitions) {
        this.transitions = transitions;
    }

    public boolean isDefaultCell() {
        return defaultCell;
    }

    public void setDefaultCell(boolean defaultCell) {
        this.defaultCell = defaultCell;
    }
}
