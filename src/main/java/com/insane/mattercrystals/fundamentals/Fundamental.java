package com.insane.mattercrystals.fundamentals;

import net.minecraft.util.StatCollector;

public class Fundamental {
	
	public static final int length = Type.values().length;

	public enum Type
	{
		EARTH, FIRE, WATER, STONE, AIR;
	}

	public static String getTranslatedString(Type t)
	{
		String ret = "string.fundamental";
		switch (t)
		{
		case EARTH:
			ret += "Earth";
			break;
		case FIRE:
			ret += "Fire";
			break;
		case WATER:
			ret += "Water";
			break;
		case STONE:
			ret += "Stone";
			break;
		case AIR:
			ret += "Air";
		}
		
		return StatCollector.translateToLocal(ret);

	}

}
