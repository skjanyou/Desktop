package com.skjanyou.recycle.services;

import java.util.List;
import java.util.Map;

import com.skjanyou.recycle.pojo.ActionCollection;
import com.skjanyou.recycle.pojo.PluginsEntry;
import com.skjanyou.recycle.pojo.ShortcutKey;

/**系统所需资源加载服务，可以在系统初始化加载数据或者外部更新资源后再次调用更新数据**/
public interface ConfigService {
	/**加载图片资源**/
	public void loadImage(ActionCollection ac);
	/**加载动作资源**/
	public ActionCollection loadConfig();
	/**加载翻译语言**/
	public Map<String,String> loadLanguages();
	/**加载按键代码，用于按键连点**/
	public Map<String,String> loadKeyCodeMap();
	/**加载快捷键代码，用于快捷键**/
	public Map<String,String> loadShortcutKey();
	/**从外部配置文件加载用户自己定义的快捷键**/
	public List<ShortcutKey> loadOuterShortcutKey();
	/**加载外部插件**/
	public List<PluginsEntry> loadPluginsEntry();
	/**加载外部插件配置**/
	public void loadPluginsEntryConfig();
}
