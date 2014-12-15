package com.insane.mattercrystals.fundamentals;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import lombok.ToString;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

@ToString
public class BasicStack implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
	
	public BasicStack(String string)
	{
		String[] arr = string.split(":");
		if (arr.length<3)
		{
			throw new IllegalArgumentException("Input string must be of length 3");
		}
		if (Item.itemRegistry.getObject(arr[0]+":"+arr[1]) == null)
		{
		}
		else
		{
			this.item = (Item) Item.itemRegistry.getObject(arr[0]+":"+arr[1]);
			this.meta = Integer.parseInt(arr[2]);
			this.id = Item.itemRegistry.getIDForObject(this.item);
		}
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
		return Item.itemRegistry.getNameForObject(this.item)+":"+this.meta;
	}
	
	
	
}
