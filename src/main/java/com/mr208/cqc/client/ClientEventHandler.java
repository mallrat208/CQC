package com.mr208.cqc.client;

import com.mr208.cqc.CQC;
import com.mr208.cqc.network.NetworkHandler;
import com.mr208.cqc.network.packets.PacketBreachDoor;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.relauncher.Side;

@EventBusSubscriber(value = Side.CLIENT,modid = CQC.MOD_ID)
public class ClientEventHandler
{
	private static Minecraft mc = Minecraft.getMinecraft();
	
	private static boolean breachDoorTimeout = false;
	private static int breachDoorTimer = 0;
	
	@SubscribeEvent()
	public static void onClientTickEvent(TickEvent.ClientTickEvent event)
	{
		if(event.phase == Phase.END)
		{
			if(mc.player != null && mc.currentScreen == null)
			{
				if(!breachDoorTimeout && KeyHandler.breachDoor.isPressed())
				{
					BlockPos pos = getTargetBlockstate(mc.player, mc.getRenderPartialTicks());
					if(!pos.equals(new BlockPos(0,0,0)))
					{
						NetworkHandler.INSTANCE.sendToServer(new PacketBreachDoor(pos));
						
						breachDoorTimeout=true;
						breachDoorTimer=40;
					}
				}
			}
			
			if(breachDoorTimeout && breachDoorTimer>0)
			{
				breachDoorTimer--;
			} else if(breachDoorTimeout && breachDoorTimer<=0)
			{
				breachDoorTimeout = false;
				breachDoorTimer = 0;
			}
		}
	}
	
	private static BlockPos getTargetBlockstate(EntityPlayerSP playerSP, float partialTicks)
	{
		RayTraceResult rayTraceResult = playerSP.rayTrace(2,partialTicks);
		
		if(rayTraceResult != null && rayTraceResult.typeOfHit == Type.BLOCK)
		{
			return rayTraceResult.getBlockPos();
		}
		return new BlockPos(0,0,0);
	}
	
	public static void resetBreachDoorTimer()
	{
		breachDoorTimer = 0;
		breachDoorTimeout = false;
	}
}
