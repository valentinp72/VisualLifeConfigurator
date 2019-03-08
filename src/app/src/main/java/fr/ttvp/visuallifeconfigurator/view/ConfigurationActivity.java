package fr.ttvp.visuallifeconfigurator.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
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

    private ResultCallbackParam<Cell> callback;

    private Cell cell;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);

        //this.cell = (Cell) getIntent().getExtras().getSerializable("Cell");
        this.cell = (Cell) getIntent().getSerializableExtra("Cell");
        //this.callback = (ResultCallbackParam<Cell>) getIntent().getSerializableExtra("Callback");

        this.toolbar    = findViewById(R.id.toolbar);
        this.backButton = findViewById(R.id.cell_configuration_toolbar_back);
        this.name       = findViewById(R.id.cell_configuration_toolbar_text);

        this.divider       = findViewById(R.id.divider);
        this.selectedCells = findViewById(R.id.cell_configuration_selected);
        this.config        = findViewById(R.id.cell_configuration_list);

        init();
        System.out.println("coucou");

    }

    private void init() {
        this.selectedCells.removeAllViews();
        this.config.removeAllViews();

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
                ConfigurationActivity.this.finish();
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
                    init();
                }
            });
            this.selectedCells.addView(im);
        }
        ImageButton plusButton = createPlusButton();
        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogCellChoosing dialog = new DialogCellChoosing(ConfigurationActivity.this, cell.getOriginAutomata().getCells(), cell.getOriginAutomata().getCells().get(0));
                dialog.show();
                dialog.setCallback(new ResultCallback() {
                    @Override
                    public void ended() {
                        cell.getCellsToCount().add(dialog.getCurrentCell());
                        init();
                    }
                });
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

    private RelativeLayout createLineForCount(final int count) {
        final Activity activity = this;
        RelativeLayout line = new RelativeLayout(this);

        TextView text = new TextView(this);
        text.setText(Integer.toString(count));
        // vertcial center
        RelativeLayout.LayoutParams paramsText = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        paramsText.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        text.setLayoutParams(paramsText);

        final ImageButton button = createButtonForCell(cell.getTransitions()[count]);
        // align to the right the image
        RelativeLayout.LayoutParams paramsImg = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        paramsImg.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        button.setLayoutParams(paramsImg);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogCellChoosing dialog = new DialogCellChoosing(activity, cell.getOriginAutomata().getCells(), cell.getTransitions()[count]);
                dialog.show();
                dialog.setCallback(new ResultCallback() {
                    @Override
                    public void ended() {
                        button.setColorFilter(dialog.getCurrentCell().getColorInt());
                        cell.getTransitions()[count] = dialog.getCurrentCell();
                    }
                });
            }
        });
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

    @Override
    public void finish() {
        Intent data = new Intent();
        data.putExtra("cell", this.cell);
        setResult(RESULT_OK, data);
        super.finish();
    }
}
