package com.insane.mattercrystals.blocks;

import java.util.List;

import com.insane.mattercrystals.render.CrystalStandRender;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCrystalStand extends MCBlockBase {

	public BlockCrystalStand()
	{
		super(Material.wood);
		this.setBlockName("crystalStand");
		this.setBlockBounds(0f, 0f, 0.45f, 1f, 0.35f, 0.55f);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		if (player!=null && player.getCurrentEquippedItem()!= null)
		{
			int meta = world.getBlockMetadata(x,y,z);
			if (player.getCurrentEquippedItem().getItem() == this.getItem(world, x, y, z) && meta<3)
			{
				world.setBlockMetadataWithNotify(x, y, z, meta+1, 3);
				player.inventory.decrStackSize(player.inventory.currentItem, 1);
				return true;
			}
		}
		return false;
	}
	
	@SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister par1IconRegister) {
        this.blockIcon = par1IconRegister.registerIcon("mattercrystals:crystalstandstick");
    }

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
		for (int i=0; i<3; i++) {
			par3List.add(new ItemStack(par1, 1, i));
		}

	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public int getRenderType()
	{
		return CrystalStandRender.model;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered (IBlockAccess world, int x, int y, int z, int side)
	{
		return true;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

}
