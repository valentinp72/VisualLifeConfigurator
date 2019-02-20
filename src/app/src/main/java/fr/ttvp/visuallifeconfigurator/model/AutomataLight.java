package fr.ttvp.visuallifeconfigurator.model;

public class AutomataLight {

    private static long idTotal = 0;

    private final String name;
    private final long id;

    public AutomataLight(String name, long id) {
        this.name = name;
        this.id = id;
    }

    public AutomataLight(String name) {
        this(name, idTotal++);
    }

    public Automata getRealAutomata() {
        return null;
        //return new Automata();
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }
}
