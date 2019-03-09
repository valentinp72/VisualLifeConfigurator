package fr.ttvp.visuallifeconfigurator.view.Home;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.LinearLayout;

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
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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
