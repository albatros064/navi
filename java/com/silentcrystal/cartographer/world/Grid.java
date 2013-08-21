package com.silentcrystal.cartographer.world;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

import com.silentcrystal.cartographer.geometry.DoublePoint;
import com.silentcrystal.cartographer.geometry.DoubleRect;

public class Grid implements WorldDrawable {
	
	protected double xScale;
	protected double yScale;
	
	public Grid(double xScale, double yScale) {
		this.xScale = xScale;
		this.yScale = yScale;
	}
	
	public void setXScale(double xScale) {
		this.xScale = xScale;
	}
	public double getXScale() {
		return xScale;
	}
	
	public void setYScale(double yScale) {
		this.yScale = yScale;
	}
	public double getYScale() {
		return yScale;
	}

    public boolean draw(Canvas canvas, ViewPort viewPort) {
        canvas.drawColor(Color.WHITE);
        double screenXScale = viewPort.toScreen(xScale);
        double screenYScale = viewPort.toScreen(yScale);
        double screenWidth = viewPort.getScreenBounds().width();
        double screenHeight = viewPort.getScreenBounds().height();

        RectF rect = viewPort.toRectF();

        Log.i("Grid:draw", "viewPort: " + rect);

        DoublePoint offset = viewPort.toScreenDouble(new DoublePoint(Math.floor(rect.left / xScale) * xScale, Math.floor(rect.top / yScale) * yScale));
        double offsetX = offset.getX();
        double offsetY = offset.getY();

        drawLines(canvas, offsetY, screenWidth, screenHeight, screenYScale, true);
        drawLines(canvas, offsetX, screenWidth, screenHeight, screenXScale, false);
        return true;
    }

    public void drawLines(Canvas canvas, double offset, double width, double height, double screenScale, boolean horizontal) {
        Log.i("Grid:draw", "Metrics: (" + offset + "," + width + "," + height + "," + screenScale + ")");
        Paint paint = new Paint(Color.DKGRAY);
        double current = offset;
        double limiter = horizontal ? height : width;
        while (current < limiter) {
            if (horizontal) {
                canvas.drawLine(0, (float) current, (float) width, (float) current, paint);
            }
            else {
                canvas.drawLine((float) current, 0, (float) current, (float) height, paint);
            }

            current += screenScale;
        }
    }
    public boolean isVisible(ViewPort viewPort) {
        return true;
    }
}
