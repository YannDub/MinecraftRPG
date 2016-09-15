package com.yanndub.rpg.bestiary;

import java.lang.reflect.InvocationTargetException;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class RPGBestiaryCard {
	
	private String creatureType;
    private EntityCreature creature;
	
	public RPGBestiaryCard() {
		
	}
	
	public RPGBestiaryCard(String creature) {
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
        EntityCreature creature = (EntityCreature) EntityList.createEntityByName(type, FMLClientHandler.instance().getWorldClient());
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
		if(obj instanceof RPGBestiaryCard) {
			RPGBestiaryCard card = (RPGBestiaryCard) obj;
			return card.getCreatureType().equals(this.getCreatureType());
		}
		
		return false;
	}
	
}
