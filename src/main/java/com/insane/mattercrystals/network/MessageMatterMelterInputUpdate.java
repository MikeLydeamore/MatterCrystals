package com.insane.mattercrystals.network;

import com.insane.mattercrystals.tileentity.TileMatterMelter;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessageMatterMelterInputUpdate implements IMessage {

	public MessageMatterMelterInputUpdate() {}

	public int x, y, z;
	public ItemStack stack;

	public MessageMatterMelterInputUpdate(int x, int y, int z, ItemStack stack)
	{
		this.x=x;
		this.y=y;
		this.z=z;
		this.stack = stack;
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		ByteBufUtils.writeItemStack(buf, stack);
		if (stack != null)
			buf.writeInt(stack.stackSize);

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
		
		this.x = buf.readInt();
		this.y = buf.readInt();
		this.z = buf.readInt();
	}
	
	public static final class Handler implements IMessageHandler<MessageMatterMelterInputUpdate, IMessage>
	{
		@Override
		public IMessage onMessage(MessageMatterMelterInputUpdate message, MessageContext ctx)
		{
			TileEntity te = Minecraft.getMinecraft().theWorld.getTileEntity(message.x, message.y, message.z);
			if (te != null && te instanceof TileMatterMelter)
				((TileMatterMelter) te).setInputSlot(message.stack);
			
			return null;
		}
	}
}
