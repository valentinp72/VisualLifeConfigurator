package fr.ttvp.visuallifeconfigurator.model;

import java.io.Serializable;

public class MapLight implements Serializable {

    public static long idTotal = 0;

    private String name;
    private String path;
    private long id;

    public MapLight(long id, String name, String path) {
        this.name = name;
        this.path = path + "/map_" + id + ".txt";
        this.id = id;
        idTotal = idTotal + 1;
    }

    public Map getMap() {
        return Map.fromFile(path, id, this);
    }

    public void setPath(String path) {
        this.path = path;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPath() {
        return path;
    }
    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }
}
