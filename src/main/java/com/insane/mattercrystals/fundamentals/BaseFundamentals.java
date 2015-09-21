package com.insane.mattercrystals.fundamentals;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import com.insane.mattercrystals.fundamentals.Fundamental.Type;

public class BaseFundamentals {
	
	public static void init()
	{
		FundamentalList.addFundamentalsToStack("cobblestone", new FundamentalData().map(Type.STONE, 1));
		FundamentalList.addFundamentalsToStack("dustRedstone", new FundamentalData().map(Type.STONE, 4).map(Type.FIRE, 16));
		String[] dyes = {"Black", "Red", "Green", "Brown", "Blue", "Purple", "Cyan", "LightGray", "Gray", "Pink", "Lime", "Yellow", "LightBlue", "Magenta", "Orange", "White"};
		for (String s : dyes)
		{
			FundamentalList.addFundamentalsToStack("dye"+s, new FundamentalData().map(Type.EARTH, 4));
		}
		
		FundamentalList.addFundamentalsToStack("gemDiamond", new FundamentalData().map(Type.STONE, 64).map(Type.AIR, 16).map(Type.EARTH, 64));
		FundamentalList.addFundamentalsToStack("gemEmerald", new FundamentalData().map(Type.STONE, 64).map(Type.AIR, 24).map(Type.EARTH, 64));
	
		FundamentalList.addFundamentalsToStack("gemLapis", new FundamentalData().map(Type.STONE,  32).map(Type.WATER,  16));
		FundamentalList.addFundamentalsToStack("gemQuartz", new FundamentalData().map(Type.STONE, 32).map(Type.FIRE, 16));
		
		FundamentalList.addFundamentalsToStack("ingotGold", new FundamentalData().map(Type.STONE, 32).map(Type.AIR, 24));
		FundamentalList.addFundamentalsToStack("ingotIron", new FundamentalData().map(Type.STONE, 32).map(Type.AIR,  16));
		
		FundamentalList.addFundamentalsToStack("logWood", new FundamentalData().map(Type.EARTH, 16));
		FundamentalList.addFundamentalsToStack("oreCoal", new FundamentalData().map(Type.STONE, 32).map(Type.FIRE, 8));
		
		FundamentalList.addFundamentalsToStack("record", new FundamentalData().map(Type.STONE, 64).map(Type.AIR, 64).map(Type.WATER, 64));
		
		FundamentalList.addFundamentalsToStack("sand", new FundamentalData().map(Type.EARTH, 4));
		
		FundamentalList.addFundamentalsToStack("treeSapling", new FundamentalData().map(Type.EARTH, 16));
		FundamentalList.addFundamentalsToStack("treeLeaves", new FundamentalData().map(Type.EARTH, 4));
	
		FundamentalList.addFundamentalsToStack(Blocks.grass, new FundamentalData().map(Type.EARTH, 4));
		FundamentalList.addFundamentalsToStack(new ItemStack(Blocks.dirt, 1, OreDictionary.WILDCARD_VALUE), new FundamentalData().map(Type.EARTH,  4));
		FundamentalList.addFundamentalsToStack(Blocks.gravel, new FundamentalData().map(Type.STONE, 1).map(Type.EARTH, 1));
		FundamentalList.addFundamentalsToStack(Blocks.mossy_cobblestone, new FundamentalData().map(Type.STONE, 1));
		FundamentalList.addFundamentalsToStack(Blocks.obsidian, new FundamentalData().map(Type.STONE, 4).map(Type.FIRE, 1).map(Type.WATER, 1));
		FundamentalList.addFundamentalsToStack(Blocks.ice, new FundamentalData().map(Type.WATER, 4));
		FundamentalList.addFundamentalsToStack(Blocks.pumpkin, new FundamentalData().map(Type.EARTH, 4).map(Type.AIR,  1));
		FundamentalList.addFundamentalsToStack(Blocks.netherrack, new FundamentalData().map(Type.STONE, 1).map(Type.FIRE, 1));
		FundamentalList.addFundamentalsToStack(Blocks.soul_sand, new FundamentalData().map(Type.EARTH,  4).map(Type.FIRE, 4));
		FundamentalList.addFundamentalsToStack(new ItemStack(Blocks.stonebrick, 1, OreDictionary.WILDCARD_VALUE), new FundamentalData().map(Type.STONE, 1));
		FundamentalList.addFundamentalsToStack(Blocks.mycelium, new FundamentalData().map(Type.EARTH,  4));
		FundamentalList.addFundamentalsToStack(Blocks.end_stone, new FundamentalData().map(Type.STONE, 2).map(Type.AIR, 2));
		
		FundamentalList.addFundamentalsToStack(Blocks.web, new FundamentalData().map(Type.EARTH, 4).map(Type.AIR, 4));
		FundamentalList.addFundamentalsToStack(new ItemStack(Blocks.tallgrass, 1, OreDictionary.WILDCARD_VALUE), new FundamentalData().map(Type.EARTH, 2));
		FundamentalList.addFundamentalsToStack(Blocks.deadbush, new FundamentalData().map(Type.EARTH, 1));
		FundamentalList.addFundamentalsToStack(Blocks.yellow_flower, new FundamentalData().map(Type.EARTH, 4));
		FundamentalList.addFundamentalsToStack(new ItemStack(Blocks.red_flower, 1, OreDictionary.WILDCARD_VALUE), new FundamentalData().map(Type.EARTH, 4));
		FundamentalList.addFundamentalsToStack(Blocks.brown_mushroom, new FundamentalData().map(Type.EARTH, 4));
		FundamentalList.addFundamentalsToStack(Blocks.red_mushroom, new FundamentalData().map(Type.EARTH, 4));
		FundamentalList.addFundamentalsToStack(Blocks.snow_layer, new FundamentalData().map(Type.EARTH, 1));
		FundamentalList.addFundamentalsToStack(Blocks.cactus, new FundamentalData().map(Type.EARTH, 6));
		FundamentalList.addFundamentalsToStack(Blocks.vine, new FundamentalData().map(Type.EARTH, 6));
		FundamentalList.addFundamentalsToStack(Blocks.cactus, new FundamentalData().map(Type.EARTH, 6));
		FundamentalList.addFundamentalsToStack(new ItemStack(Blocks.double_plant, 1, OreDictionary.WILDCARD_VALUE), new FundamentalData().map(Type.EARTH, 8));
		
		FundamentalList.addFundamentalsToStack(Items.saddle, new FundamentalData().map(Type.EARTH, 16).map(Type.AIR, 16));
		FundamentalList.addFundamentalsToStack(Items.snowball, new FundamentalData().map(Type.EARTH, 1).map(Type.WATER, 1));
		FundamentalList.addFundamentalsToStack(Items.slime_ball, new FundamentalData().map(Type.EARTH, 6).map(Type.AIR, 6));
		FundamentalList.addFundamentalsToStack(Items.bone, new FundamentalData().map(Type.EARTH, 6).map(Type.STONE, 4));
		FundamentalList.addFundamentalsToStack(Items.ender_pearl, new FundamentalData().map(Type.AIR, 24).map(Type.FIRE, 24));
		
		FundamentalList.addFundamentalsToStack(Items.apple, new FundamentalData().map(Type.EARTH, 2).map(Type.WATER, 2));
		FundamentalList.addFundamentalsToStack(Items.porkchop, new FundamentalData().map(Type.EARTH, 2).map(Type.WATER, 2).map(Type.FIRE, 1));
		FundamentalList.addFundamentalsToStack(new ItemStack(Items.fish, 1, OreDictionary.WILDCARD_VALUE), new FundamentalData().map(Type.EARTH, 2).map(Type.WATER, 4));
		FundamentalList.addFundamentalsToStack(Items.melon, new FundamentalData().map(Type.EARTH, 2));
		FundamentalList.addFundamentalsToStack(Items.beef, new FundamentalData().map(Type.EARTH, 2).map(Type.FIRE, 1).map(Type.WATER, 2));
		FundamentalList.addFundamentalsToStack(Items.chicken, new FundamentalData().map(Type.EARTH, 2).map(Type.FIRE, 1).map(Type.WATER, 2));
		FundamentalList.addFundamentalsToStack(Items.rotten_flesh, new FundamentalData().map(Type.EARTH, 5).map(Type.FIRE, 2));
		FundamentalList.addFundamentalsToStack(Items.spider_eye, new FundamentalData().map(Type.EARTH, 5).map(Type.AIR, 2));
		FundamentalList.addFundamentalsToStack(Items.carrot, new FundamentalData().map(Type.EARTH, 2));
		FundamentalList.addFundamentalsToStack(Items.potato, new FundamentalData().map(Type.EARTH, 3));
		FundamentalList.addFundamentalsToStack(Items.poisonous_potato, new FundamentalData().map(Type.EARTH, 2).map(Type.AIR, 1));
		
		FundamentalList.addFundamentalsToStack(Items.ghast_tear, new FundamentalData().map(Type.FIRE, 16).map(Type.AIR, 16));
		
		FundamentalList.addFundamentalsToStack(Items.string, new FundamentalData().map(Type.AIR, 2));
		FundamentalList.addFundamentalsToStack(Items.feather, new FundamentalData().map(Type.AIR, 4));
		FundamentalList.addFundamentalsToStack(Items.gunpowder, new FundamentalData().map(Type.FIRE, 2).map(Type.AIR, 2));
		FundamentalList.addFundamentalsToStack(Items.wheat_seeds, new FundamentalData().map(Type.AIR, 1).map(Type.EARTH, 1));
		FundamentalList.addFundamentalsToStack(Items.wheat, new FundamentalData().map(Type.AIR, 1).map(Type.EARTH, 1));
		FundamentalList.addFundamentalsToStack(Items.flint, new FundamentalData().map(Type.STONE, 1).map(Type.FIRE, 1));
		FundamentalList.addFundamentalsToStack(Items.leather, new FundamentalData().map(Type.AIR, 4).map(Type.EARTH, 1));
		FundamentalList.addFundamentalsToStack(Items.clay_ball, new FundamentalData().map(Type.EARTH, 1).map(Type.WATER, 2));
		FundamentalList.addFundamentalsToStack(Items.reeds, new FundamentalData().map(Type.EARTH, 3).map(Type.WATER, 2));
		FundamentalList.addFundamentalsToStack(Items.egg, new FundamentalData().map(Type.AIR, 2));
		FundamentalList.addFundamentalsToStack(Items.blaze_rod, new FundamentalData().map(Type.FIRE, 6).map(Type.AIR, 4));
		FundamentalList.addFundamentalsToStack(Items.nether_wart, new FundamentalData().map(Type.EARTH, 2).map(Type.FIRE, 1));
		FundamentalList.addFundamentalsToStack(Items.nether_star, new FundamentalData().map(Type.AIR, 32).map(Type.EARTH, 32).map(Type.FIRE, 32).map(Type.STONE, 32).map(Type.WATER, 32));
	}

}
