package fr.ttvp.visuallifeconfigurator.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.io.Serializable;
import java.util.List;

import fr.ttvp.visuallifeconfigurator.R;
import fr.ttvp.visuallifeconfigurator.model.Cell;

public class DialogCellChoosing extends Dialog {

    private List<Cell> cells;
    private Cell currentCell;

    private ResultCallback callback;
    private LinearLayout ll;

    public DialogCellChoosing(Activity a, List<Cell> cells, Cell currentCell) {
        super(a);
        this.currentCell = currentCell;
        this.cells = cells;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fragment_dialog_cell_choosing);
        
        this.ll = findViewById(R.id.dialog_cell_choosing);
        for(final Cell c : cells) {
            ImageButton imgBtn = new ImageButton(getContext());
            imgBtn.setImageResource(R.drawable.colored_rectangle);
            imgBtn.setColorFilter(c.getColorInt());
            imgBtn.setBackgroundColor(0);
            imgBtn.setPadding(0,10,0,0);
            imgBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentCell = c;
                    callback.ended();
                    dismiss();
                }
            });
            ll.addView(imgBtn);
        }
    }

    public Cell getCurrentCell() {
        return currentCell;
    }

    public void setCallback(ResultCallback callback) {
        this.callback = callback;
    }
}
