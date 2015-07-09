package com.insane.mattercrystals.util;

import com.insane.mattercrystals.blocks.BlockCrystal;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class StackUtil {
	
	public static boolean areStacksEqualWithoutStacksize(ItemStack stack1, ItemStack stack2)
	{
		if (stack1 != null && stack2 != null)
			return stack1.getItem() == stack2.getItem() && stack1.getItemDamage() == stack2.getItemDamage();
		
		return false;
	}
	
	public static boolean canStacksMerge(ItemStack stack1, ItemStack stack2)
	{
		if (stack1 == null)
			return true;
		else
		{
			if (!areStacksEqualWithoutStacksize(stack1, stack2))
				return false;
			else
			{
				return stack1.stackSize+stack2.stackSize <= stack1.getMaxStackSize();
			}
		}
	}
	
	public static boolean isItemCrystal(ItemStack stack)
	{
		if (stack == null)
			return false;
		
		return Block.getBlockFromItem(stack.getItem()) instanceof BlockCrystal;
	}

}