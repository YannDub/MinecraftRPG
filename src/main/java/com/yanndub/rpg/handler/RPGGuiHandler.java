package com.yanndub.rpg.handler;

import com.yanndub.rpg.client.gui.RPGGuiBestiary;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class RPGGuiHandler implements IGuiHandler {
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch(ID) {
		case RPGGuiBestiary.ID:
			return new RPGGuiBestiary(player);
		}
		return null;
	}

}
