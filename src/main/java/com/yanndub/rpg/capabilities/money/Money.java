package com.yanndub.rpg.capabilities.money;

import com.yanndub.rpg.MinecraftRPG;
import com.yanndub.rpg.network.PacketMoneyCapability;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class Money implements IMoney {
	
	private int money;
	
	public Money() {
		this.money = 0;
	}
	
	@Override
	public void addMoney(int amount) {
		this.money += amount;
	}

	@Override
	public boolean removeMoney(int amount) {
		if(this.money - amount < 0) {
			return false;
		}
		this.money -= amount;
		return true;
	}

	@Override
	public int getMoney() {
		return this.money;
	}

	@Override
	public void setMoney(int money) {
		this.money = money;
	}

	@Override
	public void sync(EntityPlayer player) {
		if(!player.world.isRemote) {
			PacketMoneyCapability packet = new PacketMoneyCapability(this);
			MinecraftRPG.network.sendTo(packet, (EntityPlayerMP) player);
		}
	}
}
