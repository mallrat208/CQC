package com.mr208.cqc;

import com.mr208.cqc.ConfigHandler.Options;
import com.mr208.cqc.common.CommonProxy;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

@Mod(modid = CQC.MOD_ID, name = CQC.MOD_NAME, version = CQC.MOD_VERS, dependencies = CQC.MOD_DEPS)
public class CQC
{
	public static final String MOD_ID = "cqc";
	public static final String MOD_NAME = "CQC";
	public static final String MOD_VERS = "@MOD_VERSION@";
	public static final String MOD_DEPS = "required-after:mw";
	
	private static final String PROXY_CLIENT = "com.mr208.cqc.client.ClientProxy";
	private static final String PROXY_COMMON = "com.mr208.cqc.common.CommonProxy";
	
	public static Map<Item, Float> shields = new HashMap<>();
	
	public static DamageSource BREACHING;
	
	@SidedProxy(modId = MOD_ID, clientSide = PROXY_CLIENT,serverSide = PROXY_COMMON)
	public static CommonProxy PROXY;
	
	@Mod.Instance(MOD_ID)
	public static CQC INSTANCE;
	
	public static final Logger LOG = LogManager.getLogger(MOD_ID);
	
	@EventHandler
	public void onPreInit(FMLPreInitializationEvent event)
	{
		PROXY.onPreInit();
		
		BREACHING = new DamageSource("door.breaching").setDamageBypassesArmor().setDamageIsAbsolute();
	}
	
	@EventHandler
	public void onInit(FMLInitializationEvent event)
	{
		PROXY.onInit();
	}
	
	@EventHandler
	public void onPostInit(FMLPostInitializationEvent event)
	{
		PROXY.onPostInit();
		
		if(Options.SHIELDS.SHIELDS_WEAK_VS_GUNS)
		{
			LOG.info("Shields are set to be weak vs gun fire");
			
			for(String entry:Options.SHIELDS.SHIELDS_AND_BREAK_CHANCE)
			{
				String[] parts = StringUtils.split(entry,"@");
				if(parts.length!=2)
					continue;
				
				Item shield = ForgeRegistries.ITEMS.getValue(new ResourceLocation(parts[0]));
				float breakChance = Float.parseFloat(parts[1]);
				
				if(shield != null)
				{
					shields.put(shield, breakChance);
					LOG.info("Registering a Shield, {}, with a {} chance to break", shield, breakChance);
				}
			}
		}
	}
}
