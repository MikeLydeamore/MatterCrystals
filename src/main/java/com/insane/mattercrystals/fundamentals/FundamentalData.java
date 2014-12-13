package com.insane.mattercrystals.fundamentals;

import java.util.EnumMap;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;

import com.insane.mattercrystals.fundamentals.Fundamental.Type;

public class FundamentalData {

	private EnumMap<Type, Integer> map;
	
	public FundamentalData(Pair<Type, Integer> ... values)
	{
		map = new EnumMap<Type, Integer>(Type.class);
		for (Pair<Type, Integer> p : values)
		{
			map.put(p.getLeft(), p.getRight());
		}
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
			map.put(t, map.get(t)/stacksize);
		}
	}

}
