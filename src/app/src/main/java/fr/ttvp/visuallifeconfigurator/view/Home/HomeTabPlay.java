package fr.ttvp.visuallifeconfigurator.view.Home;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import java.util.UUID;

import fr.ttvp.visuallifeconfigurator.R;
import fr.ttvp.visuallifeconfigurator.model.Map;
import fr.ttvp.visuallifeconfigurator.model.MapLight;
import fr.ttvp.visuallifeconfigurator.model.Persistance;
import fr.ttvp.visuallifeconfigurator.view.Activities.MapPlayingActivity;
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

        this.buttonRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MapPlayingActivity.class);
                MapLight mapLight = new MapLight(
                        MapLight.idTotal++,
                        UUID.randomUUID().toString().substring(0, 5),
                        Persistance.getInstance().getAutomataFolder(automata.getAutomataLight()));
                Map map = Map.fromRandom(automata, 40, 30, mapLight);
                map.save(automata.getAutomataLight());
                intent.putExtra(MapPlayingActivity.ARG_MAP, mapLight);
                intent.putExtra(MapPlayingActivity.ARG_AUTOMATA, automata);
                launchActivity(intent, HomeTab.EDITED_MAPS);
            }
        });
    }

    public void initView() {

    }

}
