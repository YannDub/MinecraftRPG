package com.yanndub.rpg.data;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;

import com.yanndub.rpg.MinecraftRPG;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;

public class BankData extends WorldSavedData{

	private static final String ID = MinecraftRPG.MODID + "_bank";
	private HashMap<UUID, Integer> accounts = new HashMap<UUID, Integer>();
	
	public BankData() {
		super(ID);
	}
	
	public void addAccount(EntityPlayer player, int amount) {
		if(!accounts.containsKey(player.getGameProfile().getId()))
			accounts.put(player.getGameProfile().getId(), amount);
	}
	
	public void removeAmount(EntityPlayer player, int amount) {
		this.addAmount(player, -amount);
	}
	
	public void addAmount(EntityPlayer player, int amount) {
		this.markDirty();
		accounts.put(player.getGameProfile().getId(), accounts.get(player.getGameProfile().getId()) + amount);
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
