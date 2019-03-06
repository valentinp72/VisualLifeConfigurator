package fr.ttvp.visuallifeconfigurator.view.Home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import fr.ttvp.visuallifeconfigurator.CellItemConfigView;
import fr.ttvp.visuallifeconfigurator.R;
import fr.ttvp.visuallifeconfigurator.model.Cell;

public class HomeTabConfiguration extends HomeTab {

    private LinearLayout linearLayout;

    public HomeTabConfiguration() {
        super(R.layout.fragment_automata_home_configuration);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        this.linearLayout = this.view.findViewById(R.id.tab_configuration_layout);

        for(Cell c : this.automata.getCells()) {
            linearLayout.addView(new CellItemConfigView(view.getContext(), c));
        }
        return view;
    }

}
