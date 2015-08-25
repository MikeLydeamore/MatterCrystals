package com.insane.mattercrystals.handlers;

import com.insane.mattercrystals.fundamentals.Fundamental.Type;
import com.insane.mattercrystals.fundamentals.FundamentalData;
import com.insane.mattercrystals.fundamentals.FundamentalList;
import com.insane.mattercrystals.items.MCItems;

import net.minecraft.util.StatCollector;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class TooltipHandler {

	@SubscribeEvent
	public void addTooltips(ItemTooltipEvent event)
	{
		FundamentalData f = FundamentalList.getFundamentalsFromStack(event.itemStack);
		if (f != null)
		{
			event.toolTip.add(StatCollector.translateToLocal("string.cost"));
			for (Type t : Type.values())
			{
				if (f.getValue(t) != 0)
					event.toolTip.add("  "+t.getLocalizedName()+": "+f.getValue(t));
			}
		}
	}

}
