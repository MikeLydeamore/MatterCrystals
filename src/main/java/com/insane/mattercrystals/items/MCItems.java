package com.insane.mattercrystals.items;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

public class MCItems {
	
	public static Item itemCapsule;
	
	public static void registerItems()
	{
		itemCapsule = new ItemCapsule();
		GameRegistry.registerItem(itemCapsule, "capsule");
		
	}

}
