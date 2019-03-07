package fr.ttvp.visuallifeconfigurator.view.Home;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import fr.ttvp.visuallifeconfigurator.view.AutomataCellInListView;
import fr.ttvp.visuallifeconfigurator.R;
import fr.ttvp.visuallifeconfigurator.model.Cell;

public class HomeTabCells extends HomeTab {

    private LinearLayout ll;
    private FloatingActionButton fab;

    public HomeTabCells() {
        super(R.layout.fragment_automata_home_cells);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        this.ll = view.findViewById(R.id.contents);

        this.fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        int currentPosition = 2;
        for (Cell cell : automata.getCells()) {
            AutomataCellInListView toAdd = new AutomataCellInListView(view.getContext(), automataHome, automata, cell);
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
        return this.view;
    }

}
