package com.skjanyou.recycle.services;

import com.melloware.jintellitype.HotkeyListener;

public interface HotKeyService {
	/**ע��Ĭ�ϵ�ȫ�ְ�������**/
	public void registerDefaultHotKey();
	/**�ݲ�ʹ��**/
	public void registerHotKey(int identifier, String modifierAndKeyCode);
	/**�Ƴ���������**/
	public void removeHotKeyListener(HotkeyListener listener);
	/**�Ƴ��ȼ�**/
	public void unregisterHotKey(int identifier);
}
