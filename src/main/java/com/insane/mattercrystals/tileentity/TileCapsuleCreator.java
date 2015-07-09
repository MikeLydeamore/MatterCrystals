package com.insane.mattercrystals.tileentity;

import com.insane.mattercrystals.fundamentals.FundamentalList;
import com.insane.mattercrystals.items.ItemCapsule;
import com.insane.mattercrystals.util.StackUtil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class TileCapsuleCreator extends TileEntity implements ISidedInventory{
	
	private ItemStack[] inventory = new ItemStack[3];
	private int itemInputSlot = 0;
	private int capsuleInputSlot = 1;
	private int outputSlot = 2;
	private int[] accessibleSlots = {itemInputSlot, capsuleInputSlot, outputSlot};
	
	public TileCapsuleCreator() {}

	@Override
	public int getSizeInventory() 
	{
		return inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot) 
	{
		return inventory[slot];
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount) 
	{
		if (inventory[slot] != null)
		{
			ItemStack retStack = inventory[slot].copy();
			int editAmount = Math.max(retStack.stackSize-amount, 0);
			retStack.stackSize = editAmount;
			inventory[slot].stackSize -= editAmount;
			if (inventory[slot].stackSize == 0)
				inventory[slot] = null;
			
			return retStack;
		}
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int p_70304_1_) 
	{
		return null;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) 
	{
		if (slot < inventory.length)
			inventory[slot] = stack;
		
	}

	@Override
	public String getInventoryName() 
	{
		return "Capsule Creator";
	}

	@Override
	public boolean hasCustomInventoryName() 
	{
		return true;
	}

	@Override
	public int getInventoryStackLimit() 
	{
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer p_70300_1_) 
	{
		return true;
	}

	@Override
	public void openInventory() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closeInventory() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) 
	{
		if (slot == outputSlot)
			return false;
		
		if (slot == itemInputSlot)
			return FundamentalList.hasFundamentals(stack) && StackUtil.canStacksMerge(inventory[slot], stack);
		
		if (slot == capsuleInputSlot)
			return stack.getItem() instanceof ItemCapsule && StackUtil.canStacksMerge(inventory[slot], stack);
		
		return false;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int p_94128_1_) 
	{
		return accessibleSlots;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack,	int side) 
	{
		return isItemValidForSlot(slot, stack);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side) 
	{
		if (slot == outputSlot && inventory[slot] != null)
			return true;
		
		return false;
	}

}