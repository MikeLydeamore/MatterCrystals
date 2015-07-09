package com.insane.mattercrystals.tileentity;

import java.util.ArrayList;
import java.util.HashMap;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;

import com.insane.mattercrystals.config.Config;
import com.insane.mattercrystals.fluids.MCFluids;
import com.insane.mattercrystals.fundamentals.Fundamental;
import com.insane.mattercrystals.fundamentals.FundamentalData;
import com.insane.mattercrystals.fundamentals.FundamentalList;
import com.insane.mattercrystals.network.MessageMatterMelterFluidUpdate;
import com.insane.mattercrystals.network.MessageMatterMelterInputUpdate;
import com.insane.mattercrystals.network.PacketHandler;
import com.insane.mattercrystals.util.StackUtil;

import lombok.Getter;
import lombok.Setter;
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

public class TileMatterMelter extends TileEntity implements ISidedInventory, IFluidHandler, IEnergyHandler {

	@Getter @Setter
	private ItemStack inputSlot;
	private FluidTank[] tanks = new FluidTank[4];
	@Getter
	private int RFCost;
	
	private int ticksSinceLast;
	
	@Getter
	private final int fluidMultiplier = 100;
	
	@Getter @Setter
	private byte progress; //0 = not started, 100 = finished.
	
	public TileMatterMelter()
	{
		RFCost = Config.melterRFCostPerTick;
		for (int i = 0; i < tanks.length; i++)
			tanks[i] = new FluidTank(Config.melterTankSize);
		
		RFCost = Config.melterRFCostPerTick;
		
		progress = 0;
	}
	
	@Override
	public void updateEntity()
	{
		if (!worldObj.isRemote)
		{
			if (this.inputSlot != null && FundamentalList.hasFundamentals(this.inputSlot)
					&& this.extractEnergy(null, RFCost, true) >= RFCost && this.hasSpaceForMelted(this.inputSlot))
			{
				ticksSinceLast++;
				this.extractEnergy(null, RFCost, false);
				
				if (ticksSinceLast == 20)
				{
					ticksSinceLast = 0;
					progress++;
					System.out.println(progress);
					
					if (isDone())
					{
						progress = 0;
						
						//Melt the item
						FundamentalData fs = FundamentalList.getFundamentalsFromStack(this.inputSlot);
						for (Fundamental.Type f : fs.getKeys())
						{
							int emptyIndex = -1;
							if (fs.getValue(f) == 0)
								continue;
							FluidStack fluid = new FluidStack(MCFluids.fluids.get(f), fs.getValue(f)*fluidMultiplier);
							for (int i = 0 ; i < tanks.length ; i++)
							{
								if (tanks[i].getFluid() == null)
								{
									if (emptyIndex == -1)
										emptyIndex = i;
								}
								else if (spaceInTank(i, fluid))
								{
									tanks[i].fill(fluid, true);
									emptyIndex = -1;
									sendFluidUpdatePacket(i);
									break;
								}
							}
							if (emptyIndex != -1)
							{
								tanks[emptyIndex].fill(fluid, true);
								sendFluidUpdatePacket(emptyIndex);
								emptyIndex = -1;
							}
						}					
						this.inputSlot.stackSize--;
						if (this.inputSlot.stackSize == 0)
							this.inputSlot = null;
						sendInputUpdatePacket();
					}
				}
			}
			else if (ticksSinceLast > 0)
			{
				ticksSinceLast=0; //Lose progress if item removed.
				progress = 0;
			}
		}
	}
	
