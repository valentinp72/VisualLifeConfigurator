package fr.ttvp.visuallifeconfigurator.view.Home;

import android.widget.LinearLayout;

import fr.ttvp.visuallifeconfigurator.view.Views.CellItemConfigView;
import fr.ttvp.visuallifeconfigurator.R;
import fr.ttvp.visuallifeconfigurator.model.Cell;

public class HomeTabConfiguration extends HomeTab {

    private LinearLayout linearLayout;

    public HomeTabConfiguration() {
        super(R.layout.fragment_automata_home_configuration);
    }

    public void initComponents() {
        this.linearLayout = this.view.findViewById(R.id.tab_configuration_layout);
    }

    public void initView(){
        linearLayout.removeAllViews();

        for(Cell c : this.automata.getCells()) {
            linearLayout.addView(new CellItemConfigView(view.getContext(), c, this));
        }
    }
}
