package com.silentcrystal.cartographer.world;

import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import com.silentcrystal.cartographer.geometry.Circle;
import com.silentcrystal.cartographer.geometry.DoublePoint;
import com.silentcrystal.cartographer.geometry.DoubleRect;

/**
 * Created by Nathaniel on 8/17/13.
 */
public class ViewPort extends Circle {
    private Rect screenBounds;

    Matrix worldTransform;
    Matrix worldTransformInverse;

    boolean valid;

    public ViewPort() {
        super(0, 0, 50);
        screenBounds = new Rect(0,0,800,1280);

        init();
    }

    public ViewPort(Rect screen, Circle world) {
        super(world);

        screenBounds = screen;

        init();
    }

    public ViewPort(Rect screen, DoubleRect world) {
        x = (world.getLeft() + world.getRight()) / 2;
        y = (world.getTop() + world.getBottom()) / 2;

        radius = (world.getRight() - world.getLeft()) / 2;

        screenBounds = screen;

        init();
    }

    private void init() {
        worldTransform = new Matrix();
        worldTransformInverse = new Matrix();
        valid = false;
    }

    public void setRadiusFixMatrix(double radius) {
        this.radius = radius;
        recomputeTransformationMatrix();
    }
    public void setXFixMatrix(double x) {
        this.x = x;
        recomputeTransformationMatrix();
    }
    public void setYFixMatrix(double y) {
        this.y = y;
        recomputeTransformationMatrix();
    }

    public void setScreenBounds(Rect screenBounds) {
        this.screenBounds = screenBounds;
        recomputeTransformationMatrix();
    }
    public Rect getScreenBounds() {
        return screenBounds;
    }

    public boolean isValid() {
        return valid;
    }

    public Point toScreenInt(DoublePoint point) {
        float[] pointF = point.toFloatArray();
        worldTransform.mapPoints(pointF);
        return new Point((int) pointF[0], (int) pointF[1]);
    }
    public DoublePoint toScreenDouble(DoublePoint point) {
        float[] pointF = point.toFloatArray();
        worldTransform.mapPoints(pointF);
        return new DoublePoint(pointF[0], pointF[1]);
    }
    public double toScreen(double length) {
        return worldTransform.mapRadius((float) length);
    }

    public DoublePoint fromScreen(Point point) {
        return fromScreen(new PointF(point));
    }
    public DoublePoint fromScreen(PointF point) {
        float[] pointF = { point.x, point.y };
        worldTransformInverse.mapPoints(pointF);
        return new DoublePoint(pointF[0], pointF[1]);
    }
    public double fromScreen(double length) {
        return worldTransformInverse.mapRadius((float) length);
    }

    private void recomputeTransformationMatrix() {
        RectF dest = new RectF(screenBounds);
        Log.i("ViewPort:recompute", "recomputing transformation matrix: " + this.toRectF() + " -> " + dest);
        boolean result = worldTransform.setRectToRect(this.toRectF(), dest, Matrix.ScaleToFit.CENTER);
        if (!result) {
            Log.e("ViewPort:recompute", "couldn't set world transform");
        }
        worldTransform.invert(worldTransformInverse);

        valid = true;
    }

    public void pan(PointF panVector) {
        Log.i("ViewPort:pan", "" + panVector);
        double panX = fromScreen(panVector.x);
        double panY = fromScreen(panVector.y);
        panX = panVector.x < 0 ? -panX : panX;
        panY = panVector.y < 0 ? -panY : panY;
        pan(new DoublePoint(panX, panY));
    }
    public void pan(DoublePoint panVector) {
        Log.i("ViewPort:pan", "vector: " + panVector.toPointF());
        x += panVector.getX();
        y += panVector.getY();

        clamp(400, 400);

        Log.i("ViewPort:pan", "viewPort: " + toRectF());

        recomputeTransformationMatrix();
    }

    private void clamp(double maxX, double maxY) {
        if (x > maxX) {
            x = maxX;
        }
        else if (x < -maxX) {
            x = -maxX;
        }

        if (y > maxY) {
            y = maxY;
        }
        else if (y < -maxY) {
            y = -maxY;
        }
    }

    public RectF toRectF() {
        double vertSize = radius * screenBounds.bottom / screenBounds.right;
        return new RectF((float) (x - radius), (float) (y - vertSize), (float) (x + radius), (float) (y + vertSize));
    }
}
