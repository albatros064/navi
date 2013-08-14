package com.silentcrystal.cartographer.model;

public class Grid {
	
	protected int xScale;
	protected int yScale;
	
	public Grid(int xScale, int yScale) {
		this.xScale = xScale;
		this.yScale = yScale;
	}
	
	public void setXScale(int xScale) {
		this.xScale = xScale;
	}
	public int getXScale() {
		return xScale;
	}
	
	public void setYScale(int yScale) {
		this.yScale = yScale;
	}
	public int getYScale() {
		return yScale;
	}
}
