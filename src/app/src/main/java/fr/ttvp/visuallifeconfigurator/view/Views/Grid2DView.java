package fr.ttvp.visuallifeconfigurator.view.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.design.widget.BottomNavigationView;
import android.view.View;

import fr.ttvp.visuallifeconfigurator.model.Automata;
import fr.ttvp.visuallifeconfigurator.model.Map;
import fr.ttvp.visuallifeconfigurator.controller.Simulator;
import fr.ttvp.visuallifeconfigurator.controller.SimulatorListener;

public class Grid2DView extends View implements SimulatorListener {

    private Automata automata;
    private Paint[] paintForCellsID;
    private Simulator simulator;
    private Map currentMap;
    private BottomNavigationView bNav;

    public Grid2DView(Context context, Automata automata, Simulator simulator, BottomNavigationView bNav) {
        super(context);

        this.automata   = automata;
        this.simulator  = simulator;
        this.currentMap = simulator.getMap();
        this.paintForCellsID = getColorsForCellsID();
        this.bNav = bNav;

        this.simulator.addListener(this);
    }

    @Override
    public void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        this.simulator.setWidthHeightRatio((float) width / (float)height);
        this.redraw();
    }

    public void redraw() {
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.LTGRAY);
        final int height = getHeight() - bNav.getHeight();
        final int width = getWidth();
        final int cols = currentMap.getCols();
        final int lines = currentMap.getLines();

        int cellSize = Math.min(width  / cols, height / lines);

        final int drawWidth  = cols * cellSize;
        final int drawHeight = lines * cellSize;

        int xOffset = (width  - drawWidth)  / 2;
        int yOffset = (height - drawHeight) / 2;
        final int margin = 1;

        for(int i = 0 ; i < lines ; i++) {
            for(int j = 0 ; j < cols ; j++) {
                int cellID = currentMap.getCellID(i, j);
                canvas.drawRect(
                        xOffset + j * cellSize + margin,
                        yOffset + i * cellSize + margin,
                        xOffset + (j+1) * cellSize - margin,
                        yOffset + (i+1) * cellSize - margin,
                        this.paintForCellsID[cellID]);
            }
        }

    }

    private Paint[] getColorsForCellsID() {
        int length = this.automata.getCells().size();
        Paint[] colors = new Paint[length];
        for(int i = 0 ; i < length ; i++) {
            final int color = this.automata.getCells().get(i).getColorInt();
            Paint paint = new Paint();
            paint.setColor(color);
            colors[i] = paint;
        }
        return colors;
    }

    @Override
    public void updated() {
        this.currentMap = this.simulator.getMap();
        this.redraw();
    }
}
