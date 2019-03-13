package fr.ttvp.visuallifeconfigurator.view.Home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import fr.ttvp.visuallifeconfigurator.model.Automata;
import fr.ttvp.visuallifeconfigurator.model.Cell;

import static android.app.Activity.RESULT_OK;

public abstract class HomeTab extends Fragment {

    private static List<HomeTab> tabs = new ArrayList<>();

    public static final int EDITED_CELL = 1;
    public static final int EDITED_MAPS = 2;

    protected AutomataHome automataHome;
    protected Automata automata;
    protected View view;
    protected int layoutName;

    public static HomeTab createInstance(Automata automata, AutomataHome automataHome, HomeTab tab) {
        Bundle args = new Bundle();
        args.putSerializable("Automata", automata);
        args.putSerializable("AutomataHome", automataHome);
        tab.setArguments(args);
        tabs.add(tab);
        return tab;
    }

    public HomeTab(int layoutName) {
        super();
        this.layoutName = layoutName;
    }

    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(this.layoutName, container, false);
        initComponents();
        initView();
        return this.view;
    }

    public abstract void initComponents();

    public abstract void initView();

    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);
        automata = (Automata) getArguments().getSerializable("Automata");
        automataHome = (AutomataHome) getArguments().getSerializable("AutomataHome");
    }

    public void launchActivity(Intent intent, int code) {
        this.startActivityForResult(intent, code);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK) {
            if(requestCode == EDITED_CELL) {
                Cell c = (Cell) data.getSerializableExtra("cell");
                automata.replaceCell(c);
                automata.save();
            }
            else if(requestCode == EDITED_MAPS) {
                //map.save(automata.getAutomataLight());
            }
            for(HomeTab tab : tabs)
                tab.initView();
        }
    }


}
