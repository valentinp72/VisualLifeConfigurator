package fr.ttvp.visuallifeconfigurator.view.Activities;

import android.content.res.AssetManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import fr.ttvp.visuallifeconfigurator.R;
import fr.ttvp.visuallifeconfigurator.model.Automata;
import fr.ttvp.visuallifeconfigurator.model.AutomataLight;
import fr.ttvp.visuallifeconfigurator.model.Cell;
import fr.ttvp.visuallifeconfigurator.model.Persistance;
import fr.ttvp.visuallifeconfigurator.view.Adapters.AutomataRecyclerViewAdapter;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton plusBtn;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.activity_main_list);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        plusBtn = findViewById(R.id.activity_main_plus);
        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Cell> cells = new ArrayList<>();
                AutomataLight automataLight = new AutomataLight(UUID.randomUUID().toString().substring(0, 5));
                Automata automata = new Automata(automataLight, false, cells);
                automata.save();
                Persistance.getInstance().addAutomataLight(automataLight);
                init();
            }
        });

        init();
    }

    public void init() {
        AssetManager assetManager = getAssets();
        Persistance persistance = Persistance.getInstance();
        persistance.setAssetManager(assetManager);
        persistance.setContext(getApplicationContext());

        mRecyclerView.setAdapter(new AutomataRecyclerViewAdapter(persistance.getAutomataLights()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_reset) {
            Persistance.getInstance().resetFiles();
            init();
        }

        return super.onOptionsItemSelected(item);
    }

}
