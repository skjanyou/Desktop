package com.skjanyou.recycle.services;

import java.util.List;
import java.util.Map;

import com.skjanyou.recycle.pojo.ActionCollection;
import com.skjanyou.recycle.pojo.PluginsEntry;
import com.skjanyou.recycle.pojo.ShortcutKey;

/**ϵͳ������Դ���ط��񣬿�����ϵͳ��ʼ���������ݻ����ⲿ������Դ���ٴε��ø�������**/
public interface ConfigService {
	/**����ͼƬ��Դ**/
	public void loadImage(ActionCollection ac);
	/**���ض�����Դ**/
	public ActionCollection loadConfig();
	/**���ط�������**/
	public Map<String,String> loadLanguages();
	/**���ذ������룬���ڰ�������**/
	public Map<String,String> loadKeyCodeMap();
	/**���ؿ�ݼ����룬���ڿ�ݼ�**/
	public Map<String,String> loadShortcutKey();
	/**���ⲿ�����ļ������û��Լ�����Ŀ�ݼ�**/
	public List<ShortcutKey> loadOuterShortcutKey();
	/**�����ⲿ���**/
	public List<PluginsEntry> loadPluginsEntry();
	/**�����ⲿ�������**/
	public void loadPluginsEntryConfig();
}
