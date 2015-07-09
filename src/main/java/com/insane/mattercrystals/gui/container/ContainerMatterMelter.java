package com.insane.mattercrystals.gui.container;

import com.insane.mattercrystals.fundamentals.FundamentalList;
import com.insane.mattercrystals.tileentity.TileMatterMelter;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;


public class ContainerMatterMelter extends Container {

	public TileMatterMelter te;
	private int lastEnergy;
	private byte lastProgress;

	public ContainerMatterMelter(InventoryPlayer player, TileMatterMelter entity)
	{
		te = entity;

		this.addSlotToContainer(new SlotMatterMelter(te, 0, 44, 51));

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
		ItemStack stack = null;
		Slot slot = this.getSlot(slotNum);

		if (slot != null && slot.getHasStack())
		{

			ItemStack stackInSlot = slot.getStack();
			stack = stackInSlot.copy();
			if (slotNum > 0 && slotNum <= 36) //In the player
			{
				if (FundamentalList.hasFundamentals(stackInSlot))
				{
					if (this.te.canInsertItem(0, stackInSlot, 0))
					{
						if (!this.mergeItemStack(stackInSlot, 0, 1, false))
							return null;
					}
				}
			}

			else if (slotNum == 0)
			{
				if (!this.mergeItemStack(stackInSlot, 1, 36, false))
					return null;
			}

			if (stackInSlot.stackSize == 0) {
				slot.putStack(null);
			} else {
				slot.onSlotChanged();
			}

			if (stackInSlot.stackSize == stack.stackSize) {
				return null;
			}
			slot.onPickupFromSlot(player, stackInSlot);
		}

		return stack;
	}

	@Override
	public void detectAndSendChanges() 
	{
		super.detectAndSendChanges();
		for (int i = 0; i < this.crafters.size(); i++) {
			ICrafting icrafting = (ICrafting) this.crafters.get(i);
			if (this.lastEnergy != te.getEnergyStored(null)) 
			{
				icrafting.sendProgressBarUpdate(this, 0, te.getEnergyStored(null));
			}
			if (this.lastProgress != te.getProgress())
			{
				icrafting.sendProgressBarUpdate(this, 1, te.getProgress());
			}
		}
		this.lastEnergy = te.getEnergyStored(null);
		this.lastProgress = te.getProgress();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void updateProgressBar(int par1, int par2) 
	{
		if (par1 == 0)
			te.setEnergyStored(par2);
		else if (par1 == 1)
			te.setProgress((byte) par2); 
	}

}
