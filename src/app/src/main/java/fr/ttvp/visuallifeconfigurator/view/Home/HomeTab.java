package fr.ttvp.visuallifeconfigurator.view.Home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.ttvp.visuallifeconfigurator.AutomataHome;
import fr.ttvp.visuallifeconfigurator.model.Automata;

public abstract class HomeTab extends Fragment {

    protected AutomataHome automataHome;
    protected Automata automata;
    protected View view;
    protected int layoutName;

    public static HomeTab createInstance(Automata automata, AutomataHome automataHome, HomeTab tab) {
        Bundle args = new Bundle();
        args.putSerializable("Automata", automata);
        args.putSerializable("AutomataHome", automataHome);
        tab.setArguments(args);
        return tab;
    }

    public HomeTab(int layoutName) {
        super();
        this.layoutName = layoutName;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(this.layoutName, container, false);
        return this.view;
    }

    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);
        automata = (Automata) getArguments().getSerializable("Automata");
        automataHome = (AutomataHome) getArguments().getSerializable("AutomataHome");
    }

}
