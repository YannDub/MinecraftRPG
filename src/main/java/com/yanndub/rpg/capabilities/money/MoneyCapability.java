package com.yanndub.rpg.capabilities.money;

import com.yanndub.rpg.capabilities.CapabilityHandler;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class MoneyCapability implements ICapabilitySerializable<NBTBase> {

	private IMoney instance = CapabilityHandler.MONEY_CAP.getDefaultInstance();
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return CapabilityHandler.MONEY_CAP != null && capability == CapabilityHandler.MONEY_CAP;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return CapabilityHandler.MONEY_CAP != null && capability == CapabilityHandler.MONEY_CAP ? CapabilityHandler.MONEY_CAP.<T>cast(instance) : null;
	}

	@Override
	public NBTBase serializeNBT() {
		return CapabilityHandler.MONEY_CAP.getStorage().writeNBT(CapabilityHandler.MONEY_CAP, instance, null);
	}

	@Override
	public void deserializeNBT(NBTBase nbt) {
		CapabilityHandler.MONEY_CAP.getStorage().readNBT(CapabilityHandler.MONEY_CAP, instance, null, nbt);
	}
	
	public static void register() {
		CapabilityManager.INSTANCE.register(IMoney.class, new MoneyStorage(), Money.class);
	}

}
