package fr.ttvp.visuallifeconfigurator.view.Home;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import fr.ttvp.visuallifeconfigurator.R;
import fr.ttvp.visuallifeconfigurator.view.Activities.SavedMapsActivity;

public class HomeTabPlay extends HomeTab {

    private Button buttonSavedMaps;
    private Button buttonRandom;

    public HomeTabPlay() {
        super(R.layout.fragment_automata_home_play);
    }

    public void initComponents() {
        this.buttonSavedMaps = view.findViewById(R.id.btn_saved_maps);
        this.buttonRandom    = view.findViewById(R.id.btn_random);

        this.buttonSavedMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SavedMapsActivity.class);
                intent.putExtra("automata", automata);
                launchActivity(intent, HomeTab.EDITED_MAPS);
            }
        });
    }

    public void initView() {

    }

}
