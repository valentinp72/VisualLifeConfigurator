package fr.ttvp.visuallifeconfigurator.view;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pes.androidmaterialcolorpickerdialog.ColorPicker;
import com.pes.androidmaterialcolorpickerdialog.ColorPickerCallback;

import fr.ttvp.visuallifeconfigurator.R;
import fr.ttvp.visuallifeconfigurator.model.Cell;

public class CellEditionActivity extends AppCompatActivity {

    private Cell cell;

    private Toolbar toolbar;
    private EditText nameEditor;
    private ImageView colorPreview;
    private TextView neighboursPreview;
    private ImageButton backButton;

    private LinearLayout neighbourArea;
    private LinearLayout colorArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_cell_edition);

        cell = ((Cell) getIntent().getSerializableExtra("Cell"));
        this.initComponents();
    }

    private void initComponents() {
        this.toolbar       = (Toolbar) findViewById(R.id.cell_edition_toolbar);
        this.nameEditor    = (EditText) findViewById(R.id.cell_edition_toolbar_edit);
        this.backButton    = (ImageButton) findViewById(R.id.cell_edition_toolbar_back);
        this.colorPreview  = (ImageView) findViewById(R.id.cell_edition_image_color);
        this.neighboursPreview = (TextView) findViewById(R.id.cell_edition_neighbour_count);
        this.neighbourArea = (LinearLayout) findViewById(R.id.cell_edition_neighbour);
        this.colorArea     = (LinearLayout) findViewById(R.id.cell_edition_color);

        toolbar.setBackgroundColor(cell.getColorInt());
        toolbar.setTitleTextColor(cell.getMatchingColor());
        toolbar.setSubtitleTextColor(cell.getMatchingColor());
        backButton.setColorFilter(cell.getMatchingColor());
        nameEditor.setText(cell.getName());
        nameEditor.setTextColor(cell.getMatchingColor());
        colorPreview.setColorFilter(cell.getColorInt());
        neighboursPreview.setText(Integer.toString(cell.getNeighbours().size()));

        colorPreview.setClickable(true);
        colorPreview.setFocusable(true);
        colorPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentColor  = Color.parseColor(cell.getColor());
                int defaultColorR = Color.red(currentColor);
                int defaultColorG = Color.green(currentColor);
                int defaultColorB = Color.blue(currentColor);
                ColorPicker cp = new ColorPicker(CellEditionActivity.this, defaultColorR, defaultColorG, defaultColorB);
                cp.show();
                cp.enableAutoClose();
                cp.setCallback(new ColorPickerCallback() {
                    @Override
                    public void onColorChosen(int color) {
                        cell.setColor("#" + Integer.toHexString(color));
                        colorPreview.setColorFilter(color);
                    }
                });
            }
        });

        neighbourArea.setClickable(true);
        neighbourArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CellEditionActivity.this, ChooseNeighbor.class);
                startActivity(intent);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
