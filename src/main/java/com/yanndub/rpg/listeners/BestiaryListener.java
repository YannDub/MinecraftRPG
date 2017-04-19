package com.yanndub.rpg.listeners;

import com.yanndub.rpg.capabilities.bestiary.BestiaryCard;

import net.minecraft.entity.player.EntityPlayer;

public interface BestiaryListener {
	
	public void creatureIsAdded(EntityPlayer player, BestiaryCard card);
}
