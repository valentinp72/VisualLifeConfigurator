package fr.ttvp.visuallifeconfigurator.view.Home;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import fr.ttvp.visuallifeconfigurator.model.NeighborPos;
import fr.ttvp.visuallifeconfigurator.view.Views.AutomataCellInListView;
import fr.ttvp.visuallifeconfigurator.R;
import fr.ttvp.visuallifeconfigurator.model.Cell;

public class HomeTabCells extends HomeTab {

    private View defaultText;
    private View divider;

    private LinearLayout ll;
    private FloatingActionButton fab;

    public HomeTabCells() {
        super(R.layout.fragment_automata_home_cells);
    }

    public void initComponents() {
        this.ll  = view.findViewById(R.id.contents);
        this.fab = view.findViewById(R.id.fab);
        this.divider     = view.findViewById(R.id.divider);
        this.defaultText = view.findViewById(R.id.textView);
    }

    public void initView() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Cell> cells = new ArrayList<>(automata.getCells());
                Cell cell = new Cell();
                cell.setOriginAutomata(automata);
                cell.setId(automata.getCells().size());
                Cell[] transitions = {cell, cell};
                cell.setTransitions(transitions);
                NeighborPos neighborPos = new NeighborPos(0, 0);
                cell.getNeighbours().add(neighborPos);
                cells.add(cell);
                automata.setCells(cells);
                automata.save();
                ///// TODO: revoir ce problème
                AutomataHome automataHome = (AutomataHome) getActivity();
                automataHome.recreate();
                initView();
            }
        });

        ll.removeAllViews();
        ll.addView(defaultText);
        ll.addView(divider);
        int currentPosition = 2;
        for (Cell cell : automata.getCells()) {
            AutomataCellInListView toAdd = new AutomataCellInListView(view.getContext(), automataHome, automata, cell, this);
            if(cell.isDefaultCell()) {
                toAdd.setPosition(1);
                ll.addView(toAdd, 1);
            }
            else {
                currentPosition++;
                toAdd.setPosition(currentPosition);
                ll.addView(toAdd);
            }
        }
    }

}
