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
import javax.xml.parsers.ParserConfigurationException;

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

        boolean isDefault;
        Cell[] cells;
        Document doc;

        try {
            doc = getDocumentXML("automata_" + automataLight.getId() + "/config.xml");
        }
        catch (Exception e) {
            return Automata.UNKNOWN_AUTOMATA;
        }

        Element element = (Element) doc.getElementsByTagName("automata").item(0);
        NodeList cellsNode = element.getElementsByTagName("cell");

        // creation of each cells to be later accessible
        cells = new Cell[cellsNode.getLength()];
        for (int i = 0 ; i < cellsNode.getLength() ; i++) {
            Element c = (Element) cellsNode.item(i);
            Cell cell = new Cell();
            int id = Integer.parseInt(Persitance.getContentXML(c, "id"));
            cells[id] = cell;
            cell.setId(id);
        }

        // each cell configuration
        for (int i = 0 ; i < cellsNode.getLength() ; i++) {
            Element cell = (Element) cellsNode.item(i);

            int cellId       = Integer.parseInt(Persitance.getContentXML(cell, "id"));
            Cell workingCell = cells[cellId];

            // name and color of this cell
            workingCell.setName(Persitance.getContentXML(cell, "name"));
            workingCell.setColor(Persitance.getContentXML(cell, "color"));
            workingCell.setDefaultCell(Boolean.parseBoolean(Persitance.getContentXML(cell, "default")));

            // neighbours ids to count
            List<Cell> cellsToCount = workingCell.getCellsToCount();
            NodeList neighourIdsToCount = cell.getElementsByTagName("neighbourIdToCount");
            for (int j = 0 ; j < neighourIdsToCount.getLength() ; j++) {
                int toCount = Integer.parseInt(neighourIdsToCount.item(j).getFirstChild().getNodeValue());
                cellsToCount.add(cells[toCount]);
            }

            // neighbours coords
            List<NeighborPos> neighborPos = workingCell.getNeighbours();
            NodeList neighbourCoords = cell.getElementsByTagName("neighbourCoord");
            for (int j = 0 ; j < neighbourCoords.getLength() ; j++) {
                Element e = (Element) neighbourCoords.item(j);
                int dX = Integer.parseInt(e.getAttribute("x"));
                int dY = Integer.parseInt(e.getAttribute("y"));
                neighborPos.add(new NeighborPos(dX, dY));
            }

            // transitions from this cell type to other types
            NodeList transitions = cell.getElementsByTagName("transition");
            Cell[] transitionsCells = new Cell[neighbourCoords.getLength() + 1];
            for (int j = 0 ; j < transitions.getLength(); j++) {
                Element e = (Element) transitions.item(j);
                int nbNeighbours = Integer.parseInt(e.getAttribute("nbNeighbours"));
                int becomeId = Integer.parseInt(e.getAttribute("become"));
                transitionsCells[nbNeighbours] = cells[becomeId];
            }
            workingCell.setTransitions(transitionsCells);

        }

        isDefault = Boolean.parseBoolean(Persitance.getContentXML(element, "default"));

        Automata automata = new Automata(automataLight, isDefault, Arrays.asList(cells));

        // we set the origin automata of each cell
        for(Cell c : automata.getCells()) {
            c.setOriginAutomata(automata);
        }
        return automata;
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

    private Document getDocumentXML(String xmlPath) throws Exception {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        String contents = loadFile(xmlPath);
        ByteArrayInputStream input = new ByteArrayInputStream(contents.getBytes("UTF-8"));
        Document doc = dBuilder.parse(input);
        doc.getDocumentElement().normalize();
        return doc;
    }

    private static String getContentXML(Element element, String tagName) {
        return element.getElementsByTagName(tagName).item(0).getFirstChild().getNodeValue();
    }

    public void writeAutomata(Automata automata) {

    }
    
    public void setAssetManager(AssetManager assetManager) {
        this.assetManager = assetManager;
    }


    // returns the maps of a particular automata
    public List<MapLight> getLightMaps(long id) {
        final String path = "automata_" + id;
        String mapFile = loadFile(path + "/maps.txt");
        List<String> lines = new ArrayList<String>(
                Arrays.asList(mapFile.split("\n"));
        );
        List<MapLight> maps new ArrayList<MapLight>;
        for (String line: lines) {
            String[] id_name = line.split(' ', 2);
            long id = Integer.parseInt(id_name[0]);
            maps.add(new MapLight(id, id_name[1], path));
        }
        return maps;
    }
}
