package com.mr208.cqc.common;

import com.mr208.cqc.CQC;
import com.vicmatskiv.weaponlib.WeaponSpawnEntity;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static com.mr208.cqc.CQC.shields;

@EventBusSubscriber(modid = CQC.MOD_ID)
public class EventHandler
{
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void onLivingAtacked(LivingAttackEvent event)
	{
		if(event.isCanceled())
			return;
		
		if(event.getSource().getImmediateSource() instanceof WeaponSpawnEntity && event.getEntityLiving().isActiveItemStackBlocking())
		{
			ItemStack activeStack = event.getEntityLiving().getActiveItemStack();
			
			float damagePercent = shields.getOrDefault(activeStack.getItem(), 0f);
			
			if(activeStack.isItemStackDamageable())
			{
				int maxDamage = activeStack.getMaxDamage();
				int gunDamage = (int)(maxDamage * damagePercent);
				
				if((int)event.getAmount() > gunDamage)
				{
					if((int)event.getAmount() > gunDamage * 2)
						gunDamage = gunDamage * 2;
					else
						gunDamage = (int)event.getAmount();
				}
				
				activeStack.damageItem(gunDamage, event.getEntityLiving());
			}
		}
	}
}
