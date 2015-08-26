package com.insane.mattercrystals.gui;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

import com.insane.mattercrystals.MatterCrystals;
import com.insane.mattercrystals.gui.container.ContainerAtomicAssembler;
import com.insane.mattercrystals.tileentity.TileAtomicAssembler;

public class GUIAtomicAssembler extends GuiBase {
	
	public TileAtomicAssembler te;

	public GUIAtomicAssembler(InventoryPlayer invPlayer, TileAtomicAssembler entity)
	{
		super(new ContainerAtomicAssembler(invPlayer, entity));

		te = entity;

		xSize = 175;
		ySize = 175;
		this.texture = new ResourceLocation(MatterCrystals.MODID.toLowerCase(), "textures/gui/guiatomicassembler.png");
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(texture);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);

	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		String title = StatCollector.translateToLocal("tile.blockAtomicAssembler.name");
		fontRendererObj.drawString(title, getCenteredOffset(title), 6, 0x404040);
		int x = (this.width - this.xSize) / 2 + 6;
		int y = (this.height - this.ySize) / 2;

	


	}

	protected int getCenteredOffset(String string, int xWidth) 
	{
		return (xWidth - fontRendererObj.getStringWidth(string)) / 2;
	}
}