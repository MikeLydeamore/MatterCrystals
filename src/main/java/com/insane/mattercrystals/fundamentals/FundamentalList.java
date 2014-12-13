package com.insane.mattercrystals.fundamentals;

import java.util.HashMap;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class FundamentalList {

	public static HashMap<BasicStack, Fundamental> fundamentalList = new HashMap<BasicStack, Fundamental>(); 

	public FundamentalList()
	{

	}

	public static void addFundamentalsToStack(ItemStack stack, Fundamental fund)
	{
		BasicStack bs = new BasicStack(stack);
		if (!fundamentalList.containsKey(bs))
		{	
			fundamentalList.put(bs,fund);
		}
		else
		{
			Fundamental f = fundamentalList.get(bs);
			if (fund.total() > f.total()) //Worse case
			{
				fundamentalList.put(bs, fund);
			}
		}
	}

	public static Fundamental getFundamentalsFromStack(ItemStack stack)
	{
		if (stack==null)
		{
			return null;
		}
		
		BasicStack bs = new BasicStack(stack);
		return fundamentalList.get(bs);
	}

}
