package com.insane.mattercrystals.config;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.insane.mattercrystals.fundamentals.BasicStack;
import com.insane.mattercrystals.fundamentals.FundamentalData;
import com.insane.mattercrystals.fundamentals.FundamentalList;
import com.insane.mattercrystals.fundamentals.Fundamental.Type;

public class Config {

	public static Gson gson;

	public Config()
	{
		gson = new GsonBuilder().registerTypeAdapter(new TypeToken<EnumMap<Type, Integer>>() {}.getType(),
				new EnumMapInstanceCreator<Type, Integer>(Type.class)).setPrettyPrinting().create();
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
