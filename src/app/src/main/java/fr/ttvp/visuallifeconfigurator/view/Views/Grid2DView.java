package fr.ttvp.visuallifeconfigurator.view.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import fr.ttvp.visuallifeconfigurator.model.Automata;
import fr.ttvp.visuallifeconfigurator.model.Cell;
import fr.ttvp.visuallifeconfigurator.model.Map;
import fr.ttvp.visuallifeconfigurator.model.Simulator;
import fr.ttvp.visuallifeconfigurator.model.SimulatorListener;

public class Grid2DView extends View implements SimulatorListener {

    private Automata automata;
    private Paint[] paintForCellsID;
    private Simulator simulator;
    private Map currentMap;

    public Grid2DView(Context context, Automata automata, Simulator simulator) {
        super(context);

        this.automata   = automata;
        this.simulator  = simulator;
        this.currentMap = simulator.getMap();
        this.paintForCellsID = getColorsForCellsID();

        this.simulator.addListener(this);
    }

    @Override
    public void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        this.currentMap.setWidthHeightRatio(width / height);
        this.redraw();
    }

    public void redraw() {
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.LTGRAY);

        int cellSize = Math.min(getWidth()  / currentMap.getCols(), getHeight() / currentMap.getLines());

        int width  = currentMap.getCols() * cellSize;
        int height = currentMap.getLines() * cellSize;

        int xOffset = (getWidth()  - width)  / 2;
        int yOffset = (getHeight() - height) / 2;

        for(int i = 0 ; i < currentMap.getLines() ; i++) {
            for(int j = 0 ; j < currentMap.getCols() ; j++) {
                int cellID = currentMap.getCellID(i, j);
                canvas.drawRect(
                        xOffset + j * cellSize,
                        yOffset + i * cellSize,
                        xOffset + (j+1) * cellSize,
                        yOffset + (i+1) * cellSize,
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
