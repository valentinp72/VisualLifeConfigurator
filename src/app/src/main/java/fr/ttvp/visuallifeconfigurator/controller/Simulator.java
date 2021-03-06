package fr.ttvp.visuallifeconfigurator.controller;

import java.util.ArrayList;
import java.util.List;

import fr.ttvp.visuallifeconfigurator.model.Automata;
import fr.ttvp.visuallifeconfigurator.model.Cell;
import fr.ttvp.visuallifeconfigurator.model.Command;
import fr.ttvp.visuallifeconfigurator.model.Map;
import fr.ttvp.visuallifeconfigurator.model.MapLight;
import fr.ttvp.visuallifeconfigurator.model.NeighborPos;

public class Simulator {

    //    private Automata automata;
    private List<SimulatorListener> listeners;
    private List<List<Cell>> cells = new ArrayList<List<Cell>>();
    private Cell defaultCell;
    private int nLines;
    private int nCols;
    private MapLight mapLight;
    private SimulatorThread playingLoop;
    private float goalRatio = 1;
    private float ratio;

    public Simulator(Automata automata, Map startingMap) {
//        this.automata = automata;
        this.mapLight = startingMap.getMapLight();
        this.nLines = startingMap.getLines();
        this.nCols = startingMap.getCols();
        Cell[][] tabcell = startingMap.forAutomata(automata);

        this.listeners = new ArrayList<>();
        this.playingLoop = new SimulatorThread(this);
        this.playingLoop.start();

        for (int i = 0; i < this.nLines; i++) {
            List<Cell> l = new ArrayList<Cell>();
            for (int j = 0; j < this.nCols; j++) {
                l.add(tabcell[i][j]);
            }
            cells.add(l);
        }


        for (Cell c: automata.getCells()) {
            if (c.isDefaultCell()) this.defaultCell = c;
        }
    }

    private void calcRatio() {
        this.ratio = (float) nCols / (float) nLines;
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
        final Cell cell = this.cells.get(line).get(col);
        final List<Cell> toCount = cell.getCellsToCount();
        final List<NeighborPos> neighs = cell.getNeighbours();

        int neighborCounter = 0;
        for (NeighborPos neigh : neighs) {
            final int nl = getLine(line + neigh.getDeltaY());
            final int nc = getCol(col + neigh.getDeltaX());
            final Cell cur = this.cells.get(nl).get(nc);
            for (Cell c: toCount) {
                if (c.getId() == cur.getId()) {
                    neighborCounter += 1;
                    break;
                }
            }
        }

        final Cell newCell = cell.getTransitions()[neighborCounter];

        if (cell != newCell) cmds.add(new Command(line, col, newCell));

    }

    //    calculate the real index of the col (wrapping around if needed)
    private int getCol(int col) {
        if (col >= 0 && col < nCols) return col;
        else if (col >= nCols) return col % nCols;
        return getCol(nCols + col);
    }

    //    calculate the real index of the line (wrapping around if needed)
    private int getLine(int line) {
        if (line >= 0 && line < nLines) return line;
        else if (line >= nLines) return line % nLines;
        return getLine(nLines + line);
    }

    public Map getMap() {
        Cell[][] mat = new Cell[nLines][nCols];
        for (int i = 0; i < nLines; i++) {

            for (int j = 0; j < nCols; j++) {
                mat[i][j] = cells.get(i).get(j);
            }
        }
        return Map.fromCells(nLines, nCols, mat, mapLight);
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

    // le ratio augmente avec la largeur
    public void setWidthHeightRatio(float ratio) {
        this.goalRatio = ratio;
    }

    public void bigger() {
        calcRatio();
        if (this.ratio < this.goalRatio) {
            this.addColumn();
        } else this.addLine();
    }

    public void smaller() {
        // TODO: réduire la taille de la map tout en suivant le ratio
        calcRatio();
        if (this.ratio < this.goalRatio) {
            this.removeLine();
        } else this.removeColumn();
    }

    private void removeColumn() {
        if (nCols == 1) return;
        nCols -= 1;
        for (List<Cell> line : cells) {
            line.remove(nCols);
        }
    }
    private void removeLine() {
        if (nLines == 1) return;
        nLines -= 1;
        cells.remove(nLines);
    }
    private void addColumn() {
        for (List<Cell> line : cells) {
            line.add(defaultCell);
        }
        nCols += 1;
    }
    private void addLine() {
        List<Cell> l = new ArrayList<Cell>();
        for (int i = 0; i < nCols; i++) {
            l.add(defaultCell);
        }
        cells.add(l);
        nLines += 1;
    }
}
