package fr.ttvp.visuallifeconfigurator.view.Home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import fr.ttvp.visuallifeconfigurator.view.CellItemConfigView;
import fr.ttvp.visuallifeconfigurator.R;
import fr.ttvp.visuallifeconfigurator.model.Cell;

import static android.app.Activity.RESULT_OK;

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
