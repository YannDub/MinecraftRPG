package com.yanndub.rpg.client;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.input.Keyboard;

import com.yanndub.rpg.MinecraftRPGCommon;
import com.yanndub.rpg.client.event.KeyboardEvent;
import com.yanndub.rpg.client.event.RenderGuiEvent;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class MinecraftRPGClient extends MinecraftRPGCommon {
	
	public static Map<String, KeyBinding> keyBindings;
	
	public static final String OPEN_BESTIARY = "openBestiary";
	
	public void preInit() {
		super.preInit();
	}
	
	public void init() {
		super.init();
		
		keyBindings = new HashMap<String, KeyBinding>();
		
		this.addKey(OPEN_BESTIARY, "key.gui.bestiary", Keyboard.KEY_P, "key.categories.gameplay");
		
		MinecraftForge.EVENT_BUS.register(new KeyboardEvent());
		MinecraftForge.EVENT_BUS.register(new RenderGuiEvent());
		
		
	}
	
	private void addKey(String key, String name, int keyCode, String description) {
		KeyBinding keyBinding = new KeyBinding(name, keyCode, description);
		keyBindings.put(key, keyBinding);
		ClientRegistry.registerKeyBinding(keyBinding);
	}
}
