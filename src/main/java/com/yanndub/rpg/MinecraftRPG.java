package com.yanndub.rpg;

import com.yanndub.rpg.capabilities.bestiary.BestiaryCapability;
import com.yanndub.rpg.capabilities.money.MoneyCapability;
import com.yanndub.rpg.commands.CommandMoney;
import com.yanndub.rpg.handler.RPGGuiHandler;
import com.yanndub.rpg.network.PacketBankData;
import com.yanndub.rpg.network.PacketBestiaryCapability;
import com.yanndub.rpg.network.PacketMoneyCapability;
import com.yanndub.rpg.utils.Config;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = MinecraftRPG.MODID, version = MinecraftRPG.VERSION)
public class MinecraftRPG
{
    public static final String MODID = "minecraftrpg";
    public static final String VERSION = "1.0";
    
    @SidedProxy(clientSide="com.yanndub.rpg.client.MinecraftRPGClient", serverSide="com.yanndub.rpg.server.MinecraftRPGServer")
    public static MinecraftRPGCommon proxy;
    
    @Instance
    public static MinecraftRPG instance = new MinecraftRPG();
    
    public static SimpleNetworkWrapper network;
    
    @EventHandler 
    public void preInit(FMLPreInitializationEvent event) {
    	proxy.preInit();
    	
    	//Config
    	Configuration config = new Configuration(event.getSuggestedConfigurationFile());
    	Config.startBankAccount = config.get("general", "startBankAccount", Config.startBankAccount).getInt();
    	
    	//Network
    	NetworkRegistry.INSTANCE.registerGuiHandler(this, new RPGGuiHandler());

    	network = NetworkRegistry.INSTANCE.newSimpleChannel(MinecraftRPG.MODID);
    	
    	network.registerMessage(PacketBestiaryCapability.ClientHandler.class, PacketBestiaryCapability.class, 3, Side.CLIENT);
    	network.registerMessage(PacketBestiaryCapability.ServerHandler.class, PacketBestiaryCapability.class, 3, Side.SERVER);
    	
    	network.registerMessage(PacketMoneyCapability.ClientHandler.class, PacketMoneyCapability.class, 4, Side.CLIENT);
    	network.registerMessage(PacketMoneyCapability.ServerHandler.class, PacketMoneyCapability.class, 4, Side.SERVER);
    	
    	network.registerMessage(PacketBankData.ClientHandler.class, PacketBankData.class, 5, Side.CLIENT);
    	network.registerMessage(PacketBankData.ServerHandler.class, PacketBankData.class, 5, Side.SERVER);
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	proxy.init();
    	
    	
    	BestiaryCapability.register();
    	MoneyCapability.register();
    }
    
    @EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
    	event.registerServerCommand(new CommandMoney());
    }
}
