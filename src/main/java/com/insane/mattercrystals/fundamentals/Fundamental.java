package com.insane.mattercrystals.fundamentals;

import net.minecraft.util.StatCollector;

public class Fundamental {
	
	public static final int length = Type.values().length;

	public enum Type
	{
		EARTH, FIRE, WATER, STONE, AIR;
		
		private String name;
		
		public Type()
		{
			name = StringUtils.capitalize(name().toLowerCase());
		}
		
		public String getLocalizedName()
		{
			return StatCollector.translateToLocal("string.fundamental" + name);
		}
	}
}
