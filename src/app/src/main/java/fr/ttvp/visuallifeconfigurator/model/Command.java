package fr.ttvp.visuallifeconfigurator.model;

public class Command {
    private int line;
    private int col;
    private Cell cell;

    public Command(int line, int col, Cell cell) {
        this.line = line;
        this.col = col;
        this.cell = cell;
    }

    public void apply(Cell[][] cells) {
        cells[line][col] = this.cell;
    }
}
