package com.yanndub.rpg.client.gui;

import org.lwjgl.opengl.GL11;

import com.yanndub.rpg.capabilities.CapabilityHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;

public class GuiOverlay extends Gui {
	
	private ResourceLocation dollarsTexture = new ResourceLocation("minecraftrpg", "textures/gui/dollars.png");
	
	public GuiOverlay(ScaledResolution scaled) {
		super();
		Minecraft mc = Minecraft.getMinecraft();
		int width = scaled.getScaledWidth();
		int height = scaled.getScaledHeight();
		
		String currentMoney = "" + mc.thePlayer.getCapability(CapabilityHandler.MONEY_CAP, null).getMoney();
		
		mc.fontRendererObj.drawStringWithShadow(currentMoney, (width - currentMoney.length() * mc.fontRendererObj.FONT_HEIGHT), mc.fontRendererObj.FONT_HEIGHT, Integer.parseInt("00FF00", 16));
	}
}
