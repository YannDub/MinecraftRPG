package com.yanndub.rpg.events;

import com.yanndub.rpg.MinecraftRPG;
import com.yanndub.rpg.capabilities.bestiary.Bestiary;
import com.yanndub.rpg.capabilities.bestiary.BestiaryCapability;
import com.yanndub.rpg.capabilities.bestiary.BestiaryCard;
import com.yanndub.rpg.listeners.BestiaryListener;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;

public class RPGPlayerEvent implements BestiaryListener {
	
	private BestiaryCapability entityCapability(Entity entity) {
		return entity.getCapability(MinecraftRPG.RPGPLAYER_CAP, null);
	}
	
	@SubscribeEvent
	public void onPlayerSpawnInWorld(EntityJoinWorldEvent event) {
		if(!(event.getEntity() instanceof EntityPlayer)) return;
		
		EntityPlayer player = (EntityPlayer) event.getEntity();
		if(!player.worldObj.isRemote)this.entityCapability(player).sync();
	}
	
	@SubscribeEvent
	public void onEntityCreatureDeath(LivingDeathEvent event) {
		if(event.getEntity() instanceof EntityCreature) {
			if(event.getSource().getSourceOfDamage() instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) event.getSource().getSourceOfDamage();
				Bestiary bestiary = this.entityCapability(player).getBestiary();
				EntityCreature creature = (EntityCreature) event.getEntity();
				
				bestiary.addRPGBestiaryListener(this);
				
				bestiary.addMonster(player, creature);
				if(!player.worldObj.isRemote)
					this.entityCapability(player).sync();
			}
			
		}
		
	}
	
	@SubscribeEvent
	public void onPlayerCloned(PlayerEvent.Clone event) {
		if(event.isWasDeath()) {
			if(event.getOriginal().hasCapability(MinecraftRPG.RPGPLAYER_CAP, null)) {
				BestiaryCapability cap = this.entityCapability(event.getOriginal());
				BestiaryCapability newCap = this.entityCapability(event.getEntityPlayer());
				
				newCap.setBestiary(cap.getBestiary());
			}
		}
	}
	
	@SubscribeEvent
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		System.out.println("test respawn event");
		if(!event.player.worldObj.isRemote) {
			this.entityCapability(event.player).sync();
		}
	}
	
	@SubscribeEvent
	public void onAttachCapability(AttachCapabilitiesEvent.Entity event) {
		if(!(event.getEntity() instanceof EntityPlayer)) return;

		event.addCapability(new ResourceLocation(MinecraftRPG.MODID + ":RPGPLAYER_CAP"), new BestiaryCapability((EntityPlayer) event.getEntity()));
	}

	@Override
	public void creatureIsAdded(EntityPlayer player, BestiaryCard card) {
		player.addChatMessage(new TextComponentString(EntityList.getEntityString(card.getCreature()) + " has been added to the bestiary"));
	}
}
