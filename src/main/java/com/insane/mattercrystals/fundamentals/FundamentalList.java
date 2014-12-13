package com.insane.mattercrystals.fundamentals;

import java.util.HashMap;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class FundamentalList {

	public static HashMap<BasicStack, FundamentalData> fundamentalList = new HashMap<BasicStack, FundamentalData>(); 

	public FundamentalList()
	{

	}

	public static void addFundamentalsToStack(ItemStack stack, FundamentalData fund)
	{
		BasicStack bs = new BasicStack(stack);
		if (!fundamentalList.containsKey(bs))
		{	
			fundamentalList.put(bs,fund);
		}
		else
		{
			FundamentalData f = fundamentalList.get(bs);
			if (fund.total() > f.total()) //Worse case
			{
				fundamentalList.put(bs, fund);
			}
		}
	}

	public static FundamentalData getFundamentalsFromStack(ItemStack stack)
	{
		if (stack==null)
		{
			return null;
		}
		
		BasicStack bs = new BasicStack(stack);
		return fundamentalList.get(bs);
	}

}
