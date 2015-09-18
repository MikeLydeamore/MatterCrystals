package com.insane.mattercrystals.fundamentals;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import org.apache.commons.lang3.tuple.Pair;

import com.insane.mattercrystals.fundamentals.Fundamental.Type;

public class BaseFundamentals {
	
	@SuppressWarnings("unchecked")
	public static void init()
	{
		FundamentalList.addFundamentalsToStack("cobblestone", new FundamentalData(Pair.of(Type.STONE, 1)));
		FundamentalList.addFundamentalsToStack("dustRedstone", new FundamentalData(Pair.of(Type.STONE, 4), Pair.of(Type.FIRE, 16)));
		String[] dyes = {"Black", "Red", "Green", "Brown", "Blue", "Purple", "Cyan", "LightGray", "Gray", "Pink", "Lime", "Yellow", "LightBlue", "Magenta", "Orange", "White"};
		for (String s : dyes)
		{
			FundamentalList.addFundamentalsToStack("dye"+s, new FundamentalData(Pair.of(Type.EARTH, 4)));
		}
		
		FundamentalList.addFundamentalsToStack("gemDiamond", new FundamentalData(Pair.of(Type.STONE, 64), Pair.of(Type.AIR, 16), Pair.of(Type.EARTH, 64)));
		FundamentalList.addFundamentalsToStack("gemEmerald", new FundamentalData(Pair.of(Type.STONE, 64), Pair.of(Type.AIR, 24), Pair.of(Type.EARTH, 64)));
	
		FundamentalList.addFundamentalsToStack("gemLapis", new FundamentalData(Pair.of(Type.STONE,  32), Pair.of(Type.WATER,  16)));
		FundamentalList.addFundamentalsToStack("gemQuartz", new FundamentalData(Pair.of(Type.STONE, 32), Pair.of(Type.FIRE, 16)));
		
		FundamentalList.addFundamentalsToStack("ingotGold", new FundamentalData(Pair.of(Type.STONE, 32), Pair.of(Type.AIR, 24)));
		FundamentalList.addFundamentalsToStack("ingotIron", new FundamentalData(Pair.of(Type.STONE, 32), Pair.of(Type.AIR,  16)));
		
		FundamentalList.addFundamentalsToStack("logWood", new FundamentalData(Pair.of(Type.EARTH, 16)));
		FundamentalList.addFundamentalsToStack("oreCoal", new FundamentalData(Pair.of(Type.STONE, 32), Pair.of(Type.FIRE, 8)));
		
		FundamentalList.addFundamentalsToStack("record", new FundamentalData(Pair.of(Type.STONE, 64), Pair.of(Type.AIR, 64), Pair.of(Type.WATER, 64)));
		
		FundamentalList.addFundamentalsToStack("sand", new FundamentalData(Pair.of(Type.EARTH, 4)));
		
		FundamentalList.addFundamentalsToStack("treeSapling", new FundamentalData(Pair.of(Type.EARTH, 16)));
		FundamentalList.addFundamentalsToStack("treeLeaves", new FundamentalData(Pair.of(Type.EARTH, 4)));
	
		FundamentalList.addFundamentalsToStack(Blocks.grass, new FundamentalData(Pair.of(Type.EARTH, 4)));
		FundamentalList.addFundamentalsToStack(new ItemStack(Blocks.dirt, 1, OreDictionary.WILDCARD_VALUE), new FundamentalData(Pair.of(Type.EARTH,  4)));
		FundamentalList.addFundamentalsToStack(Blocks.gravel, new FundamentalData(Pair.of(Type.STONE, 1), Pair.of(Type.EARTH, 1)));
		FundamentalList.addFundamentalsToStack(Blocks.mossy_cobblestone, new FundamentalData(Pair.of(Type.STONE, 1)));
		FundamentalList.addFundamentalsToStack(Blocks.obsidian, new FundamentalData(Pair.of(Type.STONE, 4), Pair.of(Type.FIRE, 1), Pair.of(Type.WATER, 1)));
		FundamentalList.addFundamentalsToStack(Blocks.ice, new FundamentalData(Pair.of(Type.WATER, 4)));
		FundamentalList.addFundamentalsToStack(Blocks.pumpkin, new FundamentalData(Pair.of(Type.EARTH, 4), Pair.of(Type.AIR,  1)));
		FundamentalList.addFundamentalsToStack(Blocks.netherrack, new FundamentalData(Pair.of(Type.STONE, 1), Pair.of(Type.FIRE, 1)));
		FundamentalList.addFundamentalsToStack(Blocks.soul_sand, new FundamentalData(Pair.of(Type.EARTH,  4), Pair.of(Type.FIRE, 4)));
		FundamentalList.addFundamentalsToStack(new ItemStack(Blocks.stonebrick, 1, OreDictionary.WILDCARD_VALUE), new FundamentalData(Pair.of(Type.STONE, 1)));
		FundamentalList.addFundamentalsToStack(Blocks.mycelium, new FundamentalData(Pair.of(Type.EARTH,  4)));
		FundamentalList.addFundamentalsToStack(Blocks.end_stone, new FundamentalData(Pair.of(Type.STONE, 2), Pair.of(Type.AIR, 2)));
		
		FundamentalList.addFundamentalsToStack(Blocks.web, new FundamentalData(Pair.of(Type.EARTH, 4), Pair.of(Type.AIR, 4)));
		FundamentalList.addFundamentalsToStack(new ItemStack(Blocks.tallgrass, 1, OreDictionary.WILDCARD_VALUE), new FundamentalData(Pair.of(Type.EARTH, 2)));
		FundamentalList.addFundamentalsToStack(Blocks.deadbush, new FundamentalData(Pair.of(Type.EARTH, 1)));
		FundamentalList.addFundamentalsToStack(Blocks.yellow_flower, new FundamentalData(Pair.of(Type.EARTH, 4)));
		FundamentalList.addFundamentalsToStack(new ItemStack(Blocks.red_flower, 1, OreDictionary.WILDCARD_VALUE), new FundamentalData(Pair.of(Type.EARTH, 4)));
		FundamentalList.addFundamentalsToStack(Blocks.brown_mushroom, new FundamentalData(Pair.of(Type.EARTH, 4)));
		FundamentalList.addFundamentalsToStack(Blocks.red_mushroom, new FundamentalData(Pair.of(Type.EARTH, 4)));
		FundamentalList.addFundamentalsToStack(Blocks.snow_layer, new FundamentalData(Pair.of(Type.EARTH, 1)));
		FundamentalList.addFundamentalsToStack(Blocks.cactus, new FundamentalData(Pair.of(Type.EARTH, 6)));
		FundamentalList.addFundamentalsToStack(Blocks.vine, new FundamentalData(Pair.of(Type.EARTH, 6)));
		FundamentalList.addFundamentalsToStack(Blocks.cactus, new FundamentalData(Pair.of(Type.EARTH, 6)));
		FundamentalList.addFundamentalsToStack(new ItemStack(Blocks.double_plant, 1, OreDictionary.WILDCARD_VALUE), new FundamentalData(Pair.of(Type.EARTH, 8)));
		
		FundamentalList.addFundamentalsToStack(Items.saddle, new FundamentalData(Pair.of(Type.EARTH, 16), Pair.of(Type.AIR, 16)));
		FundamentalList.addFundamentalsToStack(Items.snowball, new FundamentalData(Pair.of(Type.EARTH, 1), Pair.of(Type.WATER, 1)));
		FundamentalList.addFundamentalsToStack(Items.slime_ball, new FundamentalData(Pair.of(Type.EARTH, 6), Pair.of(Type.AIR, 6)));
		FundamentalList.addFundamentalsToStack(Items.bone, new FundamentalData(Pair.of(Type.EARTH, 6), Pair.of(Type.STONE, 4)));
		FundamentalList.addFundamentalsToStack(Items.ender_pearl, new FundamentalData(Pair.of(Type.AIR, 24), Pair.of(Type.FIRE, 24)));
		
		FundamentalList.addFundamentalsToStack(Items.apple, new FundamentalData(Pair.of(Type.EARTH, 2), Pair.of(Type.WATER, 2)));
		FundamentalList.addFundamentalsToStack(Items.porkchop, new FundamentalData(Pair.of(Type.EARTH, 2), Pair.of(Type.WATER, 2), Pair.of(Type.FIRE, 1)));
		FundamentalList.addFundamentalsToStack(new ItemStack(Items.fish, 1, OreDictionary.WILDCARD_VALUE), new FundamentalData(Pair.of(Type.EARTH, 2), Pair.of(Type.WATER, 4)));
		FundamentalList.addFundamentalsToStack(Items.melon, new FundamentalData(Pair.of(Type.EARTH, 2)));
		FundamentalList.addFundamentalsToStack(Items.beef, new FundamentalData(Pair.of(Type.EARTH, 2), Pair.of(Type.FIRE, 1), Pair.of(Type.WATER, 2)));
		FundamentalList.addFundamentalsToStack(Items.chicken, new FundamentalData(Pair.of(Type.EARTH, 2), Pair.of(Type.FIRE, 1), Pair.of(Type.WATER, 2)));
		FundamentalList.addFundamentalsToStack(Items.rotten_flesh, new FundamentalData(Pair.of(Type.EARTH, 5), Pair.of(Type.FIRE, 2)));
		FundamentalList.addFundamentalsToStack(Items.spider_eye, new FundamentalData(Pair.of(Type.EARTH, 5), Pair.of(Type.AIR, 2)));
		FundamentalList.addFundamentalsToStack(Items.carrot, new FundamentalData(Pair.of(Type.EARTH, 2)));
		FundamentalList.addFundamentalsToStack(Items.potato, new FundamentalData(Pair.of(Type.EARTH, 3)));
		FundamentalList.addFundamentalsToStack(Items.poisonous_potato, new FundamentalData(Pair.of(Type.EARTH, 2), Pair.of(Type.AIR, 1)));
		
		FundamentalList.addFundamentalsToStack(Items.ghast_tear, new FundamentalData(Pair.of(Type.FIRE, 16), Pair.of(Type.AIR, 16)));
		
		FundamentalList.addFundamentalsToStack(Items.string, new FundamentalData(Pair.of(Type.AIR, 2)));
		FundamentalList.addFundamentalsToStack(Items.feather, new FundamentalData(Pair.of(Type.AIR, 4)));
		FundamentalList.addFundamentalsToStack(Items.gunpowder, new FundamentalData(Pair.of(Type.FIRE, 2), Pair.of(Type.AIR, 2)));
		FundamentalList.addFundamentalsToStack(Items.wheat_seeds, new FundamentalData(Pair.of(Type.AIR, 1), Pair.of(Type.EARTH, 1)));
		FundamentalList.addFundamentalsToStack(Items.wheat, new FundamentalData(Pair.of(Type.AIR, 1), Pair.of(Type.EARTH, 1)));
		FundamentalList.addFundamentalsToStack(Items.flint, new FundamentalData(Pair.of(Type.STONE, 1), Pair.of(Type.FIRE, 1)));
		FundamentalList.addFundamentalsToStack(Items.leather, new FundamentalData(Pair.of(Type.AIR, 4), Pair.of(Type.EARTH, 1)));
		FundamentalList.addFundamentalsToStack(Items.clay_ball, new FundamentalData(Pair.of(Type.EARTH, 1), Pair.of(Type.WATER, 2)));
		FundamentalList.addFundamentalsToStack(Items.reeds, new FundamentalData(Pair.of(Type.EARTH, 3), Pair.of(Type.WATER, 2)));
		FundamentalList.addFundamentalsToStack(Items.egg, new FundamentalData(Pair.of(Type.AIR, 2)));
		FundamentalList.addFundamentalsToStack(Items.blaze_rod, new FundamentalData(Pair.of(Type.FIRE, 6), Pair.of(Type.AIR, 4)));
		FundamentalList.addFundamentalsToStack(Items.nether_wart, new FundamentalData(Pair.of(Type.EARTH, 2), Pair.of(Type.FIRE, 1)));
		FundamentalList.addFundamentalsToStack(Items.nether_star, new FundamentalData(Pair.of(Type.AIR, 32), Pair.of(Type.EARTH, 32), Pair.of(Type.FIRE, 32), Pair.of(Type.STONE, 32), Pair.of(Type.WATER, 32)));
	}

}
