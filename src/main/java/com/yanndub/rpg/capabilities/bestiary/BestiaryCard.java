package com.yanndub.rpg.capabilities.bestiary;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BestiaryCard {
	
	private String creatureType;
    private EntityCreature creature;
	
	public BestiaryCard() {
		
	}
	
	public BestiaryCard(String creature) {
		this.creatureType = creature;
        this.creature = this.createCreature(creature);
	}
	
	public NBTTagCompound saveData() {
		NBTTagCompound compound = new NBTTagCompound();
		
		compound.setString("creature", this.creatureType);
		
		return compound;
	}
	
	public void loadData(NBTTagCompound compound) {
		this.creatureType = compound.getString("creature");
        this.creature = this.createCreature(this.creatureType);
	}

	@SideOnly(Side.CLIENT)
	private EntityCreature createCreature(String type) {
        EntityCreature creature = (EntityCreature) EntityList.createEntityByIDFromName(new ResourceLocation(type), FMLClientHandler.instance().getWorldClient());
        creature.setAlwaysRenderNameTag(false);
        //if(creature instanceof  EntityAgeable) {
        //    ((EntityAgeable) creature).setGrowingAge(0);
        //}
        return creature;
	}

	public EntityCreature getCreature() {
		return this.creature;
	}
	
	public String getCreatureType() {
		return this.creatureType;
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof BestiaryCard) {
			BestiaryCard card = (BestiaryCard) obj;
			return card.getCreatureType().equals(this.getCreatureType());
		}
		
		return false;
	}
	
}
