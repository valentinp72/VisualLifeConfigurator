package fr.ttvp.visuallifeconfigurator.model;

import android.graphics.Color;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

public class Cell implements Serializable {

    public static final int NEIGHBOURS_SIZE_HORIZONTAL = 5;
    public static final int NEIGHBOURS_SIZE_VERTICAL   = 5;

    private int id;
    private String name;
    private String color;
    private List<NeighborPos> neighbours;
    private List<Cell> cellsToCount;
    private Cell[] transitions;
    private boolean defaultCell;
    private Automata originAutomata;

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

    public int getColorInt() {
        return Color.parseColor(this.color);
    }

    public int getMatchingColor() {
        final int middle  = (255 * 3) / 2;
        final int current = this.getColorInt();
        if(Color.red(current) + Color.green(current) + Color.blue(current) < middle) {
            return Color.WHITE;
        }
        return Color.BLACK;
    }

    public List<NeighborPos> getNeighboursPosForSizes() {
        List<NeighborPos> list = new ArrayList<>();
        int middleX = NEIGHBOURS_SIZE_HORIZONTAL / 2;
        int middleY = NEIGHBOURS_SIZE_VERTICAL / 2;

        for(int y = 0 ; y < NEIGHBOURS_SIZE_VERTICAL ; y++) {
            for(int x = 0 ; x < NEIGHBOURS_SIZE_HORIZONTAL ; x++) {
                NeighborPos nPos = new NeighborPos(x - middleX, y - middleY);
                list.add(nPos);
            }
        }
        return list;
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

    public Automata getOriginAutomata() {
        return originAutomata;
    }

    public void setOriginAutomata(Automata originAutomata) {
        this.originAutomata = originAutomata;
    }

    public void setNeighbourStatus(NeighborPos nPos, boolean isChecked) {
        List<Cell> trans = new ArrayList<>(Arrays.asList(transitions));

        if(isChecked) {
            if(!nPos.presentInList(this.getNeighbours())) {
                this.neighbours.add(nPos);
                trans.add(this);

            }
        }
        else {
            ListIterator<NeighborPos> listIterator = neighbours.listIterator();
            while(listIterator.hasNext()) {
                if(listIterator.next().equals(nPos)) {
                    listIterator.remove();
                    trans.remove(trans.size() - 1);
                }
            }
        }
        this.transitions = trans.toArray(new Cell[0]);
    }

    public void setNeighbours(List<NeighborPos> neighbours) {
        this.neighbours = neighbours;
    }
}
