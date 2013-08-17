package com.silentcrystal.cartographer.world;


import android.graphics.Canvas;

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

    public boolean isChanged() {
        return changed;
    }

    public boolean draw(Canvas canvas) {

        for (WorldDrawable drawable: drawables) {
            if (drawable.isVisible(viewPort)) {
                drawable.draw(canvas, viewPort);
            }
        }
        return true;
    }
}
