package com.insane.mattercrystals.fundamentals;

public class Fundamental {
	
	public int EARTH, FIRE, WATER, STONE, AIR;
	
	public Fundamental(int earth, int fire, int water, int stone, int air)
	{
		this.EARTH = earth;
		this.FIRE = fire;
		this.WATER = water;
		this.STONE = stone;
		this.AIR = air;
	}
	
	public int total()
	{
		return EARTH+FIRE+WATER+STONE+AIR;
	}

}
