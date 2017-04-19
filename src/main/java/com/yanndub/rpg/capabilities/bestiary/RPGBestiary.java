package com.yanndub.rpg.capabilities.bestiary;

import java.util.ArrayList;
import java.util.List;

import com.yanndub.rpg.listeners.RPGBestiaryListener;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class RPGBestiary {
	
	private List<RPGBestiaryCard> bestiary;
	private List<RPGBestiaryListener> listeners;
	
	public RPGBestiary() {
		this(new ArrayList<RPGBestiaryCard>());
	}
	
	public RPGBestiary(List<RPGBestiaryCard> bestiary) {
		this.listeners = new ArrayList<RPGBestiaryListener>();
		this.bestiary = bestiary;
	}
	
	public void addRPGBestiaryListener(RPGBestiaryListener listener) {
		if(!this.listeners.contains(listener))
			this.listeners.add(listener);
	}
	
	public void removeRPGBestiaryListener(RPGBestiaryListener listener) {
		this.listeners.remove(listener);
	}
	
	public void addMonster(EntityPlayer player, EntityCreature creature) {
		this.addMonster(player, new RPGBestiaryCard(EntityList.getEntityString(creature)));
	}
	
	public void addMonster(EntityPlayer player, String name) {
		this.addMonster(player, new RPGBestiaryCard(name));
	}
	
	public void addMonster(EntityPlayer player, RPGBestiaryCard card) {
		if(!this.bestiary.contains(card)) {
			this.fireAddingMonster(player, card);
			this.addMonster(card);
		}
	}
	
	public void addMonster(RPGBestiaryCard card) {
		if(!this.bestiary.contains(card)) {
			this.bestiary.add(card);
		}
	}
	
	public String toString() {
		String result = "";
		for(RPGBestiaryCard each : this.bestiary) {
			result += each.getCreature() + "\n";
		}
		
		return result;
	}
	
	public List<RPGBestiaryCard> getCreatures() {
		return this.bestiary;
	}
	
	public NBTTagCompound saveData() {
		NBTTagCompound compound = new NBTTagCompound();
		
		for(RPGBestiaryCard each : this.bestiary) {
			compound.setTag(each.getCreatureType(), each.saveData());
		}
		
		return compound;
	}
	
	public void loadData(NBTTagCompound compound) {
		for(String key : compound.getKeySet()) {
			RPGBestiaryCard card = new RPGBestiaryCard();
			card.loadData(compound.getCompoundTag(key));
			this.addMonster(card);
		}
	}
	
	public void fireAddingMonster(EntityPlayer player, RPGBestiaryCard card) {
		for(RPGBestiaryListener listener : this.listeners) {
			listener.creatureIsAdded(player, card);
		}
	}
}
