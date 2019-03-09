package fr.ttvp.visuallifeconfigurator.view.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pes.androidmaterialcolorpickerdialog.ColorPicker;
import com.pes.androidmaterialcolorpickerdialog.ColorPickerCallback;

import java.lang.reflect.Field;

import fr.ttvp.visuallifeconfigurator.R;
import fr.ttvp.visuallifeconfigurator.model.Cell;
import fr.ttvp.visuallifeconfigurator.view.Home.HomeTab;

public class CellEditionActivity extends CustomActivity {

    public static final int EDITED_NEIGHBOURS = 100;

    private Cell cell;

    private EditText nameEditor;
    private ImageView colorPreview;
    private TextView neighboursPreview;

    private LinearLayout neighbourArea;
    private LinearLayout colorArea;

    @Override
    public int getContentView() {
        return R.layout.activity_cell_edition;
    }

    @Override
    protected void initParameters() {
        this.cell = (Cell) getParameter("Cell");
        this.addResult("cell", this.cell);
    }

    @Override
    protected void initComponents() {
        this.nameEditor    = findViewById(R.id.cell_edition_edit_name);
        this.colorPreview  = findViewById(R.id.cell_edition_image_color);
        this.neighboursPreview = findViewById(R.id.cell_edition_neighbour_count);
        this.neighbourArea = findViewById(R.id.cell_edition_neighbour);
        this.colorArea     = findViewById(R.id.cell_edition_color);

        colorPreview.setClickable(true);
        colorPreview.setFocusable(true);
        colorArea.setOnClickListener(new View.OnClickListener() {
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
                        updateView();
                    }
                });
            }
        });

        neighbourArea.setClickable(true);
        neighbourArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CellEditionActivity.this, ChooseNeighborActivity.class);
                intent.putExtra("cell", cell);
                launchActivity(intent, EDITED_NEIGHBOURS);
            }
        });

        nameEditor.setText(cell.getName());
        nameEditor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                cell.setName(s.toString());
                updateView();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    @Override
    protected void initView() {
        colorPreview.setColorFilter(cell.getColorInt());
        neighboursPreview.setText(Integer.toString(cell.getNeighbours().size()));
        nameEditor.getBackground().setColorFilter(cell.getColorInt(), PorterDuff.Mode.SRC_ATOP);
        setCursorColor(nameEditor, cell.getColorInt());
        //setCursorPointerColor(nameEditor, cell.getColorInt());
    }

    @Override
    public String getToolbarTitle() {
        return cell.getName();
    }

    @Override
    public int getToolbarBackgroundColor() {
        return cell.getColorInt();
    }

    @Override
    public int getToolbarTextColor() {
        return cell.getMatchingColor();
    }


    public static void setCursorColor(EditText view, @ColorInt int color) {
        /*
            Edition de la couleur du curseur programmatiquement
            https://stackoverflow.com/a/26543290
         */
        try {
            // Get the cursor resource id
            Field field = TextView.class.getDeclaredField("mCursorDrawableRes");
            field.setAccessible(true);
            int drawableResId = field.getInt(view);

            // Get the editor
            field = TextView.class.getDeclaredField("mEditor");
            field.setAccessible(true);
            Object editor = field.get(view);

            // Get the drawable and set a color filter
            Drawable drawable = ContextCompat.getDrawable(view.getContext(), drawableResId);
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_IN);
            Drawable[] drawables = {drawable, drawable};

            // Set the drawables
            field = editor.getClass().getDeclaredField("mCursorDrawable");
            field.setAccessible(true);
            field.set(editor, drawables);
        }
        catch (Exception ignored) {

        }
    }

    @Override
    protected void onChildActivityFinish(int resultCode, Intent data) {
        if(resultCode == EDITED_NEIGHBOURS) {
            Cell c = (Cell) data.getSerializableExtra("cell");
            cell.setNeighbours(c.getNeighbours());
            cell.setTransitions(c.getTransitions());
        }
    }

}
