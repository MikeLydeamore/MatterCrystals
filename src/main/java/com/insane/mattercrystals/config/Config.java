package com.insane.mattercrystals.config;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.insane.mattercrystals.fundamentals.FundamentalList;

public class Config {
	
	//public static JsonConfigReader js = new JsonConfigReader();
	public static Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
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

}
