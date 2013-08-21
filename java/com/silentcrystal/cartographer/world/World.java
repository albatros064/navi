package com.silentcrystal.cartographer.world;


import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class World {
    protected boolean changed;



    List<WorldDrawable> drawables;

    private Grid worldGrid;
    private ViewPort viewPort;

    public World() {
        drawables = new ArrayList<WorldDrawable>();
        changed = false;
        worldGrid = new Grid(10,10);
        drawables.add(worldGrid);
    }

    public void setViewPort(ViewPort viewPort) {
        this.viewPort = viewPort;
    }

    public boolean isChanged() {
        return changed;
    }

    public boolean draw(Canvas canvas) {
        if (viewPort == null || !viewPort.isValid()) {
            return true;
        }
        Log.i("World:draw", "redrawing");
        for (WorldDrawable drawable: drawables) {
            if (drawable.isVisible(viewPort)) {
                drawable.draw(canvas, viewPort);
            }
        }
        changed = false;
        return true;
    }
}
