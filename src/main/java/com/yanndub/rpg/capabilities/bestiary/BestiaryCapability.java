package com.yanndub.rpg.capabilities.bestiary;

import com.yanndub.rpg.MinecraftRPG;
import com.yanndub.rpg.capabilities.CapabilityHandler;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class BestiaryCapability implements ICapabilitySerializable<NBTBase> {
	
	private Bestiary bestiary;
	private IBestiary instance = CapabilityHandler.BESTIARY_CAP.getDefaultInstance();
	
	
	public BestiaryCapability() {
		this.bestiary = new Bestiary();
	}
	
	@Override
	public NBTBase serializeNBT() {
		return CapabilityHandler.BESTIARY_CAP.getStorage().writeNBT(CapabilityHandler.BESTIARY_CAP, instance, null);
	}

	@Override
	public void deserializeNBT(NBTBase nbt) {
		CapabilityHandler.BESTIARY_CAP.getStorage().readNBT(CapabilityHandler.BESTIARY_CAP, instance, null, nbt);
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return CapabilityHandler.BESTIARY_CAP != null && capability == CapabilityHandler.BESTIARY_CAP;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return CapabilityHandler.BESTIARY_CAP != null && capability == CapabilityHandler.BESTIARY_CAP ? CapabilityHandler.BESTIARY_CAP.<T>cast(instance) : null;
	}
	
	public Bestiary getBestiary() {
		return this.bestiary;
	}
	
	public void setBestiary(Bestiary bestiary) {
		this.bestiary = bestiary;
	}
	
	public static void register() {
		CapabilityManager.INSTANCE.register(IBestiary.class, new BestiaryStorage(), Bestiary.class);
	}

}
