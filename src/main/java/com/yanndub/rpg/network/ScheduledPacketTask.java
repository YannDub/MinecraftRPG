package com.yanndub.rpg.network;

import com.yanndub.rpg.capabilities.CapabilityHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ScheduledPacketTask implements Runnable {
	
	private EntityPlayer player;
	private PacketBestiaryCapability message;
	
	public ScheduledPacketTask(EntityPlayer player, PacketBestiaryCapability message) {
		this.player = player;
		this.message = message;
	}
	
	@Override
	public void run() {
		EntityPlayer player = this.player == null ? this.getPlayer() : this.player;
		player.getCapability(CapabilityHandler.BESTIARY_CAP, null).setCreatures(message.getBestiary().getCreatures());
	}
	
	@SideOnly(Side.CLIENT)
	private EntityPlayer getPlayer() {
		return Minecraft.getMinecraft().thePlayer;
	}

}