	private boolean hasSpaceForMelted(ItemStack stack)
	{
		if (stack == null)
			return false;
		if (!FundamentalList.hasFundamentals(stack))
			return false;
		
		FundamentalData fs = FundamentalList.getFundamentalsFromStack(stack);
		HashMap<Integer, Fundamental.Type> allocatedTanks = new HashMap<Integer, Fundamental.Type>();
		HashMap<Fundamental.Type, Boolean> allocated = new HashMap<Fundamental.Type, Boolean>();
		for (Fundamental.Type f : fs.getKeys())
		{
			Fluid fluid = MCFluids.fluids.get(f);
			if (fs.getValue(f) == 0)
				continue;
			FluidStack fluidStack = new FluidStack(fluid, fs.getValue(f)*fluidMultiplier);
			for (int i = 0 ; i < tanks.length ; i++)
			{
				if (!allocatedTanks.containsKey(i) && !allocated.containsKey(f) && spaceInTank(i, fluidStack))
				{
					allocatedTanks.put(i, f);
					allocated.put(f, true);
				}
			}
		}
		
		for (Fundamental.Type f : fs.getKeys())
		{
			if (fs.getValue(f) == 0)
				continue;
			if (allocated.get(f) == null)
			{
				return false;
			}
		}
		
		return true;	
	}
	
	private boolean spaceInTank(int tankNum, FluidStack fluid)
	{
		if (tanks[tankNum].getFluid() == null)
			return true;
		int fillAmount = tanks[tankNum].fill(fluid, false);
		return fillAmount == fluid.amount;
	}
	
	public boolean isDone()
	{
		return progress>=10;
	}

	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		if (inputSlot != null)
		{
			NBTTagList list = new NBTTagList();
			NBTTagCompound stackTag = new NBTTagCompound();
			inputSlot.writeToNBT(stackTag);
			list.appendTag(stackTag);

			tag.setTag("inputSlot", list);
		}
		
		//Fluid Tanks
		NBTTagList tanksList = new NBTTagList();
		for (int i = 0 ; i < tanks.length ; i++)
		{
			NBTTagCompound tankTag = new NBTTagCompound();
			if (tanks[i] != null)
			{
				tanks[i].writeToNBT(tankTag);
				tanksList.appendTag(tankTag);
			}
		}
		tag.setTag("tanks", tanksList);

		//Energy
		tag.setInteger("perTick", RFCost);
		storage.writeToNBT(tag);
		
		//Progress
		tag.setByte("progress", progress);
		tag.setInteger("ticksSinceLast", ticksSinceLast);
		
		super.writeToNBT(tag);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		NBTTagList list = (NBTTagList) tag.getTag("inputSlot");

		if (list != null)
		{
			NBTTagCompound stackTag = list.getCompoundTagAt(0);

			inputSlot = ItemStack.loadItemStackFromNBT(stackTag);
		}
		
		//Fluid Tanks
		NBTTagList tanksList = (NBTTagList) tag.getTag("tanks");
		if (tanksList != null)
		{
			for (int i = 0; i < tanks.length; i++)
			{
				NBTTagCompound tankTag = tanksList.getCompoundTagAt(i);
				if (tankTag != null)
					tanks[i].readFromNBT(tankTag);
			}
		}
		
		//Energy
		RFCost = tag.getInteger("perTick");
		storage.readFromNBT(tag);
		
		//Progress
		progress = tag.getByte("progress");
		ticksSinceLast = tag.getInteger("ticksSinceLast");
		
		super.readFromNBT(tag);
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
	
	public void setTankInfo(int tankNum, FluidStack stack)
	{
		this.tanks[tankNum].setFluid(stack);
	}

	@Override
	public int getSizeInventory() 
	{
		return 1;
	}

	@Override
	public ItemStack getStackInSlot(int slot) 
	{
		if (slot==0)
			return inputSlot;

		return null;
	}

	@Override
	public ItemStack decrStackSize(int slot, int quantity)
	{
		if (slot==0)
		{
			ItemStack ret = inputSlot.copy();
			if (quantity > inputSlot.stackSize)
				ret.stackSize = inputSlot.stackSize;
			else
				ret.stackSize = quantity;
			inputSlot.stackSize = Math.max(inputSlot.stackSize-quantity, 0);
			if (inputSlot.stackSize == 0)
				inputSlot = null;
			sendInputUpdatePacket();
			return ret;
		}
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot)
	{
		return slot == 0 ? inputSlot : null;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) 
	{
		if (slot == 0)
		{
			if (!ItemStack.areItemStacksEqual(inputSlot, stack))
			{
				this.inputSlot = stack;
				sendInputUpdatePacket();
			}
		}
	}

