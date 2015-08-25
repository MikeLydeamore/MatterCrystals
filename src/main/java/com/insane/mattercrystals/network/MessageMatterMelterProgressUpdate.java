package com.insane.mattercrystals.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.insane.mattercrystals.tileentity.TileMatterMelter;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessageMatterMelterProgressUpdate implements IMessage {
	
	public MessageMatterMelterProgressUpdate() {}

	public int x, y, z;
	public byte progress;

	public MessageMatterMelterProgressUpdate(int x, int y, int z, byte progress)
	{
		this.x=x;
		this.y=y;
		this.z=z;
		this.progress = progress;
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeByte(progress);
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{		
		this.x = buf.readInt();
		this.y = buf.readInt();
		this.z = buf.readInt();
		
		this.progress = buf.readByte();
	}
	
	public static final class Handler implements IMessageHandler<MessageMatterMelterProgressUpdate, IMessage>
	{
		@Override
		public IMessage onMessage(MessageMatterMelterProgressUpdate message, MessageContext ctx)
		{
			World world = Minecraft.getMinecraft().theWorld;
			TileEntity te = Minecraft.getMinecraft().theWorld.getTileEntity(message.x, message.y, message.z);
			if (te != null && te instanceof TileMatterMelter)
			{
				((TileMatterMelter) te).setProgress(message.progress);
				world.markBlockForUpdate(message.x, message.y, message.z);
				
			}
			
			return null;
		}
	}

}
