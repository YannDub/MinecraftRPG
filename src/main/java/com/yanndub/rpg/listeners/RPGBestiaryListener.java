package com.yanndub.rpg.listeners;

import com.yanndub.rpg.bestiary.RPGBestiaryCard;

import net.minecraft.entity.player.EntityPlayer;

public interface RPGBestiaryListener {
	
	public void creatureIsAdded(EntityPlayer player, RPGBestiaryCard card);
}
