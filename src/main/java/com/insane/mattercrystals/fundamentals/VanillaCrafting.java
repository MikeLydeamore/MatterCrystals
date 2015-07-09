package com.insane.mattercrystals.fundamentals;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class VanillaCrafting implements IFundamentalRecipeProvider {

	public static VanillaCrafting INSTANCE = new VanillaCrafting();
	
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<BasicRecipe> getRecipes() 
	{
		ArrayList<BasicRecipe> list = new ArrayList<BasicRecipe>();

		List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();
		Iterator<IRecipe> it = recipes.iterator();

		while (it.hasNext())
		{
			IRecipe rec = it.next();

			if (rec instanceof ShapedRecipes)
			{
				ItemStack output = rec.getRecipeOutput();
				ItemStack[] ingredients = ((ShapedRecipes) rec).recipeItems;
				list.add(new BasicRecipe(output, ingredients));
			}
			else if (rec instanceof ShapelessRecipes)
			{
				ItemStack output = rec.getRecipeOutput();
				ItemStack[] ingredients= {};
				for (Object obj :  ((ShapelessRecipes) rec).recipeItems.toArray())
				{
					if (obj == null)
						continue;
					else if (obj instanceof ItemStack)
						ingredients = ArrayUtils.add(ingredients, (ItemStack) obj);
				}
				list.add(new BasicRecipe(output, ingredients));

			}
			else if (rec instanceof ShapedOreRecipe)
			{
				ItemStack output = rec.getRecipeOutput();
				ItemStack[] ingredients = {};
				for (Object obj : ((ShapedOreRecipe) rec).getInput())
				{
					if (obj == null)
						continue;
					else if (obj instanceof ItemStack)
					{
						ingredients = ArrayUtils.add(ingredients, (ItemStack)obj);
					}
					else if (obj instanceof ArrayList)
					{
						for (ItemStack s : (ArrayList<ItemStack>) obj)
						{
							if (s==null)
								continue;
							else
								ingredients = ArrayUtils.add(ingredients, s.copy());
						}
					}

				}
				list.add(new BasicRecipe(output, ingredients));
			}

			else if (rec instanceof ShapelessOreRecipe)
			{
				ItemStack output = rec.getRecipeOutput();
				ItemStack[] ingredients = {};
				for (Object obj : ((ShapelessOreRecipe) rec).getInput())
				{
					if (obj == null)
						continue;
					else if (obj instanceof ItemStack)
					{
						ingredients = ArrayUtils.add(ingredients, (ItemStack)obj);
					}
					else if (obj instanceof ArrayList)
					{
						for (ItemStack s : (ArrayList<ItemStack>) obj)
						{
							if (s==null)
								continue;
							else
								ingredients = ArrayUtils.add(ingredients, s.copy());
						}
					}

				}
				list.add(new BasicRecipe(output, ingredients));
			}
		}
		
		return list;
	}
}
