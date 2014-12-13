package com.insane.mattercrystals.blocks;

import com.insane.mattercrystals.MatterCrystals;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class MCBlockBase extends Block {
	
	public MCBlockBase(Material mat)
	{
		super(mat);
		this.setCreativeTab(MatterCrystals.tabMC);
	}

}
