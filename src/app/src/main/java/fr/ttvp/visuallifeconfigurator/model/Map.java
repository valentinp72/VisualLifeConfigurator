package fr.ttvp.visuallifeconfigurator.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Map implements Serializable {
    private int[][] indexes;
    private int nLine;
    private int nCol;
    private MapLight mapLight;

    public Map(int nLine, int nCol, int[][] indexes, MapLight mapLight) {
        this.nLine = nLine;
        this.nCol = nCol;
        this.indexes = indexes;
        this.mapLight = mapLight;
    }

    public static Map fromCells(int nLine, int nCol, Cell[][] cells, MapLight mapLight) {
        int[][] indexes = new int[nLine][nCol];
        for (int i = 0; i < nLine; i++)
            for (int j = 0; j < nCol; j++)
                indexes[i][j] = cells[i][j].getId();
        return new Map(nLine, nCol, indexes, mapLight);
    }

    public static Map fromFile(String filename, long id, MapLight mapLight) {
        Persistance p = Persistance.getInstance();
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
        Map m = new Map(nLine, nCol, indexes, mapLight);

        return m;
    }

    public String toFile() {
        StringBuilder str = new StringBuilder();

        str.append(nLine + " " + nCol + "\n");
        for(int i = 0 ; i < nLine ; i++) {
            for(int j = 0 ; j < nCol ; j++)
                str.append(indexes[i][j] + " ");
            str.setLength(str.length() - 1);
            str.append("\n");
        }

        return str.toString();
    }

    public static Map fromRandom(Automata a, int nLine, int nCol, MapLight mapLight) {
        List<Cell> allCells = a.getCells();
        Random rand = new Random();
        Cell[][] cells = new Cell[nLine][nCol];
        for(int i = 0 ; i < nLine ; i++)
            for(int j = 0 ; j < nCol ; j++)
                cells[i][j] = allCells.get(rand.nextInt(allCells.size()));
        return Map.fromCells(nLine, nCol, cells, mapLight);
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


    public int getCols() {
        return nCol;
    }
    public int getLines() {
        return nLine;
    }

    public void save(AutomataLight automataLight) {
        Persistance.getInstance().saveMap(automataLight, this);
    }

    public MapLight getMapLight() {
        return mapLight;
    }
}
