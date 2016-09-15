package com.yanndub.rpg.capabilities;

import java.util.concurrent.Callable;

import com.yanndub.rpg.MinecraftRPG;
import com.yanndub.rpg.bestiary.RPGBestiary;
import com.yanndub.rpg.network.RPGPacketPlayerCapabilities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;

public class RPGPlayerCapabilities implements ICapabilityProvider, INBTSerializable<NBTTagCompound>{
	
	private RPGBestiary bestiary;
	private EntityPlayer player;
	
	public RPGPlayerCapabilities(EntityPlayer player) {
		this.player = player;
		this.bestiary = new RPGBestiary();
	}
	
	public void sync() {
		RPGPacketPlayerCapabilities packet = new RPGPacketPlayerCapabilities(this.getBestiary());
		if(!this.player.worldObj.isRemote) {
			EntityPlayerMP playerMP = (EntityPlayerMP) this.player;
			MinecraftRPG.network.sendTo(packet, playerMP);
		} else {
			MinecraftRPG.network.sendToServer(packet);
		}
	}
	
	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound compound = new NBTTagCompound();
		
		compound.setTag("bestiary", this.bestiary.saveData());
		
		return compound;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		this.bestiary = new RPGBestiary();
		
		this.bestiary.loadData(nbt.getCompoundTag("bestiary"));
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return MinecraftRPG.RPGPLAYER_CAP != null && capability == MinecraftRPG.RPGPLAYER_CAP;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return MinecraftRPG.RPGPLAYER_CAP != null && capability == MinecraftRPG.RPGPLAYER_CAP ? (T)this : null;
	}
	
	public RPGBestiary getBestiary() {
		return this.bestiary;
	}
	
	public void setBestiary(RPGBestiary bestiary) {
		this.bestiary = bestiary;
	}
	
	public static void register() {
		CapabilityManager.INSTANCE.register(RPGPlayerCapabilities.class, new RPGPlayerCapabilities.Storage(), new RPGPlayerCapabilities.Factory());
	}
	
	private static class Storage implements Capability.IStorage<RPGPlayerCapabilities> {

		@Override
		public NBTBase writeNBT(Capability<RPGPlayerCapabilities> capability, RPGPlayerCapabilities instance,
				EnumFacing side) {
			return null;
		}

		@Override
		public void readNBT(Capability<RPGPlayerCapabilities> capability, RPGPlayerCapabilities instance,
				EnumFacing side, NBTBase nbt) {
			
		}
		
	}
	
	private static class Factory implements Callable<RPGPlayerCapabilities> {

		@Override
		public RPGPlayerCapabilities call() throws Exception {
			return null;
		}
		
	}

}
