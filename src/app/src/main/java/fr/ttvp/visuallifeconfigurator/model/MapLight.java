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
        return Map.fromFile(path, id);
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
}
// import java.io.Serializable;
// import java.util.Date;

// public class MapLight implements Serializable {

//     private String name;
//     private Date lastPlayed;
//     private String realFileName;

//     public MapLight(String name, Date lastPlayed, String realFileName) {
//         this.name = name;
//         this.lastPlayed = lastPlayed;
//         this.realFileName = realFileName;
//     }

//     public Map getRealMap() {
//         return Map.fromFile(this.realFileName);
//     }

//     public Date getLastPlayed() {
//         return lastPlayed;
//     }

//     public void setLastPlayed(Date lastPlayed) {
//         this.lastPlayed = lastPlayed;
//     }

//     public String getName() {
//         return name;
//     }

//     public void setName(String name) {
//         this.name = name;
//     }
// }
