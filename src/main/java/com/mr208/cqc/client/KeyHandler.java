package com.mr208.cqc.client;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.input.Keyboard;

public class KeyHandler
{
	private static final String CATEGORY = "cqc.keybinds.category";
	
	public static KeyBinding breachDoor;
	
	public static void init()
	{
		breachDoor = new KeyBinding("cqc.keybinds.breachdoor", Keyboard.KEY_B, CATEGORY);
		
		ClientRegistry.registerKeyBinding(breachDoor);
	}
}
