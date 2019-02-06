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
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AutomataCellInListView extends RelativeLayout {

    private ImageButton moveButton;

    public AutomataCellInListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
        //setOrientation(LinearLayout.HORIZONTAL);
        //setGravity(Gravity.CENTER_VERTICAL);

        //inflater.inflate(R.layout.view_color_options, this, true);
    }

    public AutomataCellInListView(Context context) {
        this(context, null);
        init(null);
    }

    private void init(AttributeSet set){
        inflate(getContext(), R.layout.sample_automata_cell_in_list_view, this);
        this.moveButton = (ImageButton) findViewById(R.id.moveButton);
    }



}