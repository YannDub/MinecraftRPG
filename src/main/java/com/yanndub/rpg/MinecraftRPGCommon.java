package com.yanndub.rpg;

import com.yanndub.rpg.entities.area.EntityArea;
import com.yanndub.rpg.events.RPGPlayerEvent;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class MinecraftRPGCommon {
	
	public void preInit() {
		
	}
	
	public void init() {
		MinecraftForge.EVENT_BUS.register(new RPGPlayerEvent());
		
		EntityRegistry.registerModEntity(EntityArea.class, "Area", 1200, MinecraftRPG.instance, 40, 5, true);
	}
}
