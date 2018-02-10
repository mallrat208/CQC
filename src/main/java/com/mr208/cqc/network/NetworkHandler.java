package com.mr208.cqc.network;

import com.mr208.cqc.CQC;
import com.mr208.cqc.network.packets.PacketBreachDoor;
import com.mr208.cqc.network.packets.PacketBreachDoorTimeout;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class NetworkHandler
{
	public static final SimpleNetworkWrapper INSTANCE =NetworkRegistry.INSTANCE.newSimpleChannel(CQC.MOD_ID);
	
	public static int packet = 0;
	
	public static void init()
	{
		INSTANCE.registerMessage(PacketBreachDoor.Handler.class, PacketBreachDoor.class, packet++, Side.SERVER);
		INSTANCE.registerMessage(PacketBreachDoorTimeout.Handler.class, PacketBreachDoorTimeout.class, packet++, Side.CLIENT);
	}
}
