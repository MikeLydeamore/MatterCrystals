package com.insane.mattercrystals.handlers;

import com.insane.mattercrystals.fundamentals.Fundamental;
import com.insane.mattercrystals.fundamentals.FundamentalList;

import net.minecraft.util.StatCollector;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class TooltipHandler {
	
	@SubscribeEvent
	public void addTooltips(ItemTooltipEvent event)
	{
		Fundamental f = FundamentalList.getFundamentalsFromStack(event.itemStack);
		if (f != null)
		{
			event.toolTip.add(StatCollector.translateToLocal("string.cost"));
			event.toolTip.add("  "+StatCollector.translateToLocal("string.fundamentalEarth")+": "+f.EARTH);
			event.toolTip.add("  "+StatCollector.translateToLocal("string.fundamentalFire")+": "+f.FIRE);
			event.toolTip.add("  "+StatCollector.translateToLocal("string.fundamentalWater")+": "+f.WATER);
			event.toolTip.add("  "+StatCollector.translateToLocal("string.fundamentalStone")+": "+f.STONE);
			event.toolTip.add("  "+StatCollector.translateToLocal("string.fundamentalAir")+": "+f.AIR);
		}
	}

}
