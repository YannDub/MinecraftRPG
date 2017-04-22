package com.yanndub.rpg.client.event;

import com.yanndub.rpg.client.gui.GuiOverlay;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RenderGuiEvent {
	
	@SubscribeEvent
	public void onRenderGui(RenderGameOverlayEvent.Post event) {
		new GuiOverlay(event.getResolution());
	}
}
