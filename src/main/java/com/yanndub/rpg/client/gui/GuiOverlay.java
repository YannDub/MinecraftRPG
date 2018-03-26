package com.yanndub.rpg.client.gui;

import com.yanndub.rpg.capabilities.CapabilityHandler;
import com.yanndub.rpg.data.BankData;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

public class GuiOverlay extends Gui {
	
	public GuiOverlay(ScaledResolution scaled) {
		super();
		Minecraft mc = Minecraft.getMinecraft();
		int width = scaled.getScaledWidth();
		
		String currentMoney = "$" + mc.player.getCapability(CapabilityHandler.MONEY_CAP, null).getMoney();
		String currentMoneyInBank = "$" + BankData.get(mc.world).getAmountOf(mc.player);
		
		mc.fontRenderer.drawStringWithShadow(currentMoney, (width - currentMoney.length() * mc.fontRenderer.FONT_HEIGHT), mc.fontRenderer.FONT_HEIGHT, Integer.parseInt("00FF00", 16));
		mc.fontRenderer.drawStringWithShadow(currentMoneyInBank, (width - currentMoney.length() * mc.fontRenderer.FONT_HEIGHT), mc.fontRenderer.FONT_HEIGHT * 2, Integer.parseInt("AAFFAA", 16));
	}
}
