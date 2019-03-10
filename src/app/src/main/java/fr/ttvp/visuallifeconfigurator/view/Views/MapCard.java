package fr.ttvp.visuallifeconfigurator.view.Views;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.text.DateFormat;

import fr.ttvp.visuallifeconfigurator.R;
import fr.ttvp.visuallifeconfigurator.model.Automata;
import fr.ttvp.visuallifeconfigurator.model.Map;
import fr.ttvp.visuallifeconfigurator.model.MapLight;
import fr.ttvp.visuallifeconfigurator.view.Activities.CustomActivity;
import fr.ttvp.visuallifeconfigurator.view.Activities.MapPlayingActivity;

public class MapCard extends LinearLayout {

    private static final int PLAYED_MAP = 1;

    private TextView mapName;
    private TextView mapDate;
    private ImageButton editBtn;
    private ImageButton playBtn;

    private CustomActivity context;

    private MapLight mapLight;
    private Automata automata;
    private final DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT, getResources().getConfiguration().locale);

    public MapCard(CustomActivity context, MapLight mapLight, Automata automata) {
        super(context);
        this.context  = context;
        this.mapLight = mapLight;
        this.automata = automata;

        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.fragment_map_card, this);

        this.mapName = findViewById(R.id.card_map_name);
        this.mapDate = findViewById(R.id.card_map_date);
        this.editBtn = findViewById(R.id.card_map_btn_edit);
        this.playBtn = findViewById(R.id.card_map_btn_play);

        init();
    }

    private void init() {

        mapName.setText(mapLight.getName());
        mapDate.setText(dateFormat.format(mapLight.getLastPlayed()));

        editBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        playBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MapPlayingActivity.class);
                intent.putExtra(MapPlayingActivity.ARG_MAP, mapLight.getRealMap());
                intent.putExtra(MapPlayingActivity.ARG_AUTOMATA, automata);
                context.launchActivity(intent, PLAYED_MAP);
            }
        });

    }

}
