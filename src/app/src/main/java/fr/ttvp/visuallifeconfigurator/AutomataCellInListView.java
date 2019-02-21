package fr.ttvp.visuallifeconfigurator;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import fr.ttvp.visuallifeconfigurator.model.Automata;
import fr.ttvp.visuallifeconfigurator.model.Cell;

public class AutomataCellInListView extends LinearLayout {

    private Automata automata;
    private Cell cell;

    private ImageButton moveButton;
    private TextView cellText;
    private ImageView cellImage;

    public AutomataCellInListView(Context context, Automata automata, Cell cell) {
        super(context, null);
        this.automata = automata;
        this.cell = cell;
        init();
    }

    private void init(){
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.sample_automata_cell_in_list_view, this);

        this.moveButton = findViewById(R.id.moveButton);
        this.cellText   = findViewById(R.id.cellText);
        this.cellImage  = findViewById(R.id.cellImage);

        cellText.setText(cell.getName());
        cellImage.setColorFilter(Color.parseColor(cell.getColor()));
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("HEOH");
            }
        });

    }



}