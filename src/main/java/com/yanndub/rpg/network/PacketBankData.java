package com.yanndub.rpg.network;

import com.yanndub.rpg.data.BankData;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketBankData implements IMessage {

	private String UUID;
	private int amount;

	public PacketBankData() {
		this("", 0);
	}

	public PacketBankData(String UUID, int amount) {
		super();
		this.UUID = UUID;
		this.amount = amount;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.UUID = ByteBufUtils.readUTF8String(buf);
		this.amount = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, UUID);
		buf.writeInt(amount);
	}

	public int getAmount() {
		return this.amount;
	}

	public String getUUID() {
		return this.UUID;
	}

	public static class ServerHandler implements IMessageHandler<PacketBankData, IMessage> {

		@Override
		public IMessage onMessage(PacketBankData message, MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask(new ScheduledPacketTask(BankData.get(ctx.getServerHandler().player.world), message));
			return null;
		}

	}

	public static class ClientHandler implements IMessageHandler<PacketBankData, IMessage> {

		@Override
		public IMessage onMessage(PacketBankData message, MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask(new ScheduledPacketTask(null, message));
			return null;
		}

	}

	private static class ScheduledPacketTask implements Runnable {

		private BankData bank;
		private PacketBankData message;

		public ScheduledPacketTask(BankData bank, PacketBankData message) {
			this.bank = bank;
			this.message = message;
		}

		@Override
		public void run() {
			BankData bank = this.bank == null ? this.getBank() : this.bank;
			bank.setAmount(java.util.UUID.fromString(message.UUID), message.amount);
		}

		@SideOnly(Side.CLIENT)
		private BankData getBank() {
			return BankData.get(Minecraft.getMinecraft().world);
		}

	}

}
