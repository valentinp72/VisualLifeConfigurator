package fr.ttvp.visuallifeconfigurator.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridLayout;

import java.util.List;

import fr.ttvp.visuallifeconfigurator.model.Cell;
import fr.ttvp.visuallifeconfigurator.model.NeighborPos;

public class NeighborPosAdapter extends BaseAdapter {

    private GridLayout.LayoutParams layoutParams;
    private Context context;
    private List<NeighborPos> neighborPos;
    private Cell cell;

    public NeighborPosAdapter(Context context, List<NeighborPos> neighborPos, Cell cell) {
        this.context     = context;
        this.neighborPos = neighborPos;
        this.cell        = cell;

        layoutParams = new GridLayout.LayoutParams();
        layoutParams.setMargins(0, 0, 0, 0);
    }

    @Override
    public int getCount() {
        return neighborPos.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final NeighborPos nPos = neighborPos.get(position);
        final CheckBox checkbox = new CheckBox(this.context);
        checkbox.setLayoutParams(layoutParams);

        if(nPos.presentInList(cell.getNeighbours())) {
            checkbox.toggle();
        }

        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                cell.setNeighbourStatus(nPos, isChecked);
            }
        });

        return checkbox;
    }

}
