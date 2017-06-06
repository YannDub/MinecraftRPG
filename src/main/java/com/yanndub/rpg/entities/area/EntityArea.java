package com.yanndub.rpg.entities.area;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityArea extends Entity {

	private int size;
	
	public EntityArea(World worldIn, BlockPos blockPos, int size) {
		super(worldIn);
		this.posX = blockPos.getX();
		this.posY = blockPos.getY();
		this.posZ = blockPos.getZ();
		this.size = size;
	}

	@Override
	protected void entityInit() {
		
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
		this.size = compound.getInteger("size");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
		compound.setInteger("size", this.size);
	}
	
	public int getSize() {
		return this.size;
	}
	
	public void setSize(int size) {
		this.size = size;
	}

}
