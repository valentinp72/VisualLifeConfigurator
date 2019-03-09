package fr.ttvp.visuallifeconfigurator.view.Activities;

import android.widget.GridView;

import java.util.List;

import fr.ttvp.visuallifeconfigurator.R;
import fr.ttvp.visuallifeconfigurator.model.Cell;
import fr.ttvp.visuallifeconfigurator.model.NeighborPos;
import fr.ttvp.visuallifeconfigurator.view.Adapters.NeighborPosAdapter;

public class ChooseNeighborActivity extends CustomActivity {

    private Cell cell;

    private GridView grid;

    @Override
    public int getContentView() {
        return R.layout.activity_choose_neighbor;
    }

    @Override
    protected void initParameters() {
        this.cell = (Cell) getParameter("cell");
        this.addResult("cell", this.cell);
    }

    @Override
    protected void initComponents() {
        this.grid = findViewById(R.id.neighbours_grid);
        this.grid.setVerticalSpacing(0);
        this.grid.setHorizontalSpacing(0);
        this.grid.setNumColumns(Cell.NEIGHBOURS_SIZE_VERTICAL);

    }

    @Override
    protected void initView() {
        List<NeighborPos> allPos = cell.getNeighboursPosForSizes();
        NeighborPosAdapter neighborPosAdapter = new NeighborPosAdapter(this, allPos, cell);
        this.grid.setAdapter(neighborPosAdapter);
    }

    @Override
    public int getToolbarBackgroundColor() {
        return this.cell.getColorInt();
    }

    @Override
    public int getToolbarTextColor() {
        return this.cell.getMatchingColor();
    }

    @Override
    public String getToolbarTitle() {
        return getResources().getString(R.string.neighbours_with_name, cell.getName());
    }

}
