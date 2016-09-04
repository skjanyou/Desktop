package com.skjanyou.recycle.services.impl;

import java.util.List;

import com.melloware.jintellitype.HotkeyListener;
import com.melloware.jintellitype.JIntellitype;
import com.skjanyou.recycle.pojo.Config;
import com.skjanyou.recycle.pojo.HotkeyConstants;
import com.skjanyou.recycle.pojo.Parameter;
import com.skjanyou.recycle.pojo.ShortcutKey;
import com.skjanyou.recycle.pojo.ShortcutKeyConstants;
import com.skjanyou.recycle.services.HotKeyService;

public class HotKeyServiceImpl implements HotKeyService {

	@Override
	public void registerDefaultHotKey() {
		try{
			List<ShortcutKey> list = Config.outerShortcutKey;
			for (ShortcutKey shortcutKey : list) {
				String name = shortcutKey.getName();
				String keyCode1 = shortcutKey.getKeyCode1();
				String keyCode2 = shortcutKey.getKeyCode2();
				int identifier = getIdentifier(name);
				String modifierAndKeyCode = "";
				if(ShortcutKeyConstants.unused.equals(keyCode1) && ShortcutKeyConstants.unused.equals(keyCode2)){
					continue;
				}
				
				if(!ShortcutKeyConstants.unused.equals(keyCode1) && !ShortcutKeyConstants.unused.equals(keyCode2)){
					modifierAndKeyCode = keyCode1 + "+" + keyCode2;
				}
				if(ShortcutKeyConstants.unused.equals(keyCode1) && !ShortcutKeyConstants.unused.equals(keyCode2)){
					modifierAndKeyCode = keyCode2;
				}
				if(!ShortcutKeyConstants.unused.equals(keyCode1) && ShortcutKeyConstants.unused.equals(keyCode2)){
					modifierAndKeyCode = keyCode1;
				}
				
				
				
				JIntellitype.getInstance().registerHotKey(identifier, modifierAndKeyCode);
				
			}
//			JIntellitype.getInstance().registerHotKey(MyHotkeyListener.HIDE_KEY_MARK, JIntellitype.MOD_ALT, (int)'S');    
//			JIntellitype.getInstance().registerHotKey(MyHotkeyListener.EXIT_KEY_MARK, JIntellitype.MOD_ALT, (int)'Q');
//			JIntellitype.getInstance().registerHotKey(MyHotkeyListener.MOUSE_POINT_KEY_MARK, JIntellitype.MOD_ALT, (int)'X');
//			JIntellitype.getInstance().registerHotKey(MyHotkeyListener.ISMOVE_KEY_MARK, JIntellitype.MOD_ALT, (int)'M');
//			JIntellitype.getInstance().registerHotKey(MyHotkeyListener.INPUTFIELD_KEY_MARK, JIntellitype.MOD_ALT, (int)'Z');
//			JIntellitype.getInstance().registerHotKey(MyHotkeyListener.SCREEN_KEY_MARK, JIntellitype.MOD_ALT, (int)'A');
//
//			JIntellitype.getInstance().registerHotKey(MyHotkeyListener.POINT_KEY_MARK, "ALT+F1");
//			JIntellitype.getInstance().registerHotKey(MyHotkeyListener.CLICK_KEY_MARK, "ALT+F2");
//			JIntellitype.getInstance().registerHotKey(MyHotkeyListener.STOP_CLICK_KEY_MARK, "ALT+F3");
//			JIntellitype.getInstance().registerHotKey(MyHotkeyListener.RECORD_KECODE_MARK,"ALT+F4");
//			JIntellitype.getInstance().registerHotKey(MyHotkeyListener.START_KECODE_MARK,"ALT+F5");
//			JIntellitype.getInstance().registerHotKey(MyHotkeyListener.STOP_KECODE_MARK, "ALT+F6");
			//第二步：添加热键监听器  
			JIntellitype.getInstance().addHotKeyListener(Parameter.hotkeyListener);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void registerHotKey(int identifier, String modifierAndKeyCode) {

	}

	@Override
	public void removeHotKeyListener(HotkeyListener listener) {
		JIntellitype.getInstance().removeHotKeyListener(listener);
	}

	@Override
	public void unregisterHotKey(int identifier) {
		JIntellitype.getInstance().unregisterHotKey(identifier);
	}
	
	private int getIdentifier(String name) {
		int result = -1;
		if(ShortcutKeyConstants.mouseRecord.equals(name)){
			result = HotkeyConstants.POINT_KEY_MARK;
		}else if(ShortcutKeyConstants.mouseClickStart.equals(name)){
			result = HotkeyConstants.CLICK_KEY_MARK;
		}else if(ShortcutKeyConstants.mouseClickStop.equals(name)){
			result = HotkeyConstants.STOP_CLICK_KEY_MARK;
		}else if(ShortcutKeyConstants.keyRecord.equals(name)){
			result = HotkeyConstants.RECORD_KECODE_MARK;
		}else if(ShortcutKeyConstants.keyClickStart.equals(name)){
			result = HotkeyConstants.START_KECODE_MARK;
		}else if(ShortcutKeyConstants.keyClickStop.equals(name)){
			result = HotkeyConstants.STOP_KECODE_MARK;
		}else if(ShortcutKeyConstants.hide.equals(name)){
			result = HotkeyConstants.HIDE_KEY_MARK;
		}else if(ShortcutKeyConstants.exit.equals(name)){
			result = HotkeyConstants.EXIT_KEY_MARK;
		}else if(ShortcutKeyConstants.move.equals(name)){
			result = HotkeyConstants.ISMOVE_KEY_MARK;
		}else if(ShortcutKeyConstants.getXY.equals(name)){
			result = HotkeyConstants.POINT_KEY_MARK;
		}else if(ShortcutKeyConstants.getScreenImage.equals(name)){
			result = HotkeyConstants.SCREEN_KEY_MARK;
		}else if(ShortcutKeyConstants.getColor.equals(name)){
			result = HotkeyConstants.MOUSE_POINT_KEY_MARK;
		}else if(ShortcutKeyConstants.translate.equals(name)){
			result = HotkeyConstants.INPUTFIELD_KEY_MARK;
		}
		
		return result;
	}
	

}
