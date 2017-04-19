package com.yanndub.rpg.network;

import com.yanndub.rpg.capabilities.bestiary.RPGBestiary;
import com.yanndub.rpg.capabilities.bestiary.RPGBestiaryCard;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class RPGPacketBestiaryCapability implements IMessage {
	
	private RPGBestiary bestiary;
	
	public RPGPacketBestiaryCapability() {
		this(new RPGBestiary());
	}
	
	public RPGPacketBestiaryCapability(RPGBestiary bestiary) {
		super();
		this.bestiary = bestiary;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		int size = buf.readInt();
		this.bestiary = new RPGBestiary();
		
		
		for(int i = 0; i < size; i++) {
			String type = ByteBufUtils.readUTF8String(buf);
			
			RPGBestiaryCard card = new RPGBestiaryCard(type);
			this.bestiary.addMonster(card);
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.bestiary.getCreatures().size());
		for(RPGBestiaryCard each : this.bestiary.getCreatures()) {
			ByteBufUtils.writeUTF8String(buf, each.getCreatureType());
		}
	}
	
	public RPGBestiary getBestiary() {
		return this.bestiary;
	}
	
	public static class ServerHandler implements IMessageHandler<RPGPacketBestiaryCapability, IMessage> {

		@Override
		public IMessage onMessage(RPGPacketBestiaryCapability message, MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask(new ScheduledPacketTask(ctx.getServerHandler().playerEntity, message));
			return null;
		}
		
	}
	
	public static class ClientHandler implements IMessageHandler<RPGPacketBestiaryCapability, IMessage> {

		@Override
		public IMessage onMessage(RPGPacketBestiaryCapability message, MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask(new ScheduledPacketTask(null, message));
			return null;
		}
		
	}
}
