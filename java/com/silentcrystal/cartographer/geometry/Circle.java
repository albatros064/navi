package com.silentcrystal.cartographer.geometry;

/**
 * Created by Nathaniel on 8/15/13.
 */
public class Circle extends DoublePoint {
    protected double radius;

    public Circle() {
        super(0, 0);
        radius = 0;
    }
    public Circle(double x, double y, double radius) {
        super(x, y);
        this.radius = radius;
    }
    public Circle(Circle template) {
        super(template.x, template.y);
        radius = template.radius;
    }
    public void clone(Circle template) {
        x = template.x;
        y = template.y;
        radius = template.radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }
    public double getRadius() {
        return radius;
    }

    public double maxDistanceFrom(DoublePoint point) {
        return distanceTo(point) + radius;
    }

    public boolean intersects(DoublePoint point) {
        return (point.x * point.x + point.y * point.y) <= (radius * radius);
    }
}
