package fr.ttvp.visuallifeconfigurator.model;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.content.res.AssetManager;

public class Persitance {

    private static Persitance instance;
    private AssetManager assetManager;

    private Persitance() {

    }

    public static Persitance getInstance() {
        if(instance == null)
            instance = new Persitance();
        return instance;
    }

    public String loadFile(String inFile) {
        String tContents = "";
        try {
            InputStream stream = this.assetManager.open(inFile);
            int size = stream.available();
            byte[] buffer = new byte[size];
            stream.read(buffer);
            stream.close();
            tContents = new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tContents;
    }

    public List<AutomataLight> getAutomataLights() {
        List<AutomataLight> list = new ArrayList<>();
        String file = loadFile("automata_ids.txt");

        final Pattern p = Pattern.compile("(\\d+) (.+)");

        for (String s : file.split("\n")) {
            System.out.println(s);
            Matcher m = p.matcher(s);
            if (m.matches()){
                String id = m.group(1);
                String name = m.group(2);
                System.out.println(id);
                System.out.println(name);
            }

        }

        return list;
    }


    public Automata getAutomata(AutomataLight automataLight) {
        return null;
    }

    public void writeAutomata(Automata automata) {

    }
    
    public void setAssetManager(AssetManager assetManager) {
        this.assetManager = assetManager;
    }
}
