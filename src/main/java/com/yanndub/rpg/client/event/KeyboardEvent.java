package com.yanndub.rpg.client.event;

import java.util.Map;

import com.yanndub.rpg.MinecraftRPG;
import com.yanndub.rpg.client.MinecraftRPGClient;
import com.yanndub.rpg.client.gui.GuiBestiary;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;

public class KeyboardEvent {
	
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onEvent(KeyInputEvent event) {
		Map<String, KeyBinding> keyBindings = MinecraftRPGClient.keyBindings;
		
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		
		if(keyBindings.get(MinecraftRPGClient.OPEN_BESTIARY).isPressed())
			player.openGui(MinecraftRPG.instance, GuiBestiary.ID, player.worldObj, (int) player.posX, (int) player.posY, (int) player.posZ);
	}
}
