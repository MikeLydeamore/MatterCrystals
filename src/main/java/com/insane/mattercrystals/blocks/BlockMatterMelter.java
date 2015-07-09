package com.insane.mattercrystals.blocks;

import com.insane.mattercrystals.MatterCrystals;
import com.insane.mattercrystals.gui.GuiHandler;
import com.insane.mattercrystals.tileentity.TileMatterMelter;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockMatterMelter extends MCBlockBase implements ITileEntityProvider {
	
	public BlockMatterMelter()
	{
		super(Material.rock);
		this.setBlockName("blockMatterMelter");
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) 
	{
		return new TileMatterMelter();
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float subX, float subY, float subZ)
	{
		TileMatterMelter te = (TileMatterMelter) world.getTileEntity(x, y, z);
		
		if (!player.isSneaking())
		{
			player.openGui(MatterCrystals.instance, GuiHandler.IDMatterMelter, world, x, y, z);
			return true;
		}
		
		return false;
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta)
	{
		ISidedInventory inv = (ISidedInventory) world.getTileEntity(x, y, z);
		for (int i = 0; i < inv.getSizeInventory(); i++) 
		{
			if (inv.getStackInSlot(i) != null) 
			{
				EntityItem entityitem = new EntityItem(world, x, y, z, inv.getStackInSlot(i));
				world.spawnEntityInWorld(entityitem);
			}
		}
		super.breakBlock(world, x, y, z, block, meta);
	}
	

}
