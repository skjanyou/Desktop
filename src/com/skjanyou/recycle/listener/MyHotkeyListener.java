package com.skjanyou.recycle.listener;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import com.melloware.jintellitype.HotkeyListener;
import com.skjanyou.recycle.pojo.Config;
import com.skjanyou.recycle.pojo.HotkeyConstants;
import com.skjanyou.recycle.pojo.Parameter;
import com.skjanyou.recycle.thread.KeyThread;
import com.skjanyou.recycle.view.Bubble;
import com.skjanyou.recycle.view.KeySetView.MapItem;
import com.skjanyou.utils.ClipboardUtil;
import com.skjanyou.utils.ImageUtil;
import com.skjanyou.utils.RobotUtil;
import com.sun.awt.AWTUtilities;

public class MyHotkeyListener implements HotkeyListener,HotkeyConstants{
	/**
	 * HotkeyListener全局热键
	 * @author skjanyou
	 * **/
	private int clickIsMoveCount = 1;  //通过该值的奇偶性来改变isMoveForWindows的值
	
	private RobotUtil robot = new RobotUtil();
	private Bubble bubble;   //气泡提示窗口
	
	private Timer timer_mouse = new Timer();
	private TimerTask task_mouse = new TimerTask() {
		@Override
		public void run() {
			robot.mouseMove(Parameter.mouse_x, Parameter.mouse_y).mousePress(InputEvent.BUTTON1_MASK).mouseRelease(InputEvent.BUTTON1_MASK);
		}
	};
	/*用于按键的*/
	public List<KeyThread> threadList = new ArrayList<KeyThread>();
	private boolean isStart = false;         //是否启动了按键线程
	
	@SuppressWarnings("static-access")
	@Override
	public void onHotKey(int markCode) {
		switch (markCode) {    
		case HIDE_KEY_MARK: 
			if(!Parameter.window.isVisible()){
				Parameter.window.setVisible(true);
				AWTUtilities.setWindowOpacity(Parameter.window, Parameter.window.getOpactity());
				bubble = new Bubble(ImageUtil.getImage("images/bubble/bubble1.png"), "我又出现了哦",Parameter.window.getX(),Parameter.window.getY(),1);
				new Thread(bubble).start();
			}else{
				Parameter.window.setVisible(false);
			}
			break;    
		case EXIT_KEY_MARK:    
			System.exit(0);  
			break;
		case MOUSE_POINT_KEY_MARK:
			Point point = MouseInfo.getPointerInfo().getLocation();
			String str = robot.getPixelColor((int)point.getX(), (int)point.getY());
			ClipboardUtil.setSystemClipboard(str);
			bubble = new Bubble(ImageUtil.getImage("images/bubble/bubble1.png"), "已经将文字\"" + str + "\"放到剪切板里面了,Ctrl+V可以直接使用" ,Parameter.window.getX(),Parameter.window.getY(),2);
			new Thread(bubble).start();
			break;
		case ISMOVE_KEY_MARK:
			//奇则将值设置为false，偶则将值设置为true
			if(clickIsMoveCount % 2 == 0){
				Parameter.window.isMoveForWindow = true;
			}else{
				Parameter.window.isMoveForWindow = false;
			}
			clickIsMoveCount++;
			break;
		case INPUTFIELD_KEY_MARK:
			if(Parameter.inputField.isVisible()){
				Parameter.inputField.setVisible(false);
			}else{
				Parameter.inputField.setVisible(true);
			}
			break;
		case SCREEN_KEY_MARK:
			BufferedImage img = robot.createScreenCapture(0,0,(int)Parameter.screen_width,(int)Parameter.screen_height);
			ClipboardUtil.setImageClipboard(img);
			bubble = new Bubble(ImageUtil.getImage("images/bubble/bubble1.png"), "已经将图片放到剪切板里面了,Ctrl+V可以直接使用" ,Parameter.window.getX(),Parameter.window.getY(),2);
			new Thread(bubble).start();
			break;
		case POINT_KEY_MARK:
			Point mouse_point = MouseInfo.getPointerInfo().getLocation();
			Parameter.mouse_x = (int) mouse_point.getX();
			Parameter.mouse_y = (int) mouse_point.getY();
			break;
		case CLICK_KEY_MARK:
			try {
				Field field = TimerTask.class.getDeclaredField("state");
				field.setAccessible(true);
				field.set(task_mouse,0);
				timer_mouse.schedule(task_mouse, 1000, 300);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case STOP_CLICK_KEY_MARK:
			task_mouse.cancel();
			break;
		case RECORD_KECODE_MARK:
			if(!Parameter.keySetView.isVisible()){
				isStart = false;
				for(KeyThread thread : threadList){
					thread.stop();
				}
				threadList.clear();
				Parameter.keySetView.setVisible(true);
			}else{
				Parameter.keySetView.setVisible(false);
			}
			break;
		case START_KECODE_MARK:
			if(!isStart && Config.keymap != null){
				Set<MapItem> set = Config.keymap.keySet();
				for (MapItem mapItem : set) {
					int keyCode = Integer.parseInt(mapItem.getValue().trim());
					int delay = Config.keymap.get(mapItem);
					KeyThread thread = new KeyThread(keyCode, 100, delay);
					threadList.add(thread);
					thread.start();
				}
				isStart = true;
			}
			break;
		case STOP_KECODE_MARK:
			System.out.println("--开始停止线程");
			for(KeyThread thread : threadList){
				thread.stop();
			}
			threadList.clear();
			System.out.println("--线程停止完成");
			break;
		}
	}

}
