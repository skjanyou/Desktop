package com.skjanyou.recycle.pojo;

import java.awt.Image;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.skjanyou.recycle.view.KeySetView.MapItem;


/**���ڴ洢����**/
public class Config {
	public static List<ActionCollection> actionColloection_list;                //��������
	
	public static ActionCollection actionCollection;                                 //����
	
	public static Map<String,String> languages;                                     //���Դ���
	
	public static Map<String,String> keyCodeMap ;                               //��¼��������Ҫ����İ�������
	
	public static ImageCache<String, Image> imageCache = new ImageCache<String, Image>();  //ͼƬ����
	
	public static Map<MapItem,Integer> keymap = new HashMap<MapItem, Integer>();             //��������
	
	public static Map<String,String> shortcutKey;                                   //��ݼ����룬�밴�����벻ͬ��ʹ�õ���JIntellitype������KeyEvent������ʹ����ĸ������� ���磺F1�ʹ���vk_f1
	
	public static List<ShortcutKey> outerShortcutKey;                          //���ⲿ��ݼ�.XML�ļ���������ã�����ȫ�ֿ�ݼ�
	
	public static List<PluginsEntry> pluginsEntryList;                                 //�ⲿ���
}
