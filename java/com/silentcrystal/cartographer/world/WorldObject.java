package com.silentcrystal.cartographer.world;

import com.silentcrystal.cartographer.geometry.Circle;
import com.silentcrystal.cartographer.geometry.DoubleRect;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nathaniel on 8/15/13.
 */
abstract public class WorldObject extends Circle {

    protected WorldObject parent;
    protected List<WorldObject> children;

    protected Circle boundingCircle;

    public WorldObject() {
        parent = null;
        children = new ArrayList<WorldObject>();

        boundingCircle = new Circle();
    }
    public WorldObject(WorldObject parent) {
        this.parent = parent;
        if (parent != null) {
            parent.addChild(this);
        }
        children = new ArrayList<WorldObject>();

        boundingCircle = new Circle(this);
    }

    public void setAbsoluteX(double absoluteX) {
        if (parent == null) {
            x = absoluteX;
        }
        else {
            x = absoluteX - parent.getAbsoluteX();
            calculateBoundingCircle();
        }
    }
    public double getAbsoluteX() {
        if (parent == null) {
            return y;
        }
        return parent.getAbsoluteX() + x;
    }

    public void setAbsoluteY(double absoluteY) {
        if (parent == null) {
            y = absoluteY;
        }
        else {
            y = absoluteY - parent.getAbsoluteY();
            calculateBoundingCircle();
        }
    }
    public double getAbsoluteY() {
        if (parent != null) {
            return y;
        }
        return parent.getAbsoluteY() + y;
    }

    public void setRadius(double radius) {
        super.setRadius(radius);
        calculateBoundingCircle();
    }

    public Circle getBoundingCircle() {
        return boundingCircle;
    }

    public void calculateBoundingCircle() {
        calculateBoundingCircle(false);
    }
    public void calculateBoundingCircle(boolean calculateSelf) {
        if (calculateSelf) {
            boundingCircle.clone(simpleBoundsExtremes());
        }
        if (parent != null) {
            parent.calculateBoundingCircle(true);
        }
    }

    protected Circle simpleBoundsExtremes() {
        Circle bounds = new Circle();
        DoubleRect extremes = null;
        double r = 0;

        for (WorldObject child: children) {
            if (extremes == null) {
                extremes = new DoubleRect(child.x, child.y, child.x, child.y);
            }
            else {
                if (child.x > extremes.getRight()) {
                    extremes.setRight(child.x);
                }
                else if (child.x < extremes.getLeft()) {
                    extremes.setLeft(child.x);
                }
                if (child.y > extremes.getTop()) {
                    extremes.setTop(child.y);
                }
                else if (child.y < extremes.getBottom()) {
                    extremes.setBottom(child.y);
                }
            }
        }

        bounds.setX((extremes.getLeft() + extremes.getRight()) / 2);
        bounds.setY((extremes.getBottom() + extremes.getTop()) / 2);

        for (WorldObject child: children) {
            double nR = child.maxDistanceFrom(bounds);
            if (nR > r) {
                r = nR;
            }
        }

        bounds.setRadius(r);

        return bounds;
    }

    protected void addChild(WorldObject newChild) {
        children.add(newChild);
    }
    protected void removeChild(WorldObject oldChild) {
        children.remove(oldChild);
    }
}
