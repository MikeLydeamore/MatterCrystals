package com.insane.mattercrystals.gui.container;

import com.insane.mattercrystals.fundamentals.FundamentalList;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotMatterMelter extends Slot {
	
	private int id;
	
	public SlotMatterMelter(IInventory inventory, int index, int x, int y)
	{
		super(inventory, index, x, y);
		
		this.id = index;
	}
	
	@Override
	public boolean isItemValid(ItemStack stack)
	{
		return FundamentalList.hasFundamentals(stack);
	}

}
