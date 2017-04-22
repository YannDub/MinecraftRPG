package com.yanndub.rpg.client.gui;

import com.yanndub.rpg.capabilities.CapabilityHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

public class GuiOverlay extends Gui {
	
	public GuiOverlay(ScaledResolution scaled) {
		super();
		Minecraft mc = Minecraft.getMinecraft();
		int width = scaled.getScaledWidth();
		String currentMoney = "" + mc.thePlayer.getCapability(CapabilityHandler.MONEY_CAP, null).getMoney();
		
		mc.fontRendererObj.drawStringWithShadow(currentMoney, (width - currentMoney.length() * mc.fontRendererObj.FONT_HEIGHT), mc.fontRendererObj.FONT_HEIGHT, Integer.parseInt("FFFFFF", 16));
	}
}
