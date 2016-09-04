package com.skjanyou.recycle.services;

import com.melloware.jintellitype.HotkeyListener;

public interface HotKeyService {
	/**注册默认的全局按键监听**/
	public void registerDefaultHotKey();
	/**暂不使用**/
	public void registerHotKey(int identifier, String modifierAndKeyCode);
	/**移除按键监听**/
	public void removeHotKeyListener(HotkeyListener listener);
	/**移除热键**/
	public void unregisterHotKey(int identifier);
}
