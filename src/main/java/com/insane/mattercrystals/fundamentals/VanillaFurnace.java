package com.insane.mattercrystals.fundamentals;

import java.util.ArrayList;
import java.util.Map;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

public class VanillaFurnace implements IFundamentalRecipeProvider {

	public static VanillaFurnace INSTANCE = new VanillaFurnace();
	
	@SuppressWarnings("rawtypes")
	@Override
	public ArrayList<BasicRecipe> getRecipes() 
	{
		ArrayList<BasicRecipe> list = new ArrayList<BasicRecipe>();
		Map furnaceRecipes = FurnaceRecipes.smelting().getSmeltingList();

			for (Object obj : furnaceRecipes.keySet())
			{
				ItemStack input = null;
				if (obj instanceof ItemStack)
					input = (ItemStack) obj;

				if (input != null && furnaceRecipes.get(input) != null)
				{
					ItemStack output = (ItemStack) furnaceRecipes.get(input);
					list.add(new BasicRecipe(output, new ItemStack[]{input}));
					list.add(new BasicRecipe(input, new ItemStack[]{output}));
				}
			}
		
		return list;
	}

}
