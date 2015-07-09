package com.insane.mattercrystals.gui;

import com.insane.mattercrystals.gui.container.ContainerMatterMelter;
import com.insane.mattercrystals.tileentity.TileMatterMelter;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	public static final int IDMatterMelter = 0;
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == IDMatterMelter)
		{
			
			TileMatterMelter te = (TileMatterMelter) world.getTileEntity(x, y, z);
			
			return new ContainerMatterMelter(player.inventory, te);
		}
		
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		
		if (ID == IDMatterMelter)
		{
			TileMatterMelter te = (TileMatterMelter) world.getTileEntity(x, y, z);
			
			return new GUIMatterMelter(player.inventory, te);
		}
		
		return null;
	}

}
