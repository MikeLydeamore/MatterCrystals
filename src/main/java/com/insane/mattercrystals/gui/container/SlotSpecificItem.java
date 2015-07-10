package com.insane.mattercrystals.gui.container;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.insane.mattercrystals.util.StackUtil;

public class SlotSpecificItem extends Slot {
	
	private int id;
	private ItemStack stack;
	
	public SlotSpecificItem(IInventory inventory, int index, int x, int y, ItemStack stack)
	{
		super(inventory, index, x, y);
		
		this.id = index;
		this.stack = stack;
	}
	
	@Override
	public boolean isItemValid(ItemStack stack)
	{
		return StackUtil.areStacksEqualWithoutStacksize(this.stack, stack);
	} 

}
