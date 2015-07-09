package com.insane.mattercrystals.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.insane.mattercrystals.MatterCrystals;
import com.insane.mattercrystals.gui.container.ContainerMatterMelter;
import com.insane.mattercrystals.tileentity.TileMatterMelter;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.FluidTankInfo;

public class GUIMatterMelter extends GuiBase {
	
	public TileMatterMelter te;

	public GUIMatterMelter(InventoryPlayer invPlayer, TileMatterMelter entity)
	{
		super(new ContainerMatterMelter(invPlayer, entity));

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
		
		//Tanks
		FluidTankInfo[] info = te.getTankInfo(null);
		int counter = 0;
		for (FluidTankInfo tankInfo : info)
		{
			if (tankInfo.fluid == null)
			{
				this.drawTexturedModalRect(k + 98 + 18 * counter, l + 88 - 60, 176, 28, 16, 60);
				counter++;
				continue;
			}
			
			IIcon fluidIcon = tankInfo.fluid.getFluid().getIcon(tankInfo.fluid);
			if (fluidIcon == null)
				fluidIcon = Blocks.flowing_lava.getIcon(0, 0);
			
			if (tankInfo.fluid.amount > 0)
			{
				int percentage = tankInfo.fluid.amount * 60 / tankInfo.capacity;
				
				this.drawTank(k + 98 + 18 * counter, l + 88, tankInfo.fluid, percentage);
			}
			
			counter++;
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		String title = StatCollector.translateToLocal("tile.blockMatterMelter.name");
		fontRendererObj.drawString(title, getCenteredOffset(title), 6, 0x404040);
		int x = (this.width - this.xSize) / 2 + 6;
		int y = (this.height - this.ySize) / 2;

		if (mouseX > x && mouseX < x + 15 && mouseY < y + 90 && mouseY > y + 28) {
			List<String> lines = new ArrayList<String>();
			lines.add(this.te.getEnergyStored(null) + "/"+this.te.getMaxEnergyStored(null)+" RF");
			lines.add("Consuming " + this.te.getRFCost() + " RF/t");
			this.func_146283_a(lines, mouseX - x + 10, mouseY - y + 10);
		}

		x += 72;
		FluidTankInfo[] tanks = this.te.getTankInfo(null);
		for (int i = 0 ; i < 4 ; i++)
		{
			x += 19;
			if (mouseX > x && mouseX < x + 17 && mouseY < y + 90 && mouseY > y + 28)
			{
				List<String> lines = new ArrayList<String>();
				if (tanks[i].fluid != null)
				{
					lines.add(StatCollector.translateToLocal(tanks[i].fluid.getUnlocalizedName()));
					lines.add(Integer.toString(tanks[i].fluid.amount));
				}
				else
					lines.add(StatCollector.translateToLocal("fluid.empty"));

				this.func_146283_a(lines, mouseX - 125, mouseY - y + 10);
				break;
			}
		}


	}

	protected int getCenteredOffset(String string, int xWidth) {
		return (xWidth - fontRendererObj.getStringWidth(string)) / 2;
	}

}
