package com.yanndub.rpg.capabilities;

import com.yanndub.rpg.capabilities.bestiary.IBestiary;
import com.yanndub.rpg.capabilities.money.IMoney;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class CapabilityHandler {
	
	@CapabilityInject(IBestiary.class)
    public static final Capability<IBestiary> BESTIARY_CAP = null;
	
	@CapabilityInject(IMoney.class)
    public static final Capability<IMoney> MONEY_CAP = null;
}
