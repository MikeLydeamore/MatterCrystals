package com.insane.mattercrystals.network;

import com.insane.mattercrystals.MatterCrystals;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class PacketHandler {
	
	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(MatterCrystals.MODID);
	private static int id = 0;
	
	public static void init()
	{
		INSTANCE.registerMessage(MessageMatterMelterInputUpdate.Handler.class, MessageMatterMelterInputUpdate.class, id++, Side.CLIENT);
		INSTANCE.registerMessage(MessageMatterMelterFluidUpdate.Handler.class, MessageMatterMelterFluidUpdate.class, id++, Side.CLIENT);
		
		INSTANCE.registerMessage(MessageAtomicAssemblerItemUpdate.Handler.class, MessageAtomicAssemblerItemUpdate.class, id++, Side.CLIENT);
	}

}
