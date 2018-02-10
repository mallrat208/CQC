package com.mr208.cqc.network.packets;

import com.mr208.cqc.CQC;
import com.mr208.cqc.ConfigHandler;
import com.mr208.cqc.ConfigHandler.Options;
import com.mr208.cqc.network.NetworkHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockDoor.EnumDoorHalf;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketBreachDoor implements IMessage
{
	public PacketBreachDoor()
	{
	
	}
	
	private int x;
	private int y;
	private int z;
	
	public PacketBreachDoor(int x, int y, int z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public PacketBreachDoor(BlockPos pos)
	{
		this.x = pos.getX();
		this.y = pos.getY();
		this.z = pos.getZ();
	}
	
	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.x = buf.readInt();
		this.y = buf.readInt();
		this.z = buf.readInt();
	}
	
	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
	}
	
	public static class Handler implements IMessageHandler<PacketBreachDoor, IMessage>
	{
		
		@Override
		public IMessage onMessage(PacketBreachDoor message, MessageContext ctx)
		{
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask( ()-> handle(message, ctx));
			return null;
		}
		
		private void handle(PacketBreachDoor message, MessageContext ctx)
		{
			BlockPos pos = new BlockPos(message.x, message.y, message.z);
			EntityPlayer player = ctx.getServerHandler().player;
			World world = player.world;
			
			IBlockState blockState = world.getBlockState(pos);
			Block block = blockState.getBlock();
			
			if(block != Blocks.AIR && (block instanceof BlockDoor || block instanceof BlockTrapDoor || block instanceof BlockFenceGate))
			{
				if(block instanceof BlockDoor)
				{
					BlockPos blockpos = blockState.getValue(BlockDoor.HALF) == EnumDoorHalf.LOWER ? pos : pos.down();
					if(!pos.equals(blockpos))
					{
						pos = blockpos;
						blockState = world.getBlockState(pos);
					}
				}
				
				if(!Options.BREACHING.BREACH_METAL_DOORS)
					return;
				
				if(blockState.getMaterial() == Material.IRON &&Options.BREACHING.BREACHING_METAL_DANGEROUS)
				{
					player.attackEntityFrom(CQC.BREACHING, Options.BREACHING.BREACHING_METAL_DAMAGE);
				}
				
				if(!blockState.getValue(BlockDoor.OPEN))
				{
					blockState = blockState.cycleProperty(BlockDoor.OPEN);
					world.setBlockState(pos, blockState, 3);
					world.markBlockRangeForRenderUpdate(pos, pos);
					SoundEvent sound = blockState.getMaterial() == Material.IRON ? SoundEvents.BLOCK_ANVIL_PLACE : SoundEvents.ENTITY_ZOMBIE_BREAK_DOOR_WOOD;
					world.playSound(null, pos, sound, SoundCategory.BLOCKS, 0.3f, 1f);
					
				}
			}
			
			NetworkHandler.INSTANCE.sendTo(new PacketBreachDoorTimeout(), (EntityPlayerMP) player);
		}
	}
}
