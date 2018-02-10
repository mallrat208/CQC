package com.mr208.cqc.network.packets;

import com.mr208.cqc.client.ClientEventHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketBreachDoorTimeout implements IMessage
{
	public PacketBreachDoorTimeout()
	{
	
	}
	
	@Override
	public void fromBytes(ByteBuf buf)
	{
	
	}
	
	@Override
	public void toBytes(ByteBuf buf)
	{
	
	}
	
	public static class Handler implements IMessageHandler<PacketBreachDoorTimeout, IMessage>
	{
		
		@Override
		public IMessage onMessage(PacketBreachDoorTimeout message, MessageContext ctx)
		{
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask( () ->handle(message, ctx));
			return null;
		}
		
		private void handle(PacketBreachDoorTimeout message, MessageContext ctx)
		{
			if(Minecraft.getMinecraft().player != null)
			{
				ClientEventHandler.resetBreachDoorTimer();
			}
		}
	}
}
