package com.insane.mattercrystals.network;

import com.insane.mattercrystals.tileentity.TileMatterMelter;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessageMatterMelterFluidUpdate implements IMessage {
	
	public MessageMatterMelterFluidUpdate() {}
	
	public int x, y, z;
	public FluidStack stack;
	public int tankNum;
	
	public MessageMatterMelterFluidUpdate(int x, int y, int z, FluidStack stack, int tankNum)
	{
		this.x=x;
		this.y=y;
		this.z=z;
		this.stack = stack;
		this.tankNum = tankNum;
	}
	
	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		
		buf.writeInt(tankNum);
		
		if (stack != null)
		{
			buf.writeInt(stack.getFluidID());
			buf.writeInt(stack.amount);
		}
		
		else
			buf.writeInt(-1);
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.x = buf.readInt();
		this.y = buf.readInt();
		this.z = buf.readInt();
		
		this.tankNum = buf.readInt();
		
		int id = buf.readInt();
		
		if (id != -1)
		{
			Fluid fluid = FluidRegistry.getFluid(id);
			int quantity = buf.readInt();
			if (fluid != null)
				this.stack = new FluidStack(fluid, quantity);
		}
		else
			this.stack = null;
	}
	
	public static final class Handler implements IMessageHandler<MessageMatterMelterFluidUpdate, IMessage>
	{
		@Override
		public IMessage onMessage(MessageMatterMelterFluidUpdate message, MessageContext ctx)
		{
			TileEntity te = Minecraft.getMinecraft().theWorld.getTileEntity(message.x, message.y, message.z);
			if (te != null && te instanceof TileMatterMelter)
				((TileMatterMelter) te).setTankInfo(message.tankNum, message.stack);
			
			return null;
		}
	}

}
