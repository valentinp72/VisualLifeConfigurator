package fr.ttvp.visuallifeconfigurator.model;

import java.io.Serializable;
import java.util.Date;

public class MapLight implements Serializable {

    private String name;
    private Date lastPlayed;
    private String realFileName;

    public MapLight(String name, Date lastPlayed, String realFileName) {
        this.name = name;
        this.lastPlayed = lastPlayed;
        this.realFileName = realFileName;
    }

    public Map getRealMap() {
        return Map.fromFile(this.realFileName);
    }

    public Date getLastPlayed() {
        return lastPlayed;
    }

    public void setLastPlayed(Date lastPlayed) {
        this.lastPlayed = lastPlayed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
