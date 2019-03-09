package fr.ttvp.visuallifeconfigurator.view.Activities;

import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import fr.ttvp.visuallifeconfigurator.R;
import fr.ttvp.visuallifeconfigurator.model.Persitance;
import fr.ttvp.visuallifeconfigurator.view.AutomataRecyclerViewAdapter;

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

        AssetManager assetManager = getAssets();
        Persitance persitance = Persitance.getInstance();
        persitance.setAssetManager(assetManager);

        mRecyclerView.setAdapter(new AutomataRecyclerViewAdapter(persitance.getAutomataLights()));


    }


}
