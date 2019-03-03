package fr.ttvp.visuallifeconfigurator.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Automata implements Serializable {

    private AutomataLight automataLight;
    private List<Cell> cells;
    private boolean isDefault;

    public static final Automata UNKNOWN_AUTOMATA = new Automata(AutomataLight.UNKNOWN_AUTOMATA, false, new ArrayList<Cell>());

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

    public String getName() {
        return this.getAutomataLight().getName();
    }

    public long getId() {
        return this.getAutomataLight().getId();
    }

    public AutomataLight getAutomataLight() {
        return this.automataLight;
    }
}