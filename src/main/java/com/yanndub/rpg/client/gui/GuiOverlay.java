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
		
		String currentMoney = "$" + mc.thePlayer.getCapability(CapabilityHandler.MONEY_CAP, null).getMoney();
		String currentMoneyInBank = "$" + BankData.get(mc.theWorld).getAmountOf(mc.thePlayer);
		
		mc.fontRendererObj.drawStringWithShadow(currentMoney, (width - currentMoney.length() * mc.fontRendererObj.FONT_HEIGHT), mc.fontRendererObj.FONT_HEIGHT, Integer.parseInt("00FF00", 16));
		System.out.println("Draw gui overlay");
		if(currentMoneyInBank != null)
			mc.fontRendererObj.drawStringWithShadow(currentMoneyInBank, (width - currentMoney.length() * mc.fontRendererObj.FONT_HEIGHT), mc.fontRendererObj.FONT_HEIGHT * 2, Integer.parseInt("AAFFAA", 16));
	}
}
