package fr.ttvp.visuallifeconfigurator.model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Automata implements Serializable {

    private AutomataLight automataLight;
    private List<Cell> cells;
    private boolean isDefault;

    public static final Automata UNKNOWN_AUTOMATA = new Automata(AutomataLight.UNKNOWN_AUTOMATA, false, new ArrayList<Cell>());

    public Automata(AutomataLight automataLight, boolean isDefault, List<Cell> cells) {
        this.automataLight = automataLight;
        this.cells         = cells;
        this.isDefault     = isDefault;
    }

    public String toString() {
        return "Automata: " + automataLight.toString() + " " + cells + " " + isDefault;
    }

    public List<Cell> getCells() {
        return cells;
    }

    public String getName() {
        return this.getAutomataLight().getName();
    }

    public long getId() {
        return this.getAutomataLight().getId();
    }

    public AutomataLight getAutomataLight() {
        return this.automataLight;
    }

    public void setCells(List<Cell> cells) {
        this.cells = cells;
    }

    public void replaceCell(Cell cell) {
        for(int i = 0 ; i < this.cells.size() ; i++) {
            if(this.cells.get(i).getId() == cell.getId()) {
                this.cells.set(i, cell);
            }
        }
    }

    public List<MapLight> getLightMaps() {
        return Persitance.getInstance().getLightMaps(this.automataLight);
    }

    public String toXML() {
        StringBuilder str = new StringBuilder();

        str.append("<?xml version=\"1.0\"?>\n");
        str.append("<automata>\n");
        str.append("    <id>" + this.automataLight.getId() + "</id>\n");
        str.append("    <name>" + this.automataLight.getName() + "</name>\n");
        str.append("    <default>" + String.valueOf(isDefault) + "</default>\n");

        for(Cell c : cells) {
            str.append(c.toXML());
        }

        str.append("</automata>\n");

        return str.toString();
    }

    public static Automata fromXML(AutomataLight automataLight) {
        Persitance persitance = Persitance.getInstance();
        boolean isDefault;
        Cell[] cells;
        Document doc = persitance.automataDocument(automataLight);

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

    public void save() {
        Persitance.getInstance().saveAutomata(this);
    }
}
