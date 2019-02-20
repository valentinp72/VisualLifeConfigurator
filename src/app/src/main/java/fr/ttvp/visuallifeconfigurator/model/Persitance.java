package fr.ttvp.visuallifeconfigurator.model;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.res.AssetManager;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

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
            Matcher m = p.matcher(s);
            if (m.matches()){
                String id = m.group(1);
                String name = m.group(2);
                AutomataLight a = new AutomataLight(name, Integer.parseInt(id));
                list.add(a);
            }
        }

        return list;
    }


    public Automata getAutomata(AutomataLight automataLight) {

        int id;
        String name;
        boolean isDefault = false;
        int cellCount;
        Cell[] cells;

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            String contents = loadFile("automata_" + automataLight.getId() + "/config.xml");
            ByteArrayInputStream input = new ByteArrayInputStream(contents.getBytes("UTF-8"));
            Document doc = dBuilder.parse(input);
            doc.getDocumentElement().normalize();

            Element element = (Element) doc.getElementsByTagName("automata").item(0);

            id   = Integer.parseInt(Persitance.getContentXML(element, "id"));
            name = Persitance.getContentXML(element, "name");
            isDefault = Boolean.parseBoolean(Persitance.getContentXML(element, "default"));

            NodeList cellsNode = element.getElementsByTagName("cell");

            // creation of each cells to be later accessible
            cells = new Cell[cellsNode.getLength()];
            for (int i = 0 ; i < cellsNode.getLength() ; i++) {
                Element c = (Element) cellsNode.item(i);
                Cell cell = new Cell();
                cells[Integer.parseInt(Persitance.getContentXML(c, "id"))] =  cell;
            }

            for (int i = 0 ; i < cellsNode.getLength() ; i++) {
                Element cell = (Element) cellsNode.item(i);

                int cellId       = Integer.parseInt(Persitance.getContentXML(cell, "id"));
                Cell workingCell = cells[cellId];

                workingCell.setName(Persitance.getContentXML(cell, "name"));
                workingCell.setColor(Persitance.getContentXML(cell, "color"));

                List<Cell> cellsToCount = workingCell.getCellsToCount();
                NodeList neighourIdsToCount = cell.getElementsByTagName("neighourIdToCount");
                for (int j = 0 ; j < neighourIdsToCount.getLength() ; j++) {
                    int toCount = Integer.parseInt(neighourIdsToCount.item(j).getFirstChild().getNodeValue());
                    cellsToCount.add(cells[toCount]);
                }

                List<NeighborPos> neighborPos = workingCell.getNeighbours();
                NodeList neighbourCoords = cell.getElementsByTagName("neighbourCoord");
                for (int j = 0 ; j < neighbourCoords.getLength() ; j++) {
                    Element e = (Element) neighbourCoords.item(j);
                    int dX = Integer.parseInt(e.getAttribute("x"));
                    int dY = Integer.parseInt(e.getAttribute("y"));
                    neighborPos.add(new NeighborPos(dX, dY));
                }

                Cell[] transitionsCells = new Cell[neighborPos.size() + 1];
                NodeList transitions = cell.getElementsByTagName("transition");
                for (int j = 0 ; j < transitions.getLength(); j++) {
                    Element e = (Element) transitions.item(j);
                    int nbNeighbours = Integer.parseInt(e.getAttribute("nbNeighbours"));
                    int becomeId = Integer.parseInt(e.getAttribute("become"));
                    transitionsCells[nbNeighbours] = cells[becomeId];
                }
                workingCell.setTransitions(transitionsCells);

            }

            Automata automata = new Automata(automataLight, isDefault, Arrays.asList(cells));
            return automata;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private static String getContentXML(Element element, String tagName) {
        return element.getElementsByTagName(tagName).item(0).getFirstChild().getNodeValue();
    }

    public void writeAutomata(Automata automata) {

    }
    
    public void setAssetManager(AssetManager assetManager) {
        this.assetManager = assetManager;
    }
}
