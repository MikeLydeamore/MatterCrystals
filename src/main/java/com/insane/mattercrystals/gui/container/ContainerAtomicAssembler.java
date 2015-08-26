package com.insane.mattercrystals.gui.container;

import com.insane.mattercrystals.items.MCItems;
import com.insane.mattercrystals.tileentity.TileAtomicAssembler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerAtomicAssembler extends Container {
	
	public TileAtomicAssembler te;
	
	public ContainerAtomicAssembler(InventoryPlayer player, TileAtomicAssembler entity)
	{
		te = entity;

		this.addSlotToContainer(new SlotSpecificItem(te, 0, 44, 51, new ItemStack(MCItems.itemCapsule)));
		this.addSlotToContainer(new SlotClosed(te, 1, 70, 51));
		int i;

		for (i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				this.addSlotToContainer(new Slot(player, j + i * 9 + 9,
						8 + j * 18, 94 + i * 18));
			}
		}

		for (i = 0; i < 9; ++i) {
			this.addSlotToContainer(new Slot(player, i, 8 + i * 18, 152));
		}
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player) 
	{
		return true;
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotNum)
	{
		return null;
	}

}
