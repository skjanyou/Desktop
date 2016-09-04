package com.skjanyou.recycle.start;


import com.skjanyou.recycle.services.StartupService;
import com.skjanyou.recycle.services.impl.StartupServiceImpl;

public class Start {
	public static void main(String[] args) {
		StartupService startup = new StartupServiceImpl();
		startup.start();
	}
}
