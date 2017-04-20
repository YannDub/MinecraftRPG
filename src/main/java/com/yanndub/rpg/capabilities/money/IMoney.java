package com.yanndub.rpg.capabilities.money;

import net.minecraft.entity.player.EntityPlayer;

public interface IMoney {
	
	public void addMoney(int amount);
	
	public boolean removeMoney(int amount);
	
	public int getMoney();
	
	public void setMoney(int money);
	
	public void sync(EntityPlayer player);
}
