package com.yanndub.rpg.capabilities.bestiary;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

public class BestiaryStorage implements Capability.IStorage<IBestiary> {

	@Override
	public NBTBase writeNBT(Capability<IBestiary> capability, IBestiary instance, EnumFacing side) {
		NBTTagCompound compound = new NBTTagCompound();
		
		for(BestiaryCard each : instance.getCreatures()) {
			compound.setTag(each.getCreatureType(), each.saveData());
		}
		
		return compound;
	}

	@Override
	public void readNBT(Capability<IBestiary> capability, IBestiary instance, EnumFacing side,
			NBTBase nbt) {
		for(String key : ((NBTTagCompound) nbt).getKeySet()) {
			BestiaryCard card = new BestiaryCard();
			card.loadData(((NBTTagCompound) nbt).getCompoundTag(key));
			instance.addMonster(card);
		}
	}

}
