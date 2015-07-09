package com.insane.mattercrystals.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public abstract class GuiBase extends GuiContainer {

	public ResourceLocation texture;
	
	public GuiBase(Container container) {
		super(container);
	}

	protected int getCenteredOffset(String string, int xWidth) {
		return (xWidth - fontRendererObj.getStringWidth(string)) / 2;
	}

	protected int getCenteredOffset(String string) {
		return getCenteredOffset(string, xSize);
	}

	//Taken from MFR
	@Override
	public void drawTexturedModelRectFromIcon(int x, int y, IIcon icon, int w, int h)
	{
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(x + 0, y + h, this.zLevel, icon.getMinU(), icon.getInterpolatedV(h));
		tessellator.addVertexWithUV(x + w, y + h, this.zLevel, icon.getInterpolatedU(w), icon.getInterpolatedV(h));
		tessellator.addVertexWithUV(x + w, y + 0, this.zLevel, icon.getInterpolatedU(w), icon.getMinV());
		tessellator.addVertexWithUV(x + 0, y + 0, this.zLevel, icon.getMinU(), icon.getMinV());
		tessellator.draw();
	}
	
	protected void drawTank(int xOffset, int yOffset, FluidStack stack, int level)
	{
		if (stack == null) return;
		Fluid fluid = stack.getFluid();
		if (fluid == null) return;
		
		IIcon icon = fluid.getIcon(stack);
		if (icon == null)
			icon = Blocks.flowing_lava.getIcon(0, 0);
		
		int vertOffset = 0;
		
		bindTexture(fluid);
		
		while (level > 0)
		{
			int texHeight = 0;
			
			if (level > 16)
			{
				texHeight = 16;
				level -= 16;
			}
			else
			{
				texHeight = level;
				level = 0;
			}
			
			drawTexturedModelRectFromIcon(xOffset, yOffset - texHeight - vertOffset, icon, 16, texHeight);
			vertOffset = vertOffset + 16;
		}
		
		bindTexture(texture);
		this.drawTexturedModalRect(xOffset, yOffset - 60, 176, 28, 16, 60);
	}
	
	protected void bindTexture(ResourceLocation tex)
	{
		this.mc.renderEngine.bindTexture(tex);
	}

	protected void bindTexture(Fluid fluid)
	{
		if (fluid.getSpriteNumber() == 0)
			this.mc.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
		else
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, fluid.getSpriteNumber());
	}

}
