package com.insane.mattercrystals.blocks;

import com.insane.mattercrystals.MatterCrystals;

import net.minecraft.block.material.Material;

public class BlockCrystal extends MCBlockBase {
	
	public BlockCrystal()
	{
		super(Material.glass);
		this.setBlockName("blockCrystal");
	}
	
	
	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	@Override
	public int getRenderType()
	{
		return MatterCrystals.crystalRenderID;
	}
	
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
	
	public boolean isNormalCube()
	{
		return false;
	}
	
	

}
