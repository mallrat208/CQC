package com.mr208.cqc.client;

import com.mr208.cqc.common.CommonProxy;

public class ClientProxy extends CommonProxy
{
	@Override
	public void onInit()
	{
		super.onInit();
		
		KeyHandler.init();
	}
}
