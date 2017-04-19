package com.yanndub.rpg.capabilities.bestiary;

import com.yanndub.rpg.MinecraftRPG;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class BestiaryCapability implements ICapabilitySerializable<NBTBase> {
	
	private Bestiary bestiary;
	
	
	public BestiaryCapability() {
		this.bestiary = new Bestiary();
	}
	
	@Override
	public NBTBase serializeNBT() {
		return MinecraftRPG.BESTIARY_CAP.getStorage().writeNBT(MinecraftRPG.BESTIARY_CAP, MinecraftRPG.BESTIARY_CAP.getDefaultInstance(), null);
	}

	@Override
	public void deserializeNBT(NBTBase nbt) {
		MinecraftRPG.BESTIARY_CAP.getStorage().readNBT(MinecraftRPG.BESTIARY_CAP, MinecraftRPG.BESTIARY_CAP.getDefaultInstance(), null, nbt);
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return MinecraftRPG.BESTIARY_CAP != null && capability == MinecraftRPG.BESTIARY_CAP;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return MinecraftRPG.BESTIARY_CAP != null && capability == MinecraftRPG.BESTIARY_CAP ? MinecraftRPG.BESTIARY_CAP.<T>cast((MinecraftRPG.BESTIARY_CAP.getDefaultInstance())) : null;
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
