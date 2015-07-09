package com.insane.mattercrystals.tileentity;

import com.insane.mattercrystals.config.Config;
import com.insane.mattercrystals.fundamentals.FundamentalData;
import com.insane.mattercrystals.fundamentals.FundamentalList;
import com.insane.mattercrystals.network.MessageAtomicAssemblerItemUpdate;
import com.insane.mattercrystals.network.MessageMatterMelterInputUpdate;
import com.insane.mattercrystals.network.PacketHandler;
import com.insane.mattercrystals.util.StackUtil;

import lombok.Getter;
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
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileAtomicAssembler extends TileEntity implements ISidedInventory, IFluidHandler {
	
	private ItemStack copyStack;
	private ItemStack outputStack;
	
	private FluidTank[] tanks = null;
	
	@Getter
	private boolean inProgress;
	
	private byte progress;
	
	private int ticksSinceLast = 0;
	
	private int RFCost;
	
	public TileAtomicAssembler()
	{
		RFCost = Config.assemblerRFCostPerTick;		
	}
	
	@Override
	public void updateEntity()
	{
		ticksSinceLast++;
		if (copyStack != null)
		{
			if (!this.isInProgress())
			{
				this.inProgress = true;
				FundamentalData data = FundamentalList.getFundamentalsFromStack(copyStack);
				tanks = new FluidTank[data.getNumFundamentals()];
			}
			else if (ticksSinceLast >= 20)
			{
				
			}
		}
		
		else if (this.isInProgress()) //Item removed - cancel things!
		{
			tanks = null;
			this.inProgress = false;
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		
		//Items
		NBTTagList stackList = new NBTTagList();
		
		NBTTagCompound copyTag = new NBTTagCompound();
		copyStack.writeToNBT(copyTag);
		stackList.appendTag(copyTag);
		copyTag = new NBTTagCompound();
		outputStack.writeToNBT(copyTag);
		stackList.appendTag(copyTag);
		
		tag.setTag("itemstacks", stackList);
		
		//Progress
		tag.setBoolean("inProgress", inProgress);
		tag.setByte("progress", progress);
		tag.setInteger("ticksSinceLast", ticksSinceLast);
		
		//RF
		tag.setInteger("RFCost", RFCost);
		
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		
		//Items
		NBTTagList list = (NBTTagList) tag.getTag("itemstacks");
		if (list != null)
		{
			copyStack = ItemStack.loadItemStackFromNBT(list.getCompoundTagAt(0));
			outputStack = ItemStack.loadItemStackFromNBT(list.getCompoundTagAt(1));
		}
		
		//Progress
		inProgress = tag.getBoolean("inProgress");
		progress = tag.getByte("progress");
		ticksSinceLast = tag.getInteger("ticksSinceLast");
		
		//RF
		RFCost = tag.getInteger("RFCost");
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

	@Override
	public int getSizeInventory() 
	{
		return 2;
	}

	@Override
	public ItemStack getStackInSlot(int slot) 
	{
		if (slot == 0)
			return this.copyStack;
		if (slot == 1)
			return this.outputStack;
		return null;
	}

	@Override
	public ItemStack decrStackSize(int slot, int quantity) 
	{
		if (slot == 0)
		{
			ItemStack ret = this.copyStack.copy();
			if (quantity > copyStack.stackSize)
				ret.stackSize = copyStack.stackSize;
			else
				ret.stackSize = quantity;
			copyStack.stackSize = Math.max(copyStack.stackSize-quantity, 0);
			if (copyStack.stackSize == 0)
				copyStack = null;
			sendInventoryUpdatePacket(0);
			return ret;
		}
		if (slot == 1)
		{
			ItemStack ret = this.outputStack.copy();
			if (quantity > outputStack.stackSize)
				ret.stackSize = outputStack.stackSize;
			else
				ret.stackSize = quantity;
			outputStack.stackSize = Math.max(outputStack.stackSize-quantity, 0);
			if (outputStack.stackSize == 0)
				outputStack = null;
			sendInventoryUpdatePacket(1);
			return ret;
		}
		
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) 
	{
		if (slot == 0)
			return this.copyStack;
		if (slot == 1)
			return this.outputStack;
		return null;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) 
	{
		if (slot == 0)
		{
			if (!ItemStack.areItemStacksEqual(this.copyStack, stack))
			{
				this.copyStack = stack;
				sendInventoryUpdatePacket(0);
			}
		}
		
		else if (slot == 1)
		{
			if (!ItemStack.areItemStacksEqual(this.outputStack, stack))
			{
				this.outputStack = stack;
				sendInventoryUpdatePacket(1);
			}
		}
	}

	@Override
	public String getInventoryName() 
	{
		return "AtomicAssembler";
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
		if (!FundamentalList.hasFundamentals(stack))
			return false;
		if (slot == 0 && StackUtil.canStacksMerge(this.copyStack, stack))
			return true;
		
		return false;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int p_94128_1_) 
	{
		return new int[]{0, 1};
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side) 
	{
		if (slot == 0)
		{
			return FundamentalList.hasFundamentals(stack) && StackUtil.canStacksMerge(this.copyStack, stack);
		}
		return false;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side) 
	{
		if (slot == 1)
			return stack != null;
			
		return false;
	}

	private void sendInventoryUpdatePacket(int slot)
	{
		if (slot == 0)
			PacketHandler.INSTANCE.sendToDimension(new MessageAtomicAssemblerItemUpdate(xCoord, yCoord, zCoord, this.copyStack, 0), worldObj.provider.dimensionId);
		if (slot == 1)
			PacketHandler.INSTANCE.sendToDimension(new MessageAtomicAssemblerItemUpdate(xCoord, yCoord, zCoord, this.outputStack, 1), worldObj.provider.dimensionId);
	}
	
	public void setSlot(ItemStack stack, int slot) 
	{
		if (slot == 0)
			this.copyStack = stack;
		if (slot == 1)
			this.outputStack = stack;		
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) 
	{
		if (!this.isInProgress())
			return 0;
		return 0;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource,
			boolean doDrain) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		// TODO Auto-generated method stub
		return null;
	}

}
