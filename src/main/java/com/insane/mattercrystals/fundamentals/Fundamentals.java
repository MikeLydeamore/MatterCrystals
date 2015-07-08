package com.insane.mattercrystals.fundamentals;

import java.util.ArrayList;
import com.insane.mattercrystals.fundamentals.Fundamental.Type;

import net.minecraft.item.ItemStack;

public class Fundamentals {

	private static ArrayList<IFundamentalRecipeProvider> recipeProviders = new ArrayList<IFundamentalRecipeProvider>();

	public static void addRecipeProvider(IFundamentalRecipeProvider provider)
	{
		recipeProviders.add(provider);
	}

	public static void processRecipes(int runs)
	{
		for (int i = 0 ; i < runs ; i++)
		{
			for (IFundamentalRecipeProvider provider : recipeProviders)
			{
				for (BasicRecipe recipe : provider.getRecipes())
					addCostFromRecipe(recipe.getOutput(), recipe.getIngredients());
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static void addCostFromRecipe(ItemStack output, ItemStack[] ingredients)
	{
		int itemCounter=0, fundCounter=0;
		int stackSize = output.stackSize;
		FundamentalData newData = new FundamentalData();

		for (int i=0; i<ingredients.length; i++)
		{
			FundamentalData f = FundamentalList.getFundamentalsFromStack(ingredients[i]);
			if (ingredients[i]!=null)
				itemCounter++;

			if (f != null)
			{
				for (Type t : Type.values())
					newData.addToValue(t, f.getValue(t));
				fundCounter++;
			}
		}

		if (fundCounter == itemCounter && fundCounter != 0) // No 0 counters here!
		{
			newData.correctStackSize(stackSize);
			FundamentalList.addFundamentalsToStack(output, newData);
		}
	}

}
