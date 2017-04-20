package com.yanndub.rpg.commands;

import java.util.Collections;
import java.util.List;

import com.yanndub.rpg.capabilities.CapabilityHandler;
import com.yanndub.rpg.capabilities.money.IMoney;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;

public class CommandMoney extends CommandBase {


	@Override
	public String getCommandName() {
		return "money";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "command.money.usage";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if(args.length <= 0) {
			throw new WrongUsageException(this.getCommandUsage(sender));
		}
		
		if(args[0].matches("add")) {
			if(args.length != 3) {
				sender.addChatMessage(new TextComponentTranslation(this.getCommandUsage(sender), new Object[0]));
				sender.addChatMessage(new TextComponentTranslation("command.money.usage.add", new Object[0]));
			} else {
				EntityPlayerMP player = server.getPlayerList().getPlayerByUsername(args[1]);
				if(player == null)
					throw new WrongUsageException("command.money.failed");
				
				int amount = 0;
				try {
					amount = Integer.parseInt(args[2]);
					IMoney money = player.getCapability(CapabilityHandler.MONEY_CAP, null);
					money.addMoney(amount);
					money.sync(player);
				} catch(Exception e) {
					sender.addChatMessage(new TextComponentTranslation("command.money.usage.add", new Object[0]));
				}
			}
		} else if(args[0].matches("remove")) {
			if(args.length != 3) {
				sender.addChatMessage(new TextComponentTranslation(this.getCommandUsage(sender), new Object[0]));
				sender.addChatMessage(new TextComponentTranslation("command.money.usage.remove", new Object[0]));
			} else {
				EntityPlayerMP player = server.getPlayerList().getPlayerByUsername(args[1]);
				if(player == null)
					throw new WrongUsageException("command.money.failed");
				
				int amount = 0;
				try {
					amount = Integer.parseInt(args[2]);
					IMoney money = player.getCapability(CapabilityHandler.MONEY_CAP, null);
					if(money.removeMoney(amount)) {
						money.sync(player);
					} else {
						sender.addChatMessage(new TextComponentTranslation("command.money.remove.failed", new Object[0]));
					}
				} catch(Exception e) {
					sender.addChatMessage(new TextComponentTranslation("command.money.usage.remove", new Object[0]));
				}
			}
		}
	}

	@Override
	public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args,
			BlockPos pos) {
		return args.length == 1 ? getListOfStringsMatchingLastWord(args, new String[] {"add", "remove"}) : args.length == 2
				? getListOfStringsMatchingLastWord(args, server.getPlayerList().getAllUsernames()) : Collections.<String>emptyList();
	}

}
