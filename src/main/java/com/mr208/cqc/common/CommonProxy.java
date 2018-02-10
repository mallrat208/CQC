package com.mr208.cqc.common;

import com.mr208.cqc.network.NetworkHandler;

public class CommonProxy
{
	
	public void onPreInit()
	{
	
	}
	
	public void onInit()
	{
		NetworkHandler.init();
	}
	
	public void onPostInit()
	{
	
	}
}
