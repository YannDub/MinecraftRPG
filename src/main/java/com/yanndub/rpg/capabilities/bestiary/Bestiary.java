package com.yanndub.rpg.capabilities.bestiary;

import java.util.ArrayList;
import java.util.List;

import com.yanndub.rpg.listeners.BestiaryListener;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class Bestiary {
	
	private List<BestiaryCard> bestiary;
	private List<BestiaryListener> listeners;
	
	public Bestiary() {
		this(new ArrayList<BestiaryCard>());
	}
	
	public Bestiary(List<BestiaryCard> bestiary) {
		this.listeners = new ArrayList<BestiaryListener>();
		this.bestiary = bestiary;
	}
	
	public void addRPGBestiaryListener(BestiaryListener listener) {
		if(!this.listeners.contains(listener))
			this.listeners.add(listener);
	}
	
	public void removeRPGBestiaryListener(BestiaryListener listener) {
		this.listeners.remove(listener);
	}
	
	public void addMonster(EntityPlayer player, EntityCreature creature) {
		this.addMonster(player, new BestiaryCard(EntityList.getEntityString(creature)));
	}
	
	public void addMonster(EntityPlayer player, String name) {
		this.addMonster(player, new BestiaryCard(name));
	}
	
	public void addMonster(EntityPlayer player, BestiaryCard card) {
		if(!this.bestiary.contains(card)) {
			this.fireAddingMonster(player, card);
			this.addMonster(card);
		}
	}
	
	public void addMonster(BestiaryCard card) {
		if(!this.bestiary.contains(card)) {
			this.bestiary.add(card);
		}
	}
	
	public String toString() {
		String result = "";
		for(BestiaryCard each : this.bestiary) {
			result += each.getCreature() + "\n";
		}
		
		return result;
	}
	
	public List<BestiaryCard> getCreatures() {
		return this.bestiary;
	}
	
	public NBTTagCompound saveData() {
		NBTTagCompound compound = new NBTTagCompound();
		
		for(BestiaryCard each : this.bestiary) {
			compound.setTag(each.getCreatureType(), each.saveData());
		}
		
		return compound;
	}
	
	public void loadData(NBTTagCompound compound) {
		for(String key : compound.getKeySet()) {
			BestiaryCard card = new BestiaryCard();
			card.loadData(compound.getCompoundTag(key));
			this.addMonster(card);
		}
	}
	
	public void fireAddingMonster(EntityPlayer player, BestiaryCard card) {
		for(BestiaryListener listener : this.listeners) {
			listener.creatureIsAdded(player, card);
		}
	}
}
