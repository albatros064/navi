package com.silentcrystal.cartographer.model;

import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import android.graphics.Canvas;
import android.graphics.Paint;

import android.util.AttributeSet;
import android.util.Log;

public class RenderSurface extends SurfaceView implements SurfaceHolder.Callback {
	
	protected RenderThread renderThread;

	public RenderSurface(Context context, AttributeSet attrs) {
        super(context, attrs);
        
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        
        renderThread = new RenderThread(surfaceHolder, context);
	}
	
	public void setWorld(World world) {
		renderThread.setWorld(world);
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
		
	}
	
	public class RenderThread extends Thread {
		
		protected World world;
		
		protected SurfaceHolder surfaceHolder;
		protected Context context;
		
		protected boolean running = false;
		
		public RenderThread(SurfaceHolder surfaceHolder, Context context) {
			this.surfaceHolder = surfaceHolder;
			this.context = context;
			this.running = false;
		}
		
		public void run() {
			while (running) {
				Canvas canvas = surfaceHolder.lockCanvas();
				Paint paint = new Paint();
				paint.setColor(0xff00ff00);
				canvas.drawCircle(40.0f, 50.0f, 20.0f, paint);
				//Log.i("RenderThread", "loop");
				surfaceHolder.unlockCanvasAndPost(canvas);
			}
		}
		
		public void setRunning(boolean running) {
			this.running = running;
		}
		
		public void setWorld(World world) {
			this.world = world;
		}
	}
}
