package com.yanndub.rpg.capabilities.money;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class MoneyStorage implements IStorage<IMoney> {

	@Override
	public NBTBase writeNBT(Capability<IMoney> capability, IMoney instance, EnumFacing side) {
		return new NBTTagInt(instance.getMoney());
	}

	@Override
	public void readNBT(Capability<IMoney> capability, IMoney instance, EnumFacing side, NBTBase nbt) {
		instance.setMoney(((NBTTagInt) nbt).getInt());
	}

}
