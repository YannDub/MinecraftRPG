package com.yanndub.rpg;

import com.yanndub.rpg.events.RPGPlayerEvent;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class MinecraftRPGCommon {
	
	public void preInit() {
		
	}
	
	public void init() {
		MinecraftForge.EVENT_BUS.register(new RPGPlayerEvent());
	}
}
