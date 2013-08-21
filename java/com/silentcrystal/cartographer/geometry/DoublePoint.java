package com.silentcrystal.cartographer.geometry;

import android.graphics.PointF;

/**
 * Created by Nathaniel on 8/17/13.
 */
public class DoublePoint {
    protected double x, y;

    public DoublePoint() {
        x = y = 0;
    }

    public DoublePoint(DoublePoint point) {
        x = point.x;
        y = point.y;
    }

    public DoublePoint(PointF point) {
        x = point.x;
        y = point.y;
    }

    public DoublePoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setX(double x) {
        this.x = x;
    }
    public double getX() {
        return x;
    }

    public void setY(double y) {
        this.y = y;
    }
    public double getY() {
        return y;
    }

    public double distanceTo(DoublePoint point) {
        double dX = point.x - x;
        double dY = point.y - y;
        return Math.sqrt(dX * dX + dY * dY);
    }

    public boolean isAboveLeftOf(DoublePoint point) {
        return x < point.x && y > point.y;
    }
    public boolean isBelowLeftOf(DoublePoint point) {
        return x < point.x && y < point.y;
    }
    public boolean isAboveRightOf(DoublePoint point) {
        return x > point.x && y > point.y;
    }
    public boolean isBelowRightOf(DoublePoint point) {
        return x > point.x && y < point.y;
    }

    public double[] toDoubleArray() {
        double[] doubles = { x, y };
        return doubles;
    }
    public float[] toFloatArray() {
        float[] floats = { (float) x, (float) y };
        return floats;
    }

    public PointF toPointF() {
        return new PointF((float) x, (float) y);
    }
}
