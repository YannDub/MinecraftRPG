package com.yanndub.rpg.client.renderers;

import org.lwjgl.opengl.GL11;

import com.yanndub.rpg.entities.area.EntityArea;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderArea extends Render<EntityArea> {

	public RenderArea(RenderManager renderManager) {
		super(renderManager);
	}
	
	public void doRender(EntityArea entity, double x, double y, double z, float entityYaw, float partialTicks) {
		int size = entity.getSize();
		int color = Integer.parseInt("FF0000CC", 16);
		
		GlStateManager.pushMatrix();
		GlStateManager.enableBlend();
		GlStateManager.depthMask(false);
		GlStateManager.translate(x, y, z);
		GlStateManager.color(color >> 24, color >> 16 & 0xFF, color >> 8 & 0xFF, color & 0xFF);
		
		this.renderEntityArea(size);
		
		GlStateManager.depthMask(true);
		GlStateManager.disableBlend();
		GlStateManager.popMatrix();
	}
	
	private void renderEntityArea(int size) {
		float mHalf = -size / 2f;
		float pHalf = size / 2f;
		
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder vb = tessellator.getBuffer();
		
		vb.begin(GL11.GL_TRIANGLES, DefaultVertexFormats.POSITION);
		this.renderSquare(vb, mHalf, pHalf, 0, 1, pHalf, pHalf);
		this.renderSquare(vb, pHalf, pHalf, 0, 1, pHalf, mHalf);
		this.renderSquare(vb, pHalf, mHalf, 0, 1, mHalf, mHalf);
		this.renderSquare(vb, mHalf, mHalf, 0, 1, mHalf, pHalf);
		tessellator.draw();
	}
	
	private void renderSquare(BufferBuilder vb, float xLeft, float xRight, float yBottom, float yUp, float zForward, float zBehind) {
		vb.pos(xLeft, yUp, zForward);
		vb.pos(xLeft, yBottom, zForward);
		vb.pos(xRight, yBottom, zBehind);
		
		vb.pos(xLeft, yUp, zForward);
		vb.pos(xRight, yBottom, zBehind);
		vb.pos(xRight, yUp, zBehind);
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityArea entity) {
		return null;
	}

}
