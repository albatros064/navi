package com.silentcrystal.cartographer.android;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.Rect;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import android.graphics.Canvas;
import android.graphics.Paint;

import android.util.AttributeSet;
import android.util.Log;

import com.silentcrystal.cartographer.world.ViewPort;
import com.silentcrystal.cartographer.world.World;

public class RenderSurface extends SurfaceView implements SurfaceHolder.Callback {

    protected static final String TOUCH_TAG = "RenderSurface:touch";

	private RenderThread renderThread;
    private World world;
    private ViewPort viewPort;

    private double rawX;
    private double rawY;
    private int eventAction;

	public RenderSurface(Context context, AttributeSet attrs) {
        super(context, attrs);

        rawX = rawY = 0;
        eventAction = MotionEvent.ACTION_UP;
        
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        
        renderThread = new RenderThread(surfaceHolder, context);
	}
	
	public void setWorld(World world) {
        this.world = world;
		renderThread.setWorld(world);
	}
    public void setViewPort(ViewPort viewPort) {
        this.viewPort = viewPort;
        renderThread.setViewPort(viewPort);

    }
	
	public void surfaceCreated(SurfaceHolder holder) {
		renderThread.setRunning(true);
		renderThread.start();
	}
	public void surfaceDestroyed(SurfaceHolder holder) {
		boolean retry = true;
		renderThread.setRunning(false);
        while (retry) {
            try {
                renderThread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
	}
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        viewPort.setScreenBounds(new Rect(0, 0, width, height));
        renderThread.forceRedraw();
	}

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int lastEventAction = eventAction;
        eventAction = MotionEventCompat.getActionMasked(event);

        double oldRawX = rawX;
        double oldRawY = rawY;


        rawX = event.getRawX();
        rawY = event.getRawY();

        switch (eventAction) {
            case (MotionEvent.ACTION_DOWN):
                Log.d(TOUCH_TAG,"Action was DOWN");
                // Determine if there is anything under the
                break;
            case (MotionEvent.ACTION_MOVE):
                Log.d(TOUCH_TAG,"Action was MOVE");
                PointF deltaPoint = new PointF((float) (oldRawX - rawX), (float) (oldRawY - rawY));
                Log.i(TOUCH_TAG, "point: " + deltaPoint);
                viewPort.pan(deltaPoint);
                break;
            case (MotionEvent.ACTION_UP):
                Log.d(TOUCH_TAG,"Action was UP");
                if (lastEventAction == MotionEvent.ACTION_DOWN) {
                    Log.i(TOUCH_TAG, "Action completed a CLICK");
                }
                break;
            case (MotionEvent.ACTION_CANCEL):
                Log.d(TOUCH_TAG,"Action was CANCEL");
                break;
            default:
                return super.onTouchEvent(event);
        }

        renderThread.forceRedraw();

        return true;
    }
	
	public class RenderThread extends Thread {
		
		protected World world;

        protected boolean redrawForced;
		
		protected SurfaceHolder surfaceHolder;
		protected Context context;
		
		protected boolean running;
		
		public RenderThread(SurfaceHolder surfaceHolder, Context context) {
			this.surfaceHolder = surfaceHolder;
			this.context = context;
			this.running = false;
            this.redrawForced = false;
		}

        public void forceRedraw() {
            redrawForced = true;
        }

		
		public void run() {
            // Sleep-wait until we are told to stop, then wait until any in-progress render finishes...
			while (running) {
                if (redrawForced || world.isChanged()) {
                    if (redrawForced) {
                        redrawForced = false;
                    }
                    Canvas canvas = surfaceHolder.lockCanvas();
                    world.draw(canvas);
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
                else {
                    try {
                        Thread.sleep(25);
                    }
                    catch (InterruptedException iE) {}
                }
            }
		}
		
		public void setRunning(boolean running) {
			this.running = running;
		}
		
		public void setWorld(World world) {
			this.world = world;
		}
        public void setViewPort(ViewPort viewPort) {
            this.world.setViewPort(viewPort);
        }
	}
}
