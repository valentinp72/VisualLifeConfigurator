package fr.ttvp.visuallifeconfigurator.view.Activities;

import android.app.Activity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import fr.ttvp.visuallifeconfigurator.R;
import fr.ttvp.visuallifeconfigurator.model.Cell;
import fr.ttvp.visuallifeconfigurator.view.DialogCellChoosing;
import fr.ttvp.visuallifeconfigurator.view.ResultCallback;
import fr.ttvp.visuallifeconfigurator.view.ResultCallbackParam;


public class ConfigurationActivity extends CustomActivity {

    private View divider;

    private LinearLayout selectedCells;
    private LinearLayout config;

    private ResultCallbackParam<Cell> callback;

    private Cell cell;

    @Override
    public int getContentView() {
        return R.layout.activity_configuration;
    }

    @Override
    protected void initComponents() {
        this.cell = (Cell) this.getParameter("Cell");
        this.addResult("cell", this.cell);

        this.divider       = findViewById(R.id.divider);
        this.selectedCells = findViewById(R.id.cell_configuration_selected);
        this.config        = findViewById(R.id.cell_configuration_list);
    }

    @Override
    protected void initView() {
        this.selectedCells.removeAllViews();
        this.config.removeAllViews();

        // header, selected area
        for(Cell c : cell.getCellsToCount()) {
            final Cell tmp = c;
            ImageButton im = this.createButtonForCell(c);
            im.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cell.getCellsToCount().remove(tmp);
                    updateView();
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
                        updateView();
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

}
