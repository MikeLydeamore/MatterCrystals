package com.insane.mattercrystals.fundamentals;

import net.minecraft.item.ItemStack;
import lombok.Getter;

public class BasicRecipe {
	
	@Getter
	private ItemStack output;
	
	@Getter
	private ItemStack[] ingredients;
	
	public BasicRecipe(ItemStack output, ItemStack[] ingredients) 
	{
		this.output = output;
		this.ingredients = ingredients;
	}

}
