package fr.ttvp.visuallifeconfigurator.model;

import java.util.List;

public class Command {
    private int line;
    private int col;
    private Cell cell;

    public Command(int line, int col, Cell cell) {
        this.line = line;
        this.col = col;
        this.cell = cell;
    }

    public void apply(List<List<Cell>> cells) {
        cells.get(line).set(col, this.cell);
    }
}
