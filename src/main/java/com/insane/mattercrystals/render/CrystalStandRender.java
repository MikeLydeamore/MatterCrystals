package com.insane.mattercrystals.render;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class CrystalStandRender implements ISimpleBlockRenderingHandler {

	public static int model = RenderingRegistry.getNextAvailableRenderId();

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId,
			RenderBlocks renderer) 
	{
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) 
	{
		if (modelId == model)
		{	

			int meta = world.getBlockMetadata(x, y, z);
			renderer.setRenderBounds(0, 0, 0.45, 0.1, 0.35, 0.55);
			renderer.renderStandardBlock(block, x, y, z);


			if (meta>=1)
			{
				renderer.setRenderBounds(0.9, 0, 0.45, 1.0, 0.35, 0.55);
				renderer.renderStandardBlock(block,x,y,z);
			}

			if (meta>=2)
			{
				IIcon c = Blocks.wool.getIcon(0,0);
				renderer.setOverrideBlockTexture(c);
				
				renderer.setRenderBounds(0.1, 0.22, 0.48, 0.9, 0.24, 0.51);
				renderer.renderStandardBlock(block,x,y,z);
				
				renderer.clearOverrideBlockTexture();

			}
		}
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) 
	{
		return true;
	}

	@Override
	public int getRenderId() 
	{
		return model;
	}

}
