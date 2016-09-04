package com.skjanyou.recycle.pojo;

import java.awt.Image;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.skjanyou.recycle.view.KeySetView.MapItem;


/**用于存储数据**/
public class Config {
	public static List<ActionCollection> actionColloection_list;                //动作集合
	
	public static ActionCollection actionCollection;                                 //动作
	
	public static Map<String,String> languages;                                     //语言代码
	
	public static Map<String,String> keyCodeMap ;                               //记录下来的需要续点的按键代码
	
	public static ImageCache<String, Image> imageCache = new ImageCache<String, Image>();  //图片缓存
	
	public static Map<MapItem,Integer> keymap = new HashMap<MapItem, Integer>();             //按键代码
	
	public static Map<String,String> shortcutKey;                                   //快捷键代码，与按键代码不同，使用的是JIntellitype而不是KeyEvent，可以使用字母代替代码 例如：F1就代表vk_f1
	
	public static List<ShortcutKey> outerShortcutKey;                          //从外部快捷键.XML文件读入的配置，这是全局快捷键
	
	public static List<PluginsEntry> pluginsEntryList;                                 //外部插件
}
