package fr.ttvp.visuallifeconfigurator.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import fr.ttvp.visuallifeconfigurator.R;
import fr.ttvp.visuallifeconfigurator.model.Cell;
import fr.ttvp.visuallifeconfigurator.view.Activities.ConfigurationActivity;
import fr.ttvp.visuallifeconfigurator.view.Home.HomeTab;

public class CellItemConfigView extends LinearLayout {

    private HomeTab tab;
    private Cell cell;

    public CellItemConfigView(Context context, Cell cell, HomeTab tab) {
        super(context, null);;
        this.cell = cell;
        this.tab  = tab;
        init();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void init(){
        LinearLayout layout;
        ImageView image;
        TextView text;

        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.sample_cell_item_config_view, this);

        layout = findViewById(R.id.cell_item_config);
        image  = findViewById(R.id.cell_item_config_image);
        text   = findViewById(R.id.cell_item_config_text);

        image.setColorFilter(Color.parseColor(cell.getColor()));
        text.setText(cell.getName());

        layout.setClickable(true);
        layout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                tab.launchActivity(getIntentClick(), HomeTab.EDITED_CELL);
            }
        });
        layout.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                    v.setBackgroundColor(getResources().getColor(R.color.colorClicked));
                else
                    v.setBackgroundColor(0);
                return false;
            }
        });
    }

    private Intent getIntentClick() {
        Intent intent = new Intent(getContext(), ConfigurationActivity.class);
        intent.putExtra("Cell", this.cell);
        return intent;
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

}
