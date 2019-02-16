package fr.ttvp.visuallifeconfigurator.model;

import java.util.ArrayList;
import java.util.List;

public class Automata {

    private AutomataLight automata;
    private List<Cell> cells;
    private List<Map>  maps;
    private boolean isDefault;

    public Automata() {
        this.automata = automata;
        this.cells    = new ArrayList<>();
        this.maps     = new ArrayList<>();
        this.isDefault = false;
    }

    public Automata(long id) {
        // chargement d'un automate à partir d'un id,
        // les données seront recherchés dans les fichiers

    }

    public void serialize() {

    }

}
