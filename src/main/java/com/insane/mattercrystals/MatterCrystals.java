package com.insane.mattercrystals;

import java.io.File;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;

import com.google.gson.Gson;
import com.insane.mattercrystals.blocks.MCBlocks;
import com.insane.mattercrystals.config.Config;
import com.insane.mattercrystals.fluids.MCFluids;
import com.insane.mattercrystals.fundamentals.Fundamental.Type;
import com.insane.mattercrystals.fundamentals.FundamentalData;
import com.insane.mattercrystals.fundamentals.FundamentalList;
import com.insane.mattercrystals.fundamentals.Fundamentals;
import com.insane.mattercrystals.fundamentals.VanillaCrafting;
import com.insane.mattercrystals.fundamentals.VanillaFurnace;
import com.insane.mattercrystals.gui.GuiHandler;
import com.insane.mattercrystals.handlers.TooltipHandler;
import com.insane.mattercrystals.items.MCItems;
import com.insane.mattercrystals.network.PacketHandler;
import com.insane.mattercrystals.util.OreDict;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Mod(modid=MatterCrystals.MODID, name="MatterCrystals", version="0.0.1")
public class MatterCrystals {
	
	public static final String MODID = "mattercrystals";
	
	@Mod.Instance(MODID)
	public static MatterCrystals instance;
	
	@SidedProxy(clientSide="com.insane.mattercrystals.client.ClientProxy", serverSide="com.insane.mattercrystals.CommonProxy")
	public static CommonProxy proxy;
	
	public static int crystalRenderID;
	
	public static Gson gson = new Gson();
	
	public static File configDir;
	public static File fundamentalFile;
	public static File configFile;
	
	public static Config config;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		MCBlocks.registerBlocks();
		MCItems.registerItems();
		MCFluids.registerFluids();
		OreDict.registerOreDict();
		proxy.initRenderers();
		
		config = new Config();
		
		configDir = new File(event.getSuggestedConfigurationFile().getParentFile().getAbsolutePath() + "/" + MODID);
		configDir.mkdirs();
		fundamentalFile = new File(configDir.getAbsolutePath() + "/fundamentals.json");
		configFile = new File(configDir.getAbsolutePath()+"/config.cfg");
		
		Config.doNormalConfig(configFile);
		
		MinecraftForge.EVENT_BUS.register(new TooltipHandler());
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
		PacketHandler.init();
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		Config.readFundamentals(fundamentalFile);
		
		FundamentalList.addFundamentalsToStack(new ItemStack(Blocks.cobblestone, 1, OreDictionary.WILDCARD_VALUE), new FundamentalData(Pair.of(Type.STONE, 1)));
		FundamentalList.addFundamentalsToStack(new ItemStack(Blocks.log, 1, OreDictionary.WILDCARD_VALUE), new FundamentalData(Pair.of(Type.EARTH, 8)));
		FundamentalList.addFundamentalsToStack(new ItemStack(Blocks.log2, 1, OreDictionary.WILDCARD_VALUE), new FundamentalData(Pair.of(Type.EARTH, 8)));
		FundamentalList.addFundamentalsToStack(new ItemStack(Items.iron_ingot, 1, OreDictionary.WILDCARD_VALUE), new FundamentalData(Pair.of(Type.STONE, 24), Pair.of(Type.FIRE, 2)));
		
		//Process Fundamentals
		Fundamentals.addRecipeProvider(VanillaCrafting.INSTANCE);
		Fundamentals.addRecipeProvider(VanillaFurnace.INSTANCE);
		
		Fundamentals.processRecipes(4);
		
		Config.writeFundamentals(fundamentalFile);
		
	}
	
	public static CreativeTabs tabMC = new CreativeTabs("matterCrystals")
	{
		@Override
		@SideOnly(Side.CLIENT)
		public Item getTabIconItem()
		{
			return Items.baked_potato;
		}
	};
	

}
