package com.silentcrystal.cartographer.geometry;

/**
 * Created by Nathaniel on 8/17/13.
 */
public class DoubleRect {
    protected double left, right;
    protected double top, bottom;

    public DoubleRect() {
        left = right = top = bottom = 0;
    }

    public DoubleRect(double left, double top, double right, double bottom) {
        this.left   = left;
        this.top    = top;
        this.right  = right;
        this.bottom = bottom;
    }

    public void setLeft(double left) {
        this.left = left;
    }
    public double getLeft() {
        return left;
    }

    public void setRight(double right) {
        this.right = right;
    }
    public double getRight() {
        return right;
    }

    public void setTop(double top) {
        this.top = top;
    }
    public double getTop() {
        return top;
    }

    public void setBottom(double bottom) {
        this.bottom = bottom;
    }
    public double getBottom() {
        return bottom;
    }

    public double width() {
        return right - left;
    }
    public double height() {
        return top - bottom;
    }

    public DoublePoint topLeft() {
        return new DoublePoint(left, top);
    }
    public DoublePoint topRight() {
        return new DoublePoint(right, top);
    }
    public DoublePoint bottomLeft() {
        return new DoublePoint(left, bottom);
    }
    public DoublePoint bottomRight() {
        return new DoublePoint(right, bottom);
    }

    public boolean intersects(Circle circle) {
        double x = circle.getX();
        double y = circle.getY();
        double r = circle.getRadius();

        if ((x + r) < left || (x - r) > right || (y + r) < bottom || (y - r) > top) {
            return false;
        }

        DoublePoint topLeft     = topLeft(),
                    topRight    = topRight(),
                    bottomLeft  = bottomLeft(),
                    bottomRight = bottomRight();

        if ((circle.isAboveLeftOf (topLeft ) && !circle.intersects(topLeft )) || (circle.isBelowLeftOf (bottomLeft ) && !circle.intersects(bottomLeft ))) {
            return false;
        }
        if ((circle.isAboveRightOf(topRight) && !circle.intersects(topRight)) || (circle.isBelowRightOf(bottomRight) && !circle.intersects(bottomRight))) {
            return false;
        }

        return true;
    }
}
