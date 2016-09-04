package com.skjanyou.recycle.plugins;

import com.skjanyou.plugins.Plugins;
import com.skjanyou.recycle.pojo.PluginsEntry;


public class PluginsExecutor {
		public static void Execut(PluginsEntry pe){
			if("ÊÇ".equals(pe.getSingle()) && pe.isStarted()){
				return;
			}
			Plugins plugins = pe.getPlugins();
			plugins.startPlugins();
			pe.setStarted(true);
		}
}
