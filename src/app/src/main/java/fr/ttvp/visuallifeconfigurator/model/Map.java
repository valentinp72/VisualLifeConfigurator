package fr.ttvp.visuallifeconfigurator.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Map implements Serializable {
    private int[][] indexes;
    private int nLine;
    private int nCol;
    private long id = -1;

    public Map(int nLine, int nCol, int[][] indexes) {
        this.nLine = nLine;
        this.nCol = nCol;
        this.indexes = indexes;
    }

    public static Map fromCells(int nLine, int nCol, Cell[][] cells) {
        int[][] indexes = new int[nLine][nCol];
        for (int i = 0; i < nLine; i++)
            for (int j = 0; j < nCol; j++)
                indexes[i][j] = cells[i][j].getId();
        return new Map(nLine, nCol, indexes);
    }

    public static Map fromFile(String filename, long id) {
        Persitance p = Persitance.getInstance();
        List<String> lines = new ArrayList<String>(
                Arrays.asList(p.loadFile(filename).split("\n"))
        );
        String[] dimensions = lines.remove(0).split(" ");
        int nLine = Integer.parseInt(dimensions[0]);
        int nCol = Integer.parseInt(dimensions[1]);

        int[][] indexes = new int[nLine][nCol];

        for (int i = 0; i < nLine; i++) {
            String[] line = lines.get(i).split(" ");
            for (int j = 0; j < nCol; j++) {
                indexes[i][j] = Integer.parseInt(line[j]);
            }
        }
        Map m = new Map(nLine, nCol, indexes);
        m.id = id;

        return m;
    }

    public Cell[][] forAutomata(Automata a) {

        Cell[][] cells = new Cell[nLine][nCol];

        List<Cell> automataCells = a.getCells();
        final int nCell = automataCells.size();

        // pour pouvoir acceder rapidement a une cellule a partir de son id
        Cell[] automataCellsById = new Cell[nCell];
        Cell defaultCell = automataCells.get(0);
        for (int i = 0; i < nCell; i++) {
            Cell c = automataCells.get(i);
            automataCellsById[c.getId()] = c;
            if (c.isDefaultCell()) defaultCell = c;
        }

        for (int i = 0; i < nLine; i++)
            for (int j = 0; j < nCol; j++) {
                int index = indexes[i][j];
                cells[i][j] = (index < nCell) ? automataCellsById[index] : defaultCell;
            }

        return cells;
    }

    public int getCellID(int row, int col) {
        return this.indexes[row][col];
    }

    public void setWidthHeightRatio(int ratio) {
        // TODO: coder cette fonction de sorte qu'elle adapte la forme de la map
        // => ajouter ou enlever des cellules sur les cotés
        // a voir si ça se trouve pas plutot dans Simulator
    }

    public void bigger() {
        // TODO: augmenter la taille de la map tout en suivant le ratio
        // a voir si ça se trouve pas plutot dans Simulator
    }

    public void smaller() {
        // TODO: réduire la taille de la map tout en suivant le ratio
        // a voir si ça se trouve pas plutot dans Simulator
    }

    public int getCols() {
        return nCol;
    }
    public int getLines() {
        return nLine;
    }
}
