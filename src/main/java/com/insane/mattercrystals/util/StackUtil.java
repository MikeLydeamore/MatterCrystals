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
		if (stack1 == null || stack2 == null)
			return false;
		return stack1.isItemEqual(stack2) && ItemStack.areItemStackTagsEqual(stack1, stack2)
				&& stack1.stackSize+stack2.stackSize <= stack1.getMaxStackSize();
	}

	public static boolean isItemCrystal(ItemStack stack)
	{
		if (stack == null)
			return false;

		return Block.getBlockFromItem(stack.getItem()) instanceof BlockCrystal;
	}

}
