package com.silentcrystal.cartographer.world;

import android.graphics.Rect;

import com.silentcrystal.cartographer.geometry.DoubleRect;

/**
 * Created by Nathaniel on 8/17/13.
 */
public class ViewPort extends DoubleRect {
    private Rect screenBounds;

    public void setScreenBounds(Rect screenBounds) {
        this.screenBounds = screenBounds;
    }
    public Rect getScreenBounds() {
        return screenBounds;
    }
}
