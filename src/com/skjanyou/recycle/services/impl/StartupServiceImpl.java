package com.skjanyou.recycle.services.impl;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JWindow;
import javax.swing.UIManager;

import com.skjanyou.recycle.listener.CombineListener;
import com.skjanyou.recycle.listener.MyHotkeyListener;
import com.skjanyou.recycle.otherview.OptionDialog;
import com.skjanyou.recycle.pojo.Config;
import com.skjanyou.recycle.pojo.Parameter;
import com.skjanyou.recycle.pojo.ServiceInstance;
import com.skjanyou.recycle.pojo.ThreadInstance;
import com.skjanyou.recycle.services.StartupService;
import com.skjanyou.recycle.thread.Animation;
import com.skjanyou.recycle.view.InputField;
import com.skjanyou.recycle.view.KeySetView;
import com.skjanyou.recycle.view.RecycleWindow;
import com.skjanyou.utils.DateUtil;

public class StartupServiceImpl implements StartupService {
	@Override
	public void start() {
		String clazz = UIManager.getSystemLookAndFeelClassName();
		try {
			UIManager.setLookAndFeel(clazz);
		} catch (Exception e) {
		} 
		//		ImagePanel imagePanel =  new ImagePanel();
		//		rw.getContentPane().add(imagePanel);
		//		Parameter.imagePanel = imagePanel;
		/******************************************1**************************************************/
		/************************************程序参数载入********************************************/
		System.out.println("--开始载入参数");
		long loadConfig_start_time = DateUtil.getDate();
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		Insets taskSet = Toolkit.getDefaultToolkit().getScreenInsets((new JWindow()).getGraphicsConfiguration());
		int taskHeight = taskSet.bottom;
		Config.actionCollection = ServiceInstance.cs.loadConfig();ServiceInstance.cs.loadImage(Config.actionCollection );
		Config.languages = ServiceInstance.cs.loadLanguages();
		Config.keyCodeMap = ServiceInstance.cs.loadKeyCodeMap();
		Config.shortcutKey = ServiceInstance.cs.loadShortcutKey();
		Config.outerShortcutKey = ServiceInstance.cs.loadOuterShortcutKey();
		long loadConfig_end_time = DateUtil.getDate();
		System.out.println("--程序参数载入完成,费时：" + DateUtil.getFormatTime((loadConfig_end_time - loadConfig_start_time)));
		/*************************************参数载入完成*****************************************/
		
		
		/******************************************2**************************************************/
		/************************************开始读取插件信息********************************************/
		System.out.println("--开始读取插件信息");
		long loadPlugins_start_time = DateUtil.getDate();
		Config.pluginsEntryList = ServiceInstance.cs.loadPluginsEntry();
		long loadPlugins_end_time = DateUtil.getDate();
		System.out.println("--读取插件信息完成,费时：" + DateUtil.getFormatTime((loadPlugins_end_time - loadPlugins_start_time)));
		/************************************读取插件信息完成********************************************/
		
		/******************************************3**************************************************/
		/**************************窗口及窗口参数初始化,顺序不要乱************************/
		System.out.println("--开始窗口参数初始化");
		long initView_start_time = DateUtil.getDate();
		Parameter.screen_width = screen.getWidth();
		Parameter.screen_height = screen.getHeight();
		Parameter.taskHeight = taskHeight;
		Parameter.inputField = new InputField("输入要翻译的单词哦");
		//		Parameter.keyrecordField = new KeyRecordField("录入按键");           //已废弃
		Parameter.keySetView = new KeySetView("设置自动连发按键");
		Parameter.combineListener = new CombineListener();
		Parameter.hotkeyListener = new MyHotkeyListener();
		long initView_end_time = DateUtil.getDate();
		System.out.println("--窗口参数初始化完成,费时：" + DateUtil.getFormatTime((initView_end_time - initView_start_time)));
		/***********************************************************************************/

		/**全局热键监听器，必须在Parameter.listener初始化之后才能注册，并且平台依赖(Windows)**/
		System.out.println("--注册全局监听按键");
		long registerHotKey_start_time = DateUtil.getDate();
		ServiceInstance.hotkey.registerDefaultHotKey();
		long registerHotKey_end_time = DateUtil.getDate();
		System.out.println("--全局监听按键注册完成,费时：" + DateUtil.getFormatTime((registerHotKey_end_time - registerHotKey_start_time)));
		/**************************************************************************/

		/******************************************4**************************************************/
		/*****************************主窗体参数初始化及显示*************************************/
		System.out.println("--主窗体参数初始化及显示");
		long showView_start_time = DateUtil.getDate();
		RecycleWindow rw = new RecycleWindow();
		rw.setSize(128, 128);
		Parameter.window = rw;
		int x = (int) (screen.getWidth() - rw.getWidth());
		int y = (int) (screen.getHeight() - rw.getHeight() - taskHeight);
		//窗口始终前置
		rw.setAlwaysOnTop(true);
		rw.setLocation(x, y);
		rw.getContentPane().setBackground(Color.black);
		rw.setVisible(true);
		Parameter.optionDialog = new OptionDialog(new JFrame(),false);
		long showView_end_time = DateUtil.getDate();
		System.out.println("--主窗体参数初始化及显示完成,费时：" + DateUtil.getFormatTime((showView_end_time - showView_start_time)));
		/************************************************************************/


		/******************************************5**************************************************/
		/*******************************主窗体动画效果开启*****************************************/
		System.out.println("--开启主窗体动画效果");
		Animation animation = new Animation();
		ThreadInstance.animation = animation;
		animation.start();
		System.out.println("--主窗体动画效果开启完成");
		/************************************************************************/
	}

	@Override
	public Thread end() {
		return null;
	}

}
