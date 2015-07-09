package com.insane.mattercrystals.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import com.insane.mattercrystals.tileentity.TileCapsuleCreator;
import com.insane.mattercrystals.tileentity.TileMatterMelter;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessageCapsuleCreatorInputUpdate implements IMessage {

	public MessageCapsuleCreatorInputUpdate() {}

	public int x, y, z;
	public int slot;
	public ItemStack stack;

	public MessageCapsuleCreatorInputUpdate(int x, int y, int z, int slot, ItemStack stack)
	{
		this.x=x;
		this.y=y;
		this.z=z;
		this.slot = slot;
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
		buf.writeInt(slot);
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
		this.slot = buf.readInt();
	}
	
	public static final class Handler implements IMessageHandler<MessageCapsuleCreatorInputUpdate, IMessage>
	{
		@Override
		public IMessage onMessage(MessageCapsuleCreatorInputUpdate message, MessageContext ctx)
		{
			TileEntity te = Minecraft.getMinecraft().theWorld.getTileEntity(message.x, message.y, message.z);
			if (te != null && te instanceof TileCapsuleCreator)
				((TileCapsuleCreator) te).setInventorySlotContents(message.slot, message.stack);
			
			return null;
		}
	}

}
