package fr.ttvp.visuallifeconfigurator.view.Activities;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.LinearLayout;

import fr.ttvp.visuallifeconfigurator.R;
import fr.ttvp.visuallifeconfigurator.model.Automata;
import fr.ttvp.visuallifeconfigurator.model.Cell;
import fr.ttvp.visuallifeconfigurator.model.Map;
import fr.ttvp.visuallifeconfigurator.model.Persitance;
import fr.ttvp.visuallifeconfigurator.view.Activities.CustomActivity;
import fr.ttvp.visuallifeconfigurator.view.Views.MapCard;

public class SavedMapsActivity extends CustomActivity {

    private Persitance persitance;
    private Automata automata;

    private LinearLayout layout;
    private FloatingActionButton fab;

    @Override
    public int getContentView() {
        return R.layout.activity_saved_maps;
    }

    @Override
    protected void initParameters() {
        this.automata = (Automata) getParameter("automata");
    }

    @Override
    protected void initComponents() {
        this.persitance = Persitance.getInstance();
        this.fab = findViewById(R.id.saved_map_create);
        this.layout = findViewById(R.id.saved_map_layout);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    protected void initView() {
        Map map = Map.fromFile("map.exemple");
        for(int i = 0 ; i < 10 ; i++)
            this.layout.addView(new MapCard(this, map, automata));
    }

    @Override
    public String getToolbarTitle() {
        return getResources().getString(R.string.automata_map, automata.getName());
    }

}
