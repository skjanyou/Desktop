package com.skjanyou.recycle.pojo;

import com.skjanyou.recycle.services.ActionService;
import com.skjanyou.recycle.services.ConfigService;
import com.skjanyou.recycle.services.HotKeyService;
import com.skjanyou.recycle.services.impl.ActionServiceImpl;
import com.skjanyou.recycle.services.impl.ConfigServiceImpl;
import com.skjanyou.recycle.services.impl.HotKeyServiceImpl;

public class ServiceInstance {
	public static ActionService actionService = new ActionServiceImpl();
	public static ConfigService cs = new ConfigServiceImpl();
	public static HotKeyService hotkey = new HotKeyServiceImpl();
}
