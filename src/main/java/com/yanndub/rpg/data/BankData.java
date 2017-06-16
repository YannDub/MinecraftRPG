package com.yanndub.rpg.data;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;

import com.yanndub.rpg.MinecraftRPG;
import com.yanndub.rpg.network.PacketBankData;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;

public class BankData extends WorldSavedData{

	private static final String ID = MinecraftRPG.MODID + "_bank";
	private HashMap<UUID, Integer> accounts;
	
	public BankData() {
		super(ID);
		accounts = new HashMap<UUID, Integer>();
	}
	
	public void addAccountIfNotExist(EntityPlayer player, int amount) {
		if(!accounts.containsKey(player.getGameProfile().getId())) {
			accounts.put(player.getGameProfile().getId(), amount);
		}
		
		if(!player.worldObj.isRemote) {			
			PacketBankData message = new PacketBankData(player.getGameProfile().getId().toString(), amount);
			MinecraftRPG.network.sendTo(message, (EntityPlayerMP) player); 
		}
	}
	
	public void removeAmount(EntityPlayer player, int amount) {
		this.removeAmount(player.getGameProfile().getId(), amount);
		if(!player.worldObj.isRemote) {			
			PacketBankData message = new PacketBankData(player.getGameProfile().getId().toString(), amount);
			MinecraftRPG.network.sendTo(message, (EntityPlayerMP) player); 
		}
	}
	
	public void removeAmount(UUID id, int amount) {
		this.addAmount(id, -amount);
	}
	
	public void addAmount(UUID id, int amount) {
		this.markDirty();
		accounts.put(id, accounts.get(id) + amount);
	}
	
	public void addAmount(EntityPlayer player, int amount) {
		this.addAmount(player.getGameProfile().getId(), amount);
		if(!player.worldObj.isRemote) {			
			PacketBankData message = new PacketBankData(player.getGameProfile().getId().toString(), amount);
			MinecraftRPG.network.sendTo(message, (EntityPlayerMP) player); 
		}
	}
	
	public int getAmountOf(UUID uuid) {
		return accounts.get(uuid);
	}
	
	public void setAmount(UUID id, int amount) {
		this.accounts.put(id, amount);
	}
	
	public int getAmountOf(EntityPlayer player) {
		return this.getAmountOf(player.getGameProfile().getId());
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		for(String key : nbt.getKeySet()) {
			accounts.put(UUID.fromString(key), nbt.getInteger(key));
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		for(Entry<UUID, Integer> entries : accounts.entrySet()) {
			nbt.setInteger(entries.getKey().toString(), entries.getValue());
		}
		return nbt;
	}
	
	public static BankData get(World world) {
		BankData data = (BankData) world.loadItemData(BankData.class, ID);
		if(data == null) {
			data = new BankData();
			world.setItemData(ID, data);
		}
		return data;
	}
	
	public String toString() {
		String res = "";
		for(Entry<UUID, Integer> entries : accounts.entrySet()) {
			if(!res.equals(""))
				res += ", ";
			res += entries.getKey() + ":" + entries.getValue();
		}
		return res;
	}

}
