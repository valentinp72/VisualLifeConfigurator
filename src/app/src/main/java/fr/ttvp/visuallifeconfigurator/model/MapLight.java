package fr.ttvp.visuallifeconfigurator.model;

public class MapLight {
    private String name;
    private String path;
    private long id;

    public MapLight(long id, String name, String path) {
        this.name = name;
        this.path = path + "/map_" + id + ".txt";
        this.id = id;
    }


    public Map getMap() {
        return Map.fromFile(path, this);
    }

    public String setPath(String path) {
        this.path = path;
    }
    public String setName(String name) {
        this.name = name;
    }
    public String getPath() {
        return path;
    }
    public String getName() {
        return name;
    }
}