	@Override
	public String getInventoryName() 
	{
		return "MatterMelter";
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
		return slot==0 && StackUtil.canStacksMerge(this.inputSlot, stack);
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) 
	{
		return new int[]{0};
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack,	int side) 
	{
		if (Config.melterAllowNormalItemsToBeMelted)
			return slot == 0 && FundamentalList.hasFundamentals(stack) && StackUtil.canStacksMerge(this.inputSlot, stack);
		else
			return slot == 0 && StackUtil.canStacksMerge(this.inputSlot, stack) && StackUtil.isItemCrystal(stack);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side) 
	{
		return false;
	}


	private void sendInputUpdatePacket()
	{
		PacketHandler.INSTANCE.sendToDimension(new MessageMatterMelterInputUpdate(xCoord, yCoord, zCoord, this.inputSlot), worldObj.provider.dimensionId);
	}
	
	private void sendFluidUpdatePacket(int tankNum)
	{
		PacketHandler.INSTANCE.sendToDimension(new MessageMatterMelterFluidUpdate(xCoord, yCoord, zCoord, tanks[tankNum].getFluid(), tankNum), worldObj.provider.dimensionId);
	}

	
	
	/*
	 *  FLUID TANKS
	 *  
	 */
	
	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) 
	{
		//One cannot fill these internal tanks.
		return 0;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) 
	{
		for (int i = 0 ; i < tanks.length ; i++)
		{
			if (tanks[i].getFluid().isFluidEqual(resource))
			{
				int amount = Math.min(tanks[i].getFluidAmount(), resource.amount);
				FluidStack ret = new FluidStack(resource.getFluid(), amount);
				
				if (doDrain)
				{
					if (tanks[i].getFluidAmount() - amount <= 0)
						tanks[i].setFluid(null);
					else
						tanks[i].setFluid(new FluidStack(tanks[i].getFluid(), tanks[i].getFluidAmount() - amount));
					
					sendFluidUpdatePacket(i);
				}
				return ret;
			}
		}
		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {

		for (int i = 0 ; i < tanks.length ; i++)
		{
			if (tanks[i].getFluid() != null)
			{
				int amount = Math.min(tanks[i].getFluidAmount(), maxDrain);
				FluidStack ret = new FluidStack(tanks[i].getFluid(), amount);
				if (doDrain)
				{
					if (tanks[i].getFluidAmount() - amount <= 0)
						tanks[i].setFluid(null);
					else
						tanks[i].setFluid(new FluidStack(tanks[i].getFluid(), tanks[i].getFluidAmount() - amount));
					
					sendFluidUpdatePacket(i);
				}
				return ret;
			}
		}
		return null;
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) 
	{
		return false;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) 
	{
		return true; //Maybe?
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) 
	{
		FluidTankInfo[] ret = new FluidTankInfo[tanks.length];
		for (int i = 0; i < tanks.length ; i++)
		{
			ret[i] = new FluidTankInfo(tanks[i]);
		}
		return ret;
	}
	
	/* IEnergyHandler */
	
	protected EnergyStorage storage = new EnergyStorage(100000);
	
	/* IEnergyConnection */
	@Override
	public boolean canConnectEnergy(ForgeDirection from) 
	{
		return true;
	}

	/* IEnergyReceiver */
	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) 
	{
		return storage.receiveEnergy(maxReceive, simulate);
	}

	/* IEnergyProvider */
	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) 
	{
		return storage.extractEnergy(maxExtract, simulate);
	}

	/* IEnergyReceiver and IEnergyProvider */
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
