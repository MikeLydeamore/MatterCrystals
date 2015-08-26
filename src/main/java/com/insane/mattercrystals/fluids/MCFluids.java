package com.insane.mattercrystals.fluids;

import java.util.ArrayList;
import java.util.EnumMap;

import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import com.insane.mattercrystals.MatterCrystals;
import com.insane.mattercrystals.blocks.BlockFundamentalFluid;
import com.insane.mattercrystals.fundamentals.Fundamental;

import cpw.mods.fml.common.registry.GameRegistry;

public class MCFluids {
	
	public static EnumMap<Fundamental.Type, Fluid> fluidsEnumList = new EnumMap<Fundamental.Type, Fluid>(Fundamental.Type.class);
	public static ArrayList<Fluid> fluids = new ArrayList<Fluid>();
	public static void registerFluids()
	{
		for (Fundamental.Type f : Fundamental.Type.values())
		{
			Fluid fl = new Fluid(MatterCrystals.MODID +"."+f.name().toLowerCase());
			fluidsEnumList.put(f, fl);
			fluids.add(fl);
			
			FluidRegistry.registerFluid(fl);
			
			GameRegistry.registerBlock(new BlockFundamentalFluid(fl, Material.water), "fluid"+f.name().toLowerCase());
		}
	}

}
