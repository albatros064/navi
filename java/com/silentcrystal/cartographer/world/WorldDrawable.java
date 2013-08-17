package com.silentcrystal.cartographer.world;

import android.graphics.Canvas;

/**
 * Created by Nathaniel on 8/17/13.
 */
public interface WorldDrawable {
    public boolean draw(Canvas c, ViewPort viewPort);
    public boolean isVisible(ViewPort viewPort);
}
