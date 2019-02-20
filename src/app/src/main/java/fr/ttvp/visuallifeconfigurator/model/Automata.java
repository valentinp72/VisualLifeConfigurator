package fr.ttvp.visuallifeconfigurator.model;

import java.util.ArrayList;
import java.util.List;

public class Automata {

    private AutomataLight automataLight;
    private List<Cell> cells;
    private boolean isDefault;

    public Automata(AutomataLight automataLight, boolean isDefault, List<Cell> cells) {
        this.automataLight = automataLight;
        this.cells         = cells;
        this.isDefault     = isDefault;
    }

    public String toString() {
        return "Automata: " + automataLight.toString() + " " + cells + " " + isDefault;
    }

    public List<Cell> getCells() {
        return cells;
    }

}
