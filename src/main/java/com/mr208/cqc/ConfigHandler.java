package com.mr208.cqc;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.Name;
import net.minecraftforge.common.config.Config.RangeDouble;
import net.minecraftforge.common.config.Config.RangeInt;

public class ConfigHandler
{
	@Config(modid = CQC.MOD_ID, name = CQC.MOD_NAME, category = "General")
	public static class Options
	{
		@Comment("Door Breaching Options")
		@Name("Breaching")
		public static Breaching BREACHING = new Breaching();
		
		@Comment("Various Options Regarding Shields")
		@Name("Shields")
		public static Shields SHIELDS = new Shields();
		
		public static class Shields
		{
			@Comment("Should shields take a large amount of damage from weapon fire")
			@Name("Shields are Weak to Guns")
			public boolean SHIELDS_WEAK_VS_GUNS = true;
			
			@Comment("List of Shields with chances to break when it. Format is RegistryName@BreakChanceAsFloat")
			@Name("Shields and Break Chance")
			public String[] SHIELDS_AND_BREAK_CHANCE = {"minecraft:shield@0.15f"};
		}
		
		public static class Breaching
		{
			@Comment("Should you be able to kick open Iron Doors")
			@Name("Breach Metal Doors")
			public boolean BREACH_METAL_DOORS = true;
			
			@Comment("Should Breaching Iron Doors Deal Damage to the Player")
			@Name("Breaching Metal Hurts")
			public boolean BREACHING_METAL_DANGEROUS = true;
			
			@Comment("Amount of Damage taken when Breaching Iron Doors")
			@Name("Metal Door Player Damage")
			@RangeInt(min = 0, max = 20)
			public int BREACHING_METAL_DAMAGE = 4;
		}
	}
}
