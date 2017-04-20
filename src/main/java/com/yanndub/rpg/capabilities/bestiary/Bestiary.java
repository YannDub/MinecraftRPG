package com.yanndub.rpg.capabilities.bestiary;

import java.util.ArrayList;
import java.util.List;

import com.yanndub.rpg.MinecraftRPG;
import com.yanndub.rpg.listeners.BestiaryListener;
import com.yanndub.rpg.network.PacketBestiaryCapability;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class Bestiary implements IBestiary {
	
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
	
	public void fireAddingMonster(EntityPlayer player, BestiaryCard card) {
		for(BestiaryListener listener : this.listeners) {
			listener.creatureIsAdded(player, card);
		}
	}
	
	public void sync(EntityPlayer player) {
		PacketBestiaryCapability packet = new PacketBestiaryCapability(this);
		if(!player.worldObj.isRemote) {
			EntityPlayerMP playerMP = (EntityPlayerMP) player;
			MinecraftRPG.network.sendTo(packet, playerMP);
		} else {
			MinecraftRPG.network.sendToServer(packet);
		}
	}

	@Override
	public void setCreatures(List<BestiaryCard> bestiary) {
		this.bestiary = bestiary;
	}
}
