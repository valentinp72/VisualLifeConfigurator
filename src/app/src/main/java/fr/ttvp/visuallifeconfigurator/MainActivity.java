package fr.ttvp.visuallifeconfigurator;

import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fr.ttvp.visuallifeconfigurator.dummy.DummyContent;
import fr.ttvp.visuallifeconfigurator.model.Persitance;

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

        // specify an adapter (see also next example)
        List<String> contents = new ArrayList<String>();
        contents.add("Wireworld");
        contents.add("Conway");
        contents.add("My world");
        contents.add("Titouan");

        mRecyclerView.setAdapter(new AutomataRecyclerViewAdapter(contents));

        AssetManager assetManager = getAssets();
        Persitance persitance = Persitance.getInstance();
        persitance.setAssetManager(assetManager);

        persitance.getAutomataLights();

    }


}
