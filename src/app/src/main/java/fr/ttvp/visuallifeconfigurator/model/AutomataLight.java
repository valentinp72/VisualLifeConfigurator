package fr.ttvp.visuallifeconfigurator.model;

public class AutomataLight {

    private static long idTotal = 0;

    private String name;
    private long id;

    public AutomataLight(String name, long id) {
        this.name = name;
        this.id = id;
    }

    public AutomataLight(String name) {
        this(name, idTotal++);
    }

    public Automata getRealAutomata() {
        return new Automata();
    }

    public String getName() {
        return name;
    }
}
