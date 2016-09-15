package com.yanndub.rpg.network;

import com.yanndub.rpg.MinecraftRPG;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ScheduledPacketTask implements Runnable {
	
	private EntityPlayer player;
	private RPGPacketPlayerCapabilities message;
	
	public ScheduledPacketTask(EntityPlayer player, RPGPacketPlayerCapabilities message) {
		this.player = player;
		this.message = message;
	}
	
	@Override
	public void run() {
		EntityPlayer player = this.player == null ? this.getPlayer() : this.player;
		player.getCapability(MinecraftRPG.RPGPLAYER_CAP, null).setBestiary(message.getBestiary());
	}
	
	@SideOnly(Side.CLIENT)
	private EntityPlayer getPlayer() {
		return Minecraft.getMinecraft().thePlayer;
	}

}
