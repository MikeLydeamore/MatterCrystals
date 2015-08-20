package com.insane.mattercrystals.blocks;

import com.insane.mattercrystals.MatterCrystals;
import com.insane.mattercrystals.gui.GuiHandler;
import com.insane.mattercrystals.tileentity.TileMatterMelter;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockMatterMelter extends MCBlockBase implements ITileEntityProvider {

	private IIcon[] icons = new IIcon[4];

	public BlockMatterMelter()
	{
		super(Material.rock);
		this.setBlockName("blockMatterMelter");
	}

	@Override
	public void onBlockPlacedBy(World par1World, int x, int y, int z, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack)
	{
		int whichDirectionFacing = MathHelper.floor_double((double)(par5EntityLivingBase.rotationYaw * 4.0F / 360.0F) + 2.5D) & 3;
		par1World.setBlockMetadataWithNotify(x, y, z, whichDirectionFacing, 2);
	}


	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register)
	{
		icons[0] = register.registerIcon("mattercrystals:matterMelterFront16");
		icons[1] = register.registerIcon("mattercrystals:matterMelterFrontOff16");
		icons[2] = register.registerIcon("mattercrystals:matterMelterSide16");
		icons[3] = register.registerIcon("mattercrystals:matterMelterTopBottom16");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side)
	{
		int metadata = world.getBlockMetadata(x, y, z);
		TileMatterMelter te = (TileMatterMelter) world.getTileEntity(x, y, z);
		System.out.println(te.getProgress());
		if (side == 0 || side == 1)
			return icons[3];
		
		else if (metadata == 2 && side == 2) 
			return te.getProgress() > 0 ? icons[0] : icons[1];
		else if (metadata == 3 && side == 5) 
			return te.getProgress() > 0 ? icons[0] : icons[1];
		else if (metadata == 0 && side == 3) 
			return te.getProgress() > 0 ? icons[0] : icons[1];
		else if (metadata == 1 && side == 4) 
			return te.getProgress() > 0 ? icons[0] : icons[1];
		else
			return icons[2];

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
