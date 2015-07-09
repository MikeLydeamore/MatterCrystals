package com.insane.mattercrystals.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import com.insane.mattercrystals.tileentity.TileAtomicAssembler;
import com.insane.mattercrystals.tileentity.TileMatterMelter;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessageAtomicAssemblerItemUpdate implements IMessage {

	public MessageAtomicAssemblerItemUpdate() {}

	public int x, y, z;
	public ItemStack stack;
	public int slot;

	public MessageAtomicAssemblerItemUpdate(int x, int y, int z, ItemStack stack, int slot)
	{
		this.x=x;
		this.y=y;
		this.z=z;
		this.stack = stack;
		this.slot = slot;
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		ByteBufUtils.writeItemStack(buf, stack);
		if (stack != null)
			buf.writeInt(stack.stackSize);
		buf.writeInt(slot);

		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.stack = ByteBufUtils.readItemStack(buf);
		if (stack != null)
			buf.readInt();
		this.slot = buf.readInt();
		
		this.x = buf.readInt();
		this.y = buf.readInt();
		this.z = buf.readInt();
	}
	
	public static final class Handler implements IMessageHandler<MessageAtomicAssemblerItemUpdate, IMessage>
	{
		@Override
		public IMessage onMessage(MessageAtomicAssemblerItemUpdate message, MessageContext ctx)
		{
			TileEntity te = Minecraft.getMinecraft().theWorld.getTileEntity(message.x, message.y, message.z);
			if (te != null && te instanceof TileMatterMelter)
				((TileAtomicAssembler) te).setSlot(message.stack, message.slot);
			
			return null;
		}
	}
}
