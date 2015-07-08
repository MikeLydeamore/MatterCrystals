package com.insane.mattercrystals.fundamentals;

import java.util.EnumMap;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;

import com.insane.mattercrystals.fundamentals.Fundamental.Type;

public class FundamentalData {

	private EnumMap<Type, Integer> map;
	private boolean custom;
	
	public FundamentalData(Pair<Type, Integer> ... values)
	{
		map = new EnumMap<Type, Integer>(Type.class);
		for (Pair<Type, Integer> p : values)
		{
			if (p.getRight() != 0)
				map.put(p.getLeft(), p.getRight());
		}
		
		this.custom = false;
	}
	
	public FundamentalData(boolean custom, Pair<Type, Integer> ... values)
	{
		map = new EnumMap<Type, Integer>(Type.class);
		for (Pair<Type, Integer> p : values)
		{
			map.put(p.getLeft(), p.getRight());
		}
		
		this.custom = custom;
	}
	
	public void setCustom(boolean newValue)
	{
		this.custom = newValue;
	}
	
	public boolean isCustom()
	{
		return this.custom;
	}
	
	public int total()
	{
		int total=0;
		for (Integer i : map.values())
		{
			total += i;
		}
		return total;
	}
	
	public int getValue(Type type)
	{
		if (!map.containsKey(type))
			return 0;
		else
			return map.get(type);
	}
	
	public Set<Type> getKeys()
	{
		return map.keySet();
	}
	
	public void addToValue(Type type, int amount)
	{
		if (!map.containsKey(type))
			map.put(type, amount);
		else
			map.put(type, map.get(type)+amount);
	}
	
	public void correctStackSize(int stacksize)
	{
		for (Type t : Type.values())
		{
			if (map.get(t) > 0)
				map.put(t, Math.max(map.get(t)/stacksize, 1)); //This is lossy, but so be it.
			else
				map.put(t, map.get(t));
		}
	}
	
	public int getNumFundamentals()
	{
		int ret = 0;
		for (Type t : Type.values())
		{
			if (map.get(t) > 0)
				ret++;
		}
		
		return ret;
	}

}
