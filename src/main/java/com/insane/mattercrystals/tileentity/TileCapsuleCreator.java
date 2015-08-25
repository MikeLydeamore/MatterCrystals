package com.insane.mattercrystals.tileentity;

import lombok.Getter;
import lombok.Setter;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;

import com.insane.mattercrystals.config.Config;
import com.insane.mattercrystals.fundamentals.Fundamental.Type;
import com.insane.mattercrystals.fundamentals.FundamentalData;
import com.insane.mattercrystals.fundamentals.FundamentalList;
import com.insane.mattercrystals.items.ItemCapsule;
import com.insane.mattercrystals.items.MCItems;
import com.insane.mattercrystals.network.MessageCapsuleCreatorInputUpdate;
import com.insane.mattercrystals.network.MessageMatterMelterInputUpdate;
import com.insane.mattercrystals.network.PacketHandler;
import com.insane.mattercrystals.util.StackUtil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileCapsuleCreator extends TileEntity implements ISidedInventory, IEnergyHandler {

	private ItemStack[] inventory = new ItemStack[3];
	private int itemInputSlot = 0;
	private int capsuleInputSlot = 1;
	private int outputSlot = 2;
	private int[] accessibleSlots = {itemInputSlot, capsuleInputSlot, outputSlot};

	protected EnergyStorage storage = new EnergyStorage(100000);
	@Getter @Setter
	private byte progress;
	private int RFCost;

	private int ticksSinceLast = 0;

	public TileCapsuleCreator() 
	{
		RFCost = Config.capsuleCreatorRFCostPerTick;
	}

	@Override
	public void updateEntity()
	{
		if (!worldObj.isRemote)
		{
			if (inventory[itemInputSlot] != null && inventory[capsuleInputSlot] != null
					&& FundamentalList.hasFundamentals(inventory[itemInputSlot]) && inventory[capsuleInputSlot].getItem() == MCItems.itemCapsule)
			{
				if (storage.extractEnergy(RFCost, true) == RFCost && progress < 100)
				{
					storage.extractEnergy(RFCost, false); //Extract Energy
					ticksSinceLast++;
					if (ticksSinceLast >= 20)
					{
						progress++;
						ticksSinceLast = 0;
					}
				}
				if (progress >= 100)
				{
					//Create the capsule
					ItemStack capsuleOut = new ItemStack(MCItems.itemCapsule);
					NBTTagCompound tag = new NBTTagCompound();
					FundamentalData fd = FundamentalList.getFundamentalsFromStack(inventory[itemInputSlot]);
					for (Type t : fd.getKeys())
					{
						tag.setInteger(t.name(), fd.getValue(t));
					}
					
					
					NBTTagCompound itemTag = new NBTTagCompound();
					inventory[itemInputSlot].writeToNBT(itemTag);
					
					tag.setTag("storedItem", itemTag);
					
					tag.setInteger("runs", Config.capsuleRuns);

					capsuleOut.stackTagCompound = tag;

					if (inventory[outputSlot] == null || StackUtil.canStacksMerge(capsuleOut,inventory[outputSlot]))
					{
						if (inventory[outputSlot] == null)
							inventory[outputSlot] = capsuleOut;
						else
							inventory[outputSlot].stackSize++;
						sendInputUpdatePacket(outputSlot);
						inventory[capsuleInputSlot].stackSize--;
						if (inventory[capsuleInputSlot].stackSize <= 0)
							inventory[capsuleInputSlot] = null;
						sendInputUpdatePacket(capsuleInputSlot);
						this.markDirty();
						progress = 0;
					}
				}
			}
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);

		NBTTagList invList = new NBTTagList();
		for (ItemStack s : inventory)
		{
			NBTTagCompound stackTag = new NBTTagCompound();
			if (s != null)
			{
				s.writeToNBT(stackTag);
			}
			invList.appendTag(stackTag);
		}
		tag.setTag("inventory", invList);

		tag.setInteger("ticksSinceLast", ticksSinceLast);

		tag.setByte("progress", progress);
		tag.setInteger("RFCost", RFCost);
		storage.writeToNBT(tag);

	}

	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);

		NBTTagList invList = (NBTTagList) tag.getTag("inventory");
		for (int i = 0 ; i < inventory.length ; i++)
		{
			inventory[i] = ItemStack.loadItemStackFromNBT(invList.getCompoundTagAt(i));
		}

		ticksSinceLast = tag.getInteger("ticksSinceLast");

		progress = tag.getByte("inventory");
		tag.setInteger("RFCost", RFCost);
		storage.readFromNBT(tag);
	}

	private void sendInputUpdatePacket(int slot)
	{
		PacketHandler.INSTANCE.sendToDimension(new MessageCapsuleCreatorInputUpdate(xCoord, yCoord, zCoord, slot, inventory[slot]), worldObj.provider.dimensionId);
	}

	@Override
	public Packet getDescriptionPacket() 
	{
		NBTTagCompound tagCompound = new NBTTagCompound();
		writeToNBT(tagCompound);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, this.blockMetadata, tagCompound);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
	{
		NBTTagCompound tag = pkt.func_148857_g();
		this.readFromNBT(tag);
	}

	public int getRFCost()
	{
		return RFCost;
	}

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
			int editAmount = Math.min(retStack.stackSize, amount);
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

	@Override
	public boolean canConnectEnergy(ForgeDirection from) 
	{
		return true;
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) 
	{
		return storage.receiveEnergy(maxReceive, simulate);
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) 
	{
		return storage.extractEnergy(maxExtract, simulate);
	}

	@Override
	public int getEnergyStored(ForgeDirection from) 
	{
		return storage.getEnergyStored();
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) 
	{
		return storage.getMaxEnergyStored();
	}

	public void setEnergyStored(int energy)
	{
		storage.setEnergyStored(energy);
	}

}
