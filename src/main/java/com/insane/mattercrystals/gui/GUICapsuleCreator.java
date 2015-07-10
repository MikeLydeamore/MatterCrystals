package com.insane.mattercrystals.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.insane.mattercrystals.MatterCrystals;
import com.insane.mattercrystals.gui.container.ContainerCapsuleCreator;
import com.insane.mattercrystals.tileentity.TileCapsuleCreator;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class GUICapsuleCreator extends GuiBase {

	public TileCapsuleCreator te;

	public GUICapsuleCreator(InventoryPlayer invPlayer, TileCapsuleCreator entity)
	{
		super(new ContainerCapsuleCreator(invPlayer, entity));

		te = entity;

		xSize = 175;
		ySize = 175;
		this.texture = new ResourceLocation(MatterCrystals.MODID.toLowerCase(), "textures/gui/guimattermelter.png");

	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(texture);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);

		//Power bar
		int powerheight = 61;
		int powerbase = 89;
		if (te.getEnergyStored(null) > 0)
		{
			int height = (int) ((float) te.getEnergyStored(null) / te.getMaxEnergyStored(null) * powerheight);
			this.drawTexturedModalRect(k+7, l+27+(powerheight-height), 194, 27, 13, height);
		}

		//Progress bar
		int width = (int) ((float) te.getProgress() / 100 * 34);
		this.drawTexturedModalRect(k+61, l+50, 176, 0, width, 17);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		String title = StatCollector.translateToLocal("tile.blockCapsuleCreator.name");
		fontRendererObj.drawString(title, getCenteredOffset(title), 6, 0x404040);
		int x = (this.width - this.xSize) / 2 + 6;
		int y = (this.height - this.ySize) / 2;

		if (mouseX > x && mouseX < x + 15 && mouseY < y + 90 && mouseY > y + 28) {
			List<String> lines = new ArrayList<String>();
			lines.add(this.te.getEnergyStored(null) + "/"+this.te.getMaxEnergyStored(null)+" RF");
			lines.add("Consuming " + this.te.getRFCost() + " RF/t");
			this.func_146283_a(lines, mouseX - x + 10, mouseY - y + 10);
		}

	}

	protected int getCenteredOffset(String string, int xWidth) 
	{
		return (xWidth - fontRendererObj.getStringWidth(string)) / 2;
	}
}
