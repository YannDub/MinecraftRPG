package com.yanndub.rpg;

import com.yanndub.rpg.capabilities.RPGBestiaryCapability;
import com.yanndub.rpg.handler.RPGGuiHandler;
import com.yanndub.rpg.network.RPGPacketBestiaryCapability;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = MinecraftRPG.MODID, version = MinecraftRPG.VERSION)
public class MinecraftRPG
{
    public static final String MODID = "minecraftrpg";
    public static final String VERSION = "1.0";
    
    @CapabilityInject(RPGBestiaryCapability.class)
    public static final Capability<RPGBestiaryCapability> RPGPLAYER_CAP = null;
    
    @SidedProxy(clientSide="com.yanndub.rpg.client.MinecraftRPGClient", serverSide="com.yanndub.rpg.server.MinecraftRPGServer")
    public static MinecraftRPGCommon proxy;
    
    @Instance
    public static MinecraftRPG instance = new MinecraftRPG();
    
    public static SimpleNetworkWrapper network;
    
    @EventHandler 
    public void preInit(FMLPreInitializationEvent event) {
    	proxy.preInit();
    	
    	NetworkRegistry.INSTANCE.registerGuiHandler(this, new RPGGuiHandler());

    	network = NetworkRegistry.INSTANCE.newSimpleChannel(MinecraftRPG.MODID);
    	
    	network.registerMessage(RPGPacketBestiaryCapability.ClientHandler.class, RPGPacketBestiaryCapability.class, 3, Side.CLIENT);
    	network.registerMessage(RPGPacketBestiaryCapability.ServerHandler.class, RPGPacketBestiaryCapability.class, 3, Side.SERVER);
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	proxy.init();
    	
    	
    	RPGBestiaryCapability.register();
    }
}
