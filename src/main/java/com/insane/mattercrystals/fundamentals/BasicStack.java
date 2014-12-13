package com.insane.mattercrystals.fundamentals;

import lombok.ToString;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

@ToString
public class BasicStack {
	
	public Item item;
	public int meta;
	public Fundamental fundamentals;
	private int id;
	

	public BasicStack(ItemStack stack)
	{
		this.item = stack.getItem();
		this.meta = stack.getItemDamage();
		this.id = Item.itemRegistry.getIDForObject(stack.getItem());
	}
	
	@Override
	public boolean equals(Object o)
	{
		BasicStack stack = (BasicStack) o;
		if (stack.meta==OreDictionary.WILDCARD_VALUE || this.meta==OreDictionary.WILDCARD_VALUE)
			return this.item==stack.item;
		else
			return this.item==stack.item && this.meta==stack.meta;
	}
	
	@Override
	public int hashCode()
	{
		return this.id;
	}
	
	public String toString()
	{
		return this.item.getUnlocalizedName()+":"+this.meta;
	}
	
}
