package com.yanndub.rpg.network;

import com.yanndub.rpg.capabilities.CapabilityHandler;
import com.yanndub.rpg.capabilities.bestiary.Bestiary;
import com.yanndub.rpg.capabilities.bestiary.BestiaryCard;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketBestiaryCapability implements IMessage {
	
	private Bestiary bestiary;
	
	public PacketBestiaryCapability() {
		this(new Bestiary());
	}
	
	public PacketBestiaryCapability(Bestiary bestiary) {
		super();
		this.bestiary = bestiary;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		int size = buf.readInt();
		this.bestiary = new Bestiary();
		
		
		for(int i = 0; i < size; i++) {
			String type = ByteBufUtils.readUTF8String(buf);
			
			BestiaryCard card = new BestiaryCard(type);
			this.bestiary.addMonster(card);
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.bestiary.getCreatures().size());
		for(BestiaryCard each : this.bestiary.getCreatures()) {
			ByteBufUtils.writeUTF8String(buf, each.getCreatureType());
		}
	}
	
	public Bestiary getBestiary() {
		return this.bestiary;
	}
	
	public static class ServerHandler implements IMessageHandler<PacketBestiaryCapability, IMessage> {

		@Override
		public IMessage onMessage(PacketBestiaryCapability message, MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask(new ScheduledPacketTask(ctx.getServerHandler().playerEntity, message));
			return null;
		}
		
	}
	
	public static class ClientHandler implements IMessageHandler<PacketBestiaryCapability, IMessage> {

		@Override
		public IMessage onMessage(PacketBestiaryCapability message, MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask(new ScheduledPacketTask(null, message));
			return null;
		}
		
	}
	
	private static class ScheduledPacketTask implements Runnable {
		
		private EntityPlayer player;
		private PacketBestiaryCapability message;
		
		public ScheduledPacketTask(EntityPlayer player, PacketBestiaryCapability message) {
			this.player = player;
			this.message = message;
		}
		
		@Override
		public void run() {
			EntityPlayer player = this.player == null ? this.getPlayer() : this.player;
			player.getCapability(CapabilityHandler.BESTIARY_CAP, null).setCreatures(message.getBestiary().getCreatures());
		}
		
		@SideOnly(Side.CLIENT)
		private EntityPlayer getPlayer() {
			return Minecraft.getMinecraft().thePlayer;
		}

	}
}
