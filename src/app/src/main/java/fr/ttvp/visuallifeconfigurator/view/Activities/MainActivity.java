package fr.ttvp.visuallifeconfigurator.view.Activities;

import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import fr.ttvp.visuallifeconfigurator.R;
import fr.ttvp.visuallifeconfigurator.model.Persitance;
import fr.ttvp.visuallifeconfigurator.view.Adapters.AutomataRecyclerViewAdapter;

public class MainActivity extends AppCompatActivity {

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
        init();
    }

    public void init() {
        AssetManager assetManager = getAssets();
        Persitance persitance = Persitance.getInstance();
        persitance.setAssetManager(assetManager);
        persitance.setContext(getApplicationContext());

        mRecyclerView.setAdapter(new AutomataRecyclerViewAdapter(persitance.getAutomataLights()));
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
            Persitance.getInstance().resetFiles();
            init();
        }

        return super.onOptionsItemSelected(item);
    }

}
