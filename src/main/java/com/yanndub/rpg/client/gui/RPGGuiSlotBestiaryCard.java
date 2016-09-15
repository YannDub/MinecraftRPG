package com.yanndub.rpg.client.gui;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityList;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraftforge.fml.client.GuiScrollingList;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class RPGGuiSlotBestiaryCard extends GuiScrollingList {

	private RPGGuiBestiary parent;
	private int listWidth;
	
	public RPGGuiSlotBestiaryCard(RPGGuiBestiary parent, int listWidth, int slotHeight) {
		super(parent.getMinecraftInstance(), listWidth, parent.height, 0,  parent.height, 0, slotHeight, parent.width, parent.height);
		this.parent = parent;
		this.listWidth = listWidth;
	}

	@Override
	protected int getSize() {
		return this.parent.getBestiary().getCreatures().size();
	}

	@Override
	protected void elementClicked(int index, boolean doubleClick) {
		this.parent.selectCardIndex(index);
	}
	
	@Override
    protected int getContentHeight()
    {
        return this.getSize() * 16 + 1;
    }

	@Override
	protected boolean isSelected(int index) {
		return this.parent.cardIndexSelected(index);
	}

	@Override
	protected void drawBackground() {
		this.parent.drawDefaultBackground();
	}

	@Override
	protected void drawSlot(int slotIdx, int entryRight, int slotTop, int slotBuffer, Tessellator tess) {
		FontRenderer font = this.parent.getFontRenderer();
		String type = EntityList.getEntityString(this.parent.getBestiary().getCreatures().get(slotIdx).getCreature());

		font.drawString(type, entryRight - this.listWidth / 2 - font.getStringWidth(type) / 2 + 4, slotTop + 16 / 2 - font.FONT_HEIGHT / 2 - 2, 0xffffff);
	}

}
