package fr.ttvp.visuallifeconfigurator.model;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

public class Simulator {

    //    private Automata automata;
    private List<SimulatorListener> listeners;
    private Cell[][] cells;
    private int nLines;
    private int nCols;
    private SimulatorThread playingLoop;

    public Simulator(Automata automata, Map startingMap) {
//        this.automata = automata;
        this.nLines = startingMap.getLines();
        this.nCols = startingMap.getCols();
        this.cells = startingMap.forAutomata(automata);
        this.listeners = new ArrayList<>();
        this.playingLoop = new SimulatorThread(this);
        this.playingLoop.start();
    }

    public void step() {
        final List<Command> cmds = new ArrayList<>();
        for (int i = 0; i < this.nLines; i++)
            for (int j = 0; j < this.nCols; j++)
                this.addCommand(cmds, i, j);


        // notify begin
        for (Command cmd : cmds) cmd.apply(this.cells);
        // notify end

        this.warnChangeListeners();
    }

    public void pause() {
        this.playingLoop.pause();
    }

    public void play() {
        this.playingLoop.play();
    }

    private void addCommand(List<Command> cmds, int line, int col) {
        final Cell cell = this.cells[line][col];
        final List<Cell> toCount = cell.getCellsToCount();
        final List<NeighborPos> neighs = cell.getNeighbours();

        int neighborCounter = 0;
        for (NeighborPos neigh : neighs) {
            final int nl = getLine(line + neigh.getDeltaY());
            final int nc = getCol(col + neigh.getDeltaX());
            final Cell cur = this.cells[nl][nc];
            if (toCount.contains(cur)) neighborCounter += 1;
        }

        final Cell newCell = cell.getTransitions()[neighborCounter];

        if (cell != newCell) cmds.add(new Command(line, col, newCell));

    }

    //    calculate the real index of the col (wrapping around if needed)
    private int getCol(int col) {
        if (col >= nCols) return col % nCols;
        if (col < 0) return nCols + col;
        return col;
    }

    //    calculate the real index of the line (wrapping around if needed)
    private int getLine(int line) {
        if (line >= nLines) return line % nLines;
        if (line < 0) return nLines + line;
        return line;
    }

    public Map getMap() {
        return Map.fromCells(nLines, nCols, cells);
    }

    // add a listener
    public void addListener(SimulatorListener listener) {
        this.listeners.add(listener);
    }

    // remove a specific listener
    public void removeListener(SimulatorListener listener) {
        this.listeners.remove(listener);
    }

    // prevent the listeners that a changed in the map occured
    private void warnChangeListeners() {
        for(SimulatorListener simulatorListener : this.listeners) {
            simulatorListener.updated();
        }
    }

}
