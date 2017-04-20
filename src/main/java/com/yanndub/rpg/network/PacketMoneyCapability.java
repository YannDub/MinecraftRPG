package com.yanndub.rpg.network;

import com.yanndub.rpg.capabilities.CapabilityHandler;
import com.yanndub.rpg.capabilities.money.Money;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketMoneyCapability implements IMessage {
	
	private Money money;
	
	public PacketMoneyCapability() {
		this(new Money());
	}
	
	public PacketMoneyCapability(Money money) {
		super();
		this.money = money;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.money = new Money();
		this.money.setMoney(buf.readInt());
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.money.getMoney());
	}
	
	public Money getMoney() {
		return this.money;
	}
	
public static class ServerHandler implements IMessageHandler<PacketMoneyCapability, IMessage> {

	@Override
	public IMessage onMessage(PacketMoneyCapability message, MessageContext ctx) {
		Minecraft.getMinecraft().addScheduledTask(new ScheduledPacketTask(ctx.getServerHandler().playerEntity, message));
		return null;
	}
	
}

public static class ClientHandler implements IMessageHandler<PacketMoneyCapability, IMessage> {

	@Override
	public IMessage onMessage(PacketMoneyCapability message, MessageContext ctx) {
		Minecraft.getMinecraft().addScheduledTask(new ScheduledPacketTask(null, message));
		return null;
	}
	
}
	
private static class ScheduledPacketTask implements Runnable {
		
		private EntityPlayer player;
		private PacketMoneyCapability message;
		
		public ScheduledPacketTask(EntityPlayer player, PacketMoneyCapability message) {
			this.player = player;
			this.message = message;
		}
		
		@Override
		public void run() {
			EntityPlayer player = this.player == null ? this.getPlayer() : this.player;
			player.getCapability(CapabilityHandler.MONEY_CAP, null).setMoney(message.money.getMoney());
		}
		
		@SideOnly(Side.CLIENT)
		private EntityPlayer getPlayer() {
			return Minecraft.getMinecraft().thePlayer;
		}

	}

}
