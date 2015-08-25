package com.insane.mattercrystals.items;

import java.util.List;

import com.insane.mattercrystals.fundamentals.Fundamental.Type;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;

public class ItemCapsule extends Item {

	public ItemCapsule() {
		super();
		this.setUnlocalizedName("capsule");
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean p_77624_4_)
	{
		if (stack.hasTagCompound())
		{
			ItemStack storedStack = ItemStack.loadItemStackFromNBT((NBTTagCompound) stack.stackTagCompound.getTag("storedItem"));
			list.add(storedStack.getDisplayName());

			for (Type t : Type.values())
			{
				int num = stack.stackTagCompound.getInteger(t.name());
				if (num > 0)
					list.add("  "+t.getLocalizedName()+": "+num);
			}
			
			list.add(StatCollector.translateToLocal("capsule.runs.remaining")+": "+stack.stackTagCompound.getInteger("runs"));
		}
	}

}
