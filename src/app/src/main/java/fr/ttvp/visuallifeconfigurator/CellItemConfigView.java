package fr.ttvp.visuallifeconfigurator;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import fr.ttvp.visuallifeconfigurator.model.Automata;
import fr.ttvp.visuallifeconfigurator.model.AutomataLight;
import fr.ttvp.visuallifeconfigurator.model.Cell;
import fr.ttvp.visuallifeconfigurator.view.ConfigurationActivity;

public class CellItemConfigView extends LinearLayout {

    private Cell cell;

    private LinearLayout layout;
    private ImageView image;
    private TextView text;

    public CellItemConfigView(Context context, Cell cell) {
        super(context, null);
        this.cell = cell;
        init();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void init(){
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.sample_cell_item_config_view, this);

        this.layout = findViewById(R.id.cell_item_config);
        this.image  = findViewById(R.id.cell_item_config_image);
        this.text   = findViewById(R.id.cell_item_config_text);

        this.image.setColorFilter(Color.parseColor(cell.getColor()));
        this.text.setText(cell.getName());

        this.layout.setClickable(true);
        this.layout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                getContext().startActivity(getIntentClick());
            }
        });
        this.layout.setOnTouchListener(new OnTouchListener() {
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


}
