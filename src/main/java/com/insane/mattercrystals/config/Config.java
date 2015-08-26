package com.insane.mattercrystals.config;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Iterator;
import net.minecraftforge.common.config.Configuration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import com.google.gson.reflect.TypeToken;
import com.insane.mattercrystals.fundamentals.BasicStack;
import com.insane.mattercrystals.fundamentals.FundamentalData;
import com.insane.mattercrystals.fundamentals.FundamentalList;
import com.insane.mattercrystals.fundamentals.Fundamental.Type;

public class Config {

	public static Gson gson;

	//Categories
	public static String categoryMelter = "MatterMelter";
	public static String categoryAssembler = "AtomicAssembler";
	public static String categoryCapsuleCreator = "CapsuleCreator";
	public static String categoryGeneral = "General";
	
	//Normal Config Values
	public static boolean melterEnable = true;
	public static boolean melterAllowNormalItemsToBeMelted = true;
	public static int melterRFCostPerTick = 400;
	public static int melterTankSize = 16000;
	
	public static boolean capsuleCreatorEnable = true;
	public static int capsuleCreatorRFCostPerTick = 400;
	
	public static boolean assemblerEnable = true;
	public static int assemblerRFCostPerTick = 400;
	
	public static int fluidMultiplier = 100;
	
	public static int capsuleRuns = 6;
	
	public Config()
	{
		gson = new GsonBuilder().registerTypeAdapter(new TypeToken<EnumMap<Type, Integer>>() {}.getType(),
				new EnumMapInstanceCreator<Type, Integer>(Type.class)).setPrettyPrinting().create();
	}
	
	public static void doNormalConfig(File file)
	{
		Configuration cfg = new Configuration(file);
		
		//Melter
		melterEnable = cfg.get(categoryMelter, "enableMelter", true).getBoolean();
		melterAllowNormalItemsToBeMelted = cfg.get(categoryMelter, "allowNormalItemsToBeMelted", true).getBoolean();
		melterRFCostPerTick = cfg.get(categoryMelter, "MelterRFCostPerTick", 400, "Set to 0 to disable RF cost entirely").getInt();
		melterTankSize = cfg.get(categoryMelter, "MelterTankSize", 16000, "Size of the internal tanks in the Matter Melter").getInt();
		
		//Capsule Creator
		capsuleCreatorEnable = cfg.get(categoryCapsuleCreator, "enableCapsuleCreator", true).getBoolean();
		capsuleCreatorRFCostPerTick = cfg.get(categoryCapsuleCreator, "CapsuleCreatorRFCostPerTick", 400, "Set to 0 to disable RF cost entirely").getInt();
		
		//Assembler
		assemblerEnable = cfg.get(categoryAssembler, "enableAssembler", true).getBoolean();
		assemblerRFCostPerTick = cfg.get(categoryAssembler, "AssemblerRFCostPerTick", 400, "Set to 0 to disable RF cost entirely").getInt();
		
		//General
		capsuleRuns = cfg.get(categoryGeneral,"runsPerCapsule", 6, "Number of 'runs' a single capsule will get before disintegrating").getInt();
	}

	public static void writeFundamentals(File f)
	{
		try
		{
			FileWriter fw = new FileWriter(f);
			gson.toJson(FundamentalList.fundamentalList, fw);

			fw.close();

		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static void readFundamentals(File f)
	{
		try
		{
			FileReader fr = new FileReader(f);
			HashMap<String, FundamentalData> gsonInput = gson.fromJson(fr, new TypeToken<HashMap<String, FundamentalData>>(){}.getType());
			//FundamentalList.fundamentalList = gson.fromJson(fr, new TypeToken<Map<BasicStack, FundamentalData>>(){}.getType());
			
			Iterator<String> it = gsonInput.keySet().iterator();
			while (it.hasNext())
			{
				String s = (String) it.next();
				BasicStack stack = new BasicStack(s);
				FundamentalList.fundamentalList.put(stack, gsonInput.get(s));
				System.out.println(stack.item.getUnlocalizedName()+":"+FundamentalList.fundamentalList.get(stack).getValue(Type.STONE));
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	class EnumMapInstanceCreator<K extends Enum<K>, V> implements InstanceCreator<EnumMap<K, V>>
	{
		private final Class<K> enumClazz;

		public EnumMapInstanceCreator(final Class<K> enumClazz)
		{
			super();
			this.enumClazz = enumClazz;
		}

		@Override
		public EnumMap<K, V> createInstance(final java.lang.reflect.Type type)
		{
			return new EnumMap<K, V>(enumClazz);
		}

	}


}
