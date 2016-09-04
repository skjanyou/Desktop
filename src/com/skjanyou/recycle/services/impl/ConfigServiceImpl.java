package com.skjanyou.recycle.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.skjanyou.plugins.Plugins;
import com.skjanyou.recycle.pojo.ActionCollection;
import com.skjanyou.recycle.pojo.Config;
import com.skjanyou.recycle.pojo.PluginsEntry;
import com.skjanyou.recycle.pojo.ShortcutKey;
import com.skjanyou.recycle.services.ConfigService;
import com.skjanyou.recycle.utils.ClassLoaderUtil;
import com.skjanyou.recycle.utils.ImageCacheUtil;
import com.skjanyou.recycle.utils.XMLParseUtil;
import com.skjanyou.utils.PropertiesUtil;

public class ConfigServiceImpl implements ConfigService {

	@Override
	public void loadImage(ActionCollection ac) {
		ImageCacheUtil.loadImage(ac);
	}

	@Override
	public ActionCollection loadConfig() {
		try {
			return XMLParseUtil.loadActionCollection();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public Map<String, String> loadLanguages() {
		return PropertiesUtil.loadProperties("config/lang.properties").getMap();
	}

	@Override
	public Map<String, String> loadKeyCodeMap() {
		return PropertiesUtil.loadPropertiesByResource("com/skjanyou/recycle/resources/config/keycode.properties").getMap();
	}

	@Override
	public Map<String, String> loadShortcutKey() {
		return PropertiesUtil.loadPropertiesByResource("com/skjanyou/recycle/resources/config/shortcutKeycode.properties").getMap();
	}

	@Override
	public List<ShortcutKey> loadOuterShortcutKey() {
		try {
			return XMLParseUtil.loadShortcutKey();
		} catch (Exception e) {
			return new ArrayList<ShortcutKey>();
		}
	}

	@Override
	public List<PluginsEntry> loadPluginsEntry() {
		try {
			List<PluginsEntry> result = XMLParseUtil.loadPlugins();
			for (PluginsEntry pe : result) {
				try{
					String filepath = "plugins/" + pe.getFilepath();
					String packagepath = pe.getPackagepath();
					Plugins plugins = ClassLoaderUtil.getPluginsInstance(filepath, packagepath);
					pe.setPlugins(plugins);
				}catch(Exception e){
					System.out.println(e);
					continue;
				}
			}
			return result;
		} catch (Exception e) {
			return new ArrayList<PluginsEntry>();
		}
	}

	@Override
	public void loadPluginsEntryConfig() {
		if(Config.pluginsEntryList != null && Config.pluginsEntryList.size() != 0){
			for(PluginsEntry pe : Config.pluginsEntryList){
				Plugins p = pe.getPlugins();
				if(p != null){
					p.loadConfig();
				}
			}
		}
	}



}
