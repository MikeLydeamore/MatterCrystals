package com.insane.mattercrystals.fluids;

import java.util.EnumMap;

import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import com.insane.mattercrystals.MatterCrystals;
import com.insane.mattercrystals.blocks.BlockFundamentalFluid;
import com.insane.mattercrystals.fundamentals.Fundamental;

import cpw.mods.fml.common.registry.GameRegistry;

public class MCFluids {
	
	public static EnumMap<Fundamental.Type, Fluid> fluids = new EnumMap<Fundamental.Type, Fluid>(Fundamental.Type.class);
	
	public static void registerFluids()
	{
		for (Fundamental.Type f : Fundamental.Type.values())
		{
			Fluid fl = new Fluid(MatterCrystals.MODID +"."+f.name().toLowerCase());
			fluids.put(f, fl);
			
			FluidRegistry.registerFluid(fl);
			
			GameRegistry.registerBlock(new BlockFundamentalFluid(fl, Material.water), "fluid"+f.name().toLowerCase());
		}
	}

}
