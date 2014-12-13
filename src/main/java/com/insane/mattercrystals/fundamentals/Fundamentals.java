package com.insane.mattercrystals.fundamentals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class Fundamentals {


	public static void getCostsFromRecipes(int runs)
	{
		for (int counter = 0; counter < runs; counter++)
		{
			@SuppressWarnings("unchecked")
			List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();
			Iterator<IRecipe> it = recipes.iterator();

			while (it.hasNext())
			{
				IRecipe rec = it.next();

				if (rec instanceof ShapedRecipes)
				{
					ItemStack output = rec.getRecipeOutput();
					ItemStack[] ingredients = ((ShapedRecipes) rec).recipeItems;
					addCostFromRecipe(output, ingredients);
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

					addCostFromRecipe(output, ingredients);
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
					addCostFromRecipe(output, ingredients);
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
					addCostFromRecipe(output, ingredients);
				}
			}
		}

	}

	public static void addCostFromRecipe(ItemStack output, ItemStack[] ingredients)
	{
		int earth=0, fire=0, water=0, stone=0, air = 0;
		int itemCounter=0, fundCounter=0;
		int stackSize = output.stackSize;

		for (int i=0; i<ingredients.length; i++)
		{
			Fundamental f = FundamentalList.getFundamentalsFromStack(ingredients[i]);
			if (ingredients[i]!=null)
				itemCounter++;

			if (f != null)
			{
				earth += f.EARTH;
				fire += f.FIRE;
				water += f.WATER;
				stone += f.STONE;
				air += f.AIR;
				fundCounter++;
			}
		}

		if (fundCounter == itemCounter && fundCounter != 0 && (earth+fire+water+stone+air)/stackSize != 0) // No 0 counters here!
		{
			FundamentalList.addFundamentalsToStack(output, new Fundamental(earth/stackSize,fire/stackSize,water/stackSize,stone/stackSize,air/stackSize));
		}
	}

}
