package com.yanndub.rpg.capabilities.bestiary;

import java.util.concurrent.Callable;

import com.yanndub.rpg.MinecraftRPG;
import com.yanndub.rpg.network.PacketBestiaryCapability;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;

public class BestiaryCapability implements ICapabilityProvider, INBTSerializable<NBTTagCompound>{
	
	private Bestiary bestiary;
	
	private EntityPlayer player;
	
	public BestiaryCapability(EntityPlayer player) {
		this.player = player;
		this.bestiary = new Bestiary();
	}
	
	public void sync() {
		PacketBestiaryCapability packet = new PacketBestiaryCapability(this.getBestiary());
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
		this.bestiary = new Bestiary();
		
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
	
	public Bestiary getBestiary() {
		return this.bestiary;
	}
	
	public void setBestiary(Bestiary bestiary) {
		this.bestiary = bestiary;
	}
	
	public static void register() {
		CapabilityManager.INSTANCE.register(BestiaryCapability.class, new BestiaryCapability.Storage(), new BestiaryCapability.Factory());
	}
	
	private static class Storage implements Capability.IStorage<BestiaryCapability> {

		@Override
		public NBTBase writeNBT(Capability<BestiaryCapability> capability, BestiaryCapability instance,
				EnumFacing side) {
			return null;
		}

		@Override
		public void readNBT(Capability<BestiaryCapability> capability, BestiaryCapability instance,
				EnumFacing side, NBTBase nbt) {
			
		}
		
	}
	
	private static class Factory implements Callable<BestiaryCapability> {

		@Override
		public BestiaryCapability call() throws Exception {
			return null;
		}
		
	}

}
