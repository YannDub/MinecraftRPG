package com.yanndub.rpg.capabilities.bestiary;

import java.util.List;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.player.EntityPlayer;

public interface IBestiary {
	
	public void addMonster(EntityPlayer player, EntityCreature creature);
	
	public void addMonster(EntityPlayer player, String name);
	
	public void addMonster(EntityPlayer player, BestiaryCard card);
	
	public void addMonster(BestiaryCard card);
	
	public void setCreatures(List<BestiaryCard> bestiary);
	
	public List<BestiaryCard> getCreatures();
	
	public void sync(EntityPlayer player);
}
