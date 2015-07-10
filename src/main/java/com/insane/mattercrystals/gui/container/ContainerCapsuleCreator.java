package com.insane.mattercrystals.gui.container;

import com.insane.mattercrystals.items.MCItems;
import com.insane.mattercrystals.tileentity.TileCapsuleCreator;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerCapsuleCreator extends Container {
	
	public TileCapsuleCreator te;
	private int lastEnergy;
	private byte lastProgress;
	
	public ContainerCapsuleCreator(InventoryPlayer player, TileCapsuleCreator entity)
	{
		te = entity;
		
		this.addSlotToContainer(new SlotRequiresFundmentals(te, 0, 44, 40));
		this.addSlotToContainer(new SlotSpecificItem(te, 1, 44, 60, new ItemStack(MCItems.itemCapsule)));

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
	
	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
		for (int i = 0 ; i < this.crafters.size(); i++)
		{
			ICrafting icrafting = (ICrafting) this.crafters.get(i);
			if (this.lastEnergy != te.getEnergyStored(null))
			{
				icrafting.sendProgressBarUpdate(this, 0, te.getEnergyStored(null));
			}
		}
		this.lastEnergy = te.getEnergyStored(null);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void updateProgressBar(int par1, int par2)
	{
		if (par1 == 0)
			te.setEnergyStored(par2);
	}
	

}
