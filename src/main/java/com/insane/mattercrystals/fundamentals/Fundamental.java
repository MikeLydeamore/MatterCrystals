package com.insane.mattercrystals.fundamentals;

import org.apache.commons.lang3.StringUtils;

import net.minecraft.util.StatCollector;


public class Fundamental {
	
	public static final int length = Type.values().length;

	public enum Type
	{
		EARTH, FIRE, WATER, STONE, AIR;
		
		private String name;
		
		Type()
		{
			name = StringUtils.capitalize(name().toLowerCase());
		}
		
		public String getLocalizedName()
		{
			return StatCollector.translateToLocal("string.fundamental" + name);
		}
	}
}
