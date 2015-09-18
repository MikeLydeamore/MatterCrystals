package com.insane.mattercrystals.fundamentals;

import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class FundamentalList {

	public static HashMap<BasicStack, FundamentalData> fundamentalList = new HashMap<BasicStack, FundamentalData>(); 

	public FundamentalList()
	{

	}
	
	public static void addFundamentalsToStack(String oreDict, FundamentalData fund)
	{
		for (ItemStack stack : OreDictionary.getOres(oreDict))
		{
			System.out.println(stack.getDisplayName());
			addFundamentalsToStack(stack, fund);
		}
	}

	public static void addFundamentalsToStack(Item item, FundamentalData fund)
	{
		addFundamentalsToStack(new ItemStack(item), fund);
	}
	
	public static void addFundamentalsToStack(Block block, FundamentalData fund)
	{
		addFundamentalsToStack(new ItemStack(block), fund);
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
			if (fund.total() > f.total() || !f.isCustom()) //Worse case
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
	
	public static boolean hasFundamentals(ItemStack stack)
	{
		if (stack == null)
			return false;
		
		BasicStack bs = new BasicStack(stack);
		return fundamentalList.get(bs) != null;
	}

}
