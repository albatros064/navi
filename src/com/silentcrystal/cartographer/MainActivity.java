package com.silentcrystal.cartographer;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

import com.silentcrystal.cartographer.model.RenderSurface;
import com.silentcrystal.cartographer.model.World;

public class MainActivity extends Activity {
	
	protected RenderSurface renderSurface;
	protected World world;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        renderSurface = (RenderSurface) findViewById(R.id.mainSurface);
        world = new World();
        
        renderSurface.setWorld(world);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
