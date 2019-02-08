package fr.ttvp.visuallifeconfigurator;

/*import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

public class AutomataCellInListView extends LinearLayout {

    public AutomataCellInListView(Context context) {
        super(context);
    }

    public AutomataCellInListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutomataCellInListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

}
*/


//import com.vogella.android.view.compoundview.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class CellItemConfigView extends LinearLayout {

    private ImageButton moveButton;

    public CellItemConfigView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
        //setOrientation(LinearLayout.HORIZONTAL);
        //setGravity(Gravity.CENTER_VERTICAL);

        //inflater.inflate(R.layout.sample_automata_cell_in_list_view, this, true);
    }

    public CellItemConfigView(Context context) {
        this(context, null);
        init(null);
    }

    private void init(AttributeSet set){
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.sample_cell_item_config_view, this);
    }



}