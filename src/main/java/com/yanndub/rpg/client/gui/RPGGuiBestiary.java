package com.yanndub.rpg.client.gui;

import java.io.IOException;

import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Mouse;

import com.yanndub.rpg.MinecraftRPG;
import com.yanndub.rpg.capabilities.bestiary.RPGBestiary;
import com.yanndub.rpg.capabilities.bestiary.RPGBestiaryCard;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;

public class RPGGuiBestiary extends GuiScreen {
	
	public static final int ID = 0;
	
	private RPGBestiary bestiary;
    private RPGBestiaryCard card;
    private EntityCreature selectedCreature;
	private RPGGuiSlotBestiaryCard cardsList;

    private int listWidth;
	private int selected = -1;
	
	public RPGGuiBestiary(EntityPlayer player) {
		this.bestiary = player.getCapability(MinecraftRPG.RPGPLAYER_CAP, null).getBestiary();
	}

	public void initGui() {
		this.listWidth = 0;

		for(RPGBestiaryCard card : this.bestiary.getCreatures()) {
			listWidth = Math.max(this.listWidth, this.fontRendererObj.getStringWidth(card.getCreatureType()) + 10);
		}

		this.cardsList = new RPGGuiSlotBestiaryCard(this, this.width / 4, 16);
        this.card = null;
	}
	
	public boolean doesGuiPauseGame() {
		return false;
	}
	
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        int middleWidth = this.width / 2 + this.listWidth;
        int middleHeight = this.height / 2;

        if(card != null) {
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

            int widthEntity = 64;
            int heightEntity = 128;

            GuiInventory.drawEntityOnScreen(middleWidth, middleHeight, 50, (float) (middleWidth) - mouseX, (float) (middleHeight - 50) - mouseY, this.selectedCreature);
        }
        this.cardsList.drawScreen(mouseX, mouseY, partialTicks);

        this.drawForeground(middleWidth, middleHeight);

		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	private void drawForeground(int middleWidth, int middleHeight) {
        if(card != null) {
            this.drawCenteredString(this.fontRendererObj, EntityList.getEntityString(card.getCreature()), middleWidth, 10, 0xffffff);

            this.mc.getTextureManager().bindTexture(ICONS);
            this.drawTexturedModalRect(middleWidth - 100, middleHeight + 30, 52, 0, 9, 9);
            if(card.getCreature().getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE) != null)
                this.drawTexturedModalRect(middleWidth + 50, middleHeight + 30 - 9, 18, 94, 18, 18);

            this.fontRendererObj.drawString("" + card.getCreature().getMaxHealth(), middleWidth - 89, middleHeight + 30, 0xffffff);
            if(card.getCreature().getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE) != null)
                this.fontRendererObj.drawString("" + card.getCreature().getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue(), middleWidth + 70, middleHeight + 30, 0xffffff);

        }
    }

	@Override
    public void handleMouseInput() throws IOException
    {
        int mouseX = Mouse.getEventX() * this.width / this.mc.displayWidth;
        int mouseY = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;

        super.handleMouseInput();
        this.cardsList.handleMouseInput(mouseX, mouseY);
    }
	
	public void selectCardIndex(int index)
    {
        if (index == this.selected)
            return;
        this.selected = index;
        this.card = this.bestiary.getCreatures().get(index);
        this.selectedCreature = card.getCreature();
    }

    public boolean cardIndexSelected(int index)
    {
        return index == selected;
    }
	
	public RPGBestiary getBestiary() {
		return this.bestiary;
	}
	
	public Minecraft getMinecraftInstance()
    {
        return mc;
    }
	
	public FontRenderer getFontRenderer() {
		return this.fontRendererObj;
	}
}
