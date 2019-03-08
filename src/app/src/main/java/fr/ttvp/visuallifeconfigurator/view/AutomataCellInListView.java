package fr.ttvp.visuallifeconfigurator.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import fr.ttvp.visuallifeconfigurator.R;
import fr.ttvp.visuallifeconfigurator.model.Automata;
import fr.ttvp.visuallifeconfigurator.model.Cell;
import fr.ttvp.visuallifeconfigurator.view.Home.AutomataHome;
import fr.ttvp.visuallifeconfigurator.view.Home.HomeTab;
import fr.ttvp.visuallifeconfigurator.view.Home.HomeTabCells;

public class AutomataCellInListView extends LinearLayout {

    private HomeTab tab;
    private AutomataHome automataHome;
    private Automata automata;
    private Cell cell;

    private ImageButton moveButton;
    private TextView cellText;
    private ImageView cellImage;

    private int lastTouchedX, lastTouchedY;
    private int position;

    public AutomataCellInListView(Context context, AutomataHome automataHome, Automata automata, Cell cell, HomeTab tab) {
        super(context, null);
        this.tab = tab;
        this.automata = automata;
        this.automataHome = automataHome;
        this.cell = cell;
        init();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void init(){
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.sample_automata_cell_in_list_view, this);

        this.moveButton = findViewById(R.id.moveButton);
        this.cellText   = findViewById(R.id.cellText);
        this.cellImage  = findViewById(R.id.cellImage);

        cellText.setText(cell.getName());
        cellImage.setColorFilter(cell.getColorInt());

        this.setClickable(true);
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Rect moveButtonRect = new Rect(
                        (int) moveButton.getX(),
                        (int) moveButton.getY(),
                        (int)moveButton.getX() + moveButton.getWidth(),
                        (int)moveButton.getY() + moveButton.getHeight()
                );
                if(!moveButtonRect.contains(lastTouchedX, lastTouchedY)) {
                    Intent intent = new Intent(automataHome, CellEditionActivity.class);
                    intent.putExtra("Cell", cell);
                    tab.launchActivity(intent, HomeTab.EDITED_CELL);
                }
            }
        });
        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    lastTouchedX = (int) event.getX();
                    lastTouchedY = (int) event.getY();
                    v.setBackgroundColor(getResources().getColor(R.color.colorClicked));
                }
                else {
                    v.setBackgroundColor(0);
                }
                return false;
            }
        });

        this.moveButton.setOnTouchListener(new OnTouchListener() {
            final private int previousColor = AutomataCellInListView.this.getSolidColor();

            private void setY(float y) {
                AutomataCellInListView.this.cellText.setY(y);
                AutomataCellInListView.this.cellImage.setY(y);
            }

            private void setColor(int color) {
                AutomataCellInListView.this.cellText.setBackgroundColor(color);
            }

            private void resetColor() {
                AutomataCellInListView.this.cellText.setBackgroundColor(previousColor);
            }

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int THRESHOLD_MOVE = 50;

                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    LinearLayout ll = (LinearLayout) AutomataCellInListView.this.getParent();

                    boolean movingUp = event.getY() < v.getY();
                    int position = AutomataCellInListView.this.getPosition();

                    if(movingUp && position <= 1 || !movingUp && position + 1 >= ll.getChildCount()) {
                        // cancel if we are already on top/bottom
                        return true;
                    }

                    if (event.getY() > v.getY() + THRESHOLD_MOVE || event.getY() < v.getY() - THRESHOLD_MOVE) {
                        setY(v.getY());
                        resetColor();

                        int toSwapPos;
                        if(!movingUp)
                            for(toSwapPos = position + 1 ; !(ll.getChildAt(toSwapPos) instanceof AutomataCellInListView) ; toSwapPos++);
                        else
                            for(toSwapPos = position - 1 ; !(ll.getChildAt(toSwapPos) instanceof AutomataCellInListView) ; toSwapPos--);

                        AutomataCellInListView toSwap = (AutomataCellInListView) ll.getChildAt(toSwapPos);
                        AutomataCellInListView me     = (AutomataCellInListView) ll.getChildAt(position);

                        ll.removeView(toSwap);
                        ll.removeView(me);

                        if(movingUp) ll.addView(me, toSwap.getPosition());
                        ll.addView(toSwap, me.getPosition());
                        if(!movingUp) ll.addView(me, toSwap.getPosition());

                        int tmp = toSwap.getPosition();
                        toSwap.setPosition(me.getPosition());
                        me.setPosition(tmp);
                    }
                    else {
                        setY(event.getY());
                        setColor(Color.parseColor("#eaeaea"));
                    }
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    setY(v.getY());
                    resetColor();
                }
                return true;
            }
        });
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}