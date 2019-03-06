package fr.ttvp.visuallifeconfigurator.view;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import fr.ttvp.visuallifeconfigurator.R;
import fr.ttvp.visuallifeconfigurator.model.Cell;


public class ConfigurationActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageButton backButton;
    private TextView name;
    private View divider;

    private LinearLayout selectedCells;
    private LinearLayout config;

    private Cell cell;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);

        this.cell = (Cell) getIntent().getSerializableExtra("Cell");

        this.toolbar    = findViewById(R.id.toolbar);
        this.backButton = findViewById(R.id.cell_configuration_toolbar_back);
        this.name       = findViewById(R.id.cell_configuration_toolbar_text);

        this.divider       = findViewById(R.id.divider);
        this.selectedCells = findViewById(R.id.cell_configuration_selected);
        this.config        = findViewById(R.id.cell_configuration_list);

        init();
    }

    private void init() {

        // toolbar
        toolbar.setTitle(cell.getName());
        toolbar.setBackgroundColor(cell.getColorInt());
        setSupportActionBar(toolbar);

        name.setText(cell.getName());
        name.setTextColor(cell.getMatchingColor());

        backButton.setColorFilter(cell.getMatchingColor());
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // header, selected area
        for(Cell c : cell.getCellsToCount()) {
            final Cell tmp = c;
            ImageButton im = this.createButtonForCell(c);
            im.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cell.getCellsToCount().remove(tmp);
                    recreate();
                }
            });
            this.selectedCells.addView(im);
        }
        ImageButton plusButton = createPlusButton();
        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cell.getCellsToCount().add(cell.getOriginAutomata().getCells().get(0));
                recreate();
            }
        });
        this.selectedCells.addView(plusButton);
        this.selectedCells.setPadding(10,0,10, 0);

        // contents
        config.setPadding(40,0,40,0);

        for(int i = 0 ; i < cell.getTransitions().length ; i++) {
            RelativeLayout line = createLineForCount(i);
            this.config.addView(line);
        }

    }

    private RelativeLayout createLineForCount(int count) {

        RelativeLayout line = new RelativeLayout(this);


        TextView text = new TextView(this);
        text.setText(Integer.toString(count));
        // vertcial center
        RelativeLayout.LayoutParams paramsText = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        paramsText.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        text.setLayoutParams(paramsText);

        ImageButton button = createButtonForCell(cell.getTransitions()[count]);
        // align to the right the image
        RelativeLayout.LayoutParams paramsImg = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        paramsImg.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        button.setLayoutParams(paramsImg);

        line.addView(text);
        line.addView(button);
        return line;
    }

    private ImageButton createPlusButton() {
        ImageButton plusButton = new ImageButton(this);
        plusButton.setImageResource(R.drawable.ic_add_black_24dp);
        plusButton.setBackgroundColor(0);
        plusButton.setPadding(0,10,0,0);
        return plusButton;
    }

    private ImageButton createButtonForCell(Cell c) {
        ImageButton view = new ImageButton(this);
        view.setImageResource(R.drawable.colored_rectangle);
        view.setColorFilter(c.getColorInt());
        view.setBackgroundColor(0);
        view.setPadding(5, 5,5,5);
        return view;
    }

}
