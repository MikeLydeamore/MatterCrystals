package com.insane.mattercrystals.blocks;

import com.insane.mattercrystals.render.CrystalStandRender;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;

public class MCBlocks {
	
	public static Block crystalStand;
	//public static Block crystal;
	
	public static void registerBlocks()
	{
		crystalStand = new BlockCrystalStand();
		registerBlock(crystalStand);
		RenderingRegistry.registerBlockHandler(new CrystalStandRender());
		
		//crystal = new BlockCrystal();
		//registerBlock(crystal);
	}
	
	
	private static void registerBlock(Block block)
	{
		GameRegistry.registerBlock(block, block.getUnlocalizedName().substring(5));
	}

}
