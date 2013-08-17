package com.silentcrystal.cartographer.world;

/**
 * Created by Nathaniel on 8/17/13.
 */
abstract public class WorldDrawableObject extends WorldObject implements WorldDrawable {
    public boolean isVisible(ViewPort viewPort) {
        return viewPort.intersects(this);
    }
}
