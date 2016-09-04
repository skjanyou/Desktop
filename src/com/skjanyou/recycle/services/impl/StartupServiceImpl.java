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
		/************************************�����������********************************************/
		System.out.println("--��ʼ�������");
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
		System.out.println("--��������������,��ʱ��" + DateUtil.getFormatTime((loadConfig_end_time - loadConfig_start_time)));
		/*************************************�����������*****************************************/
		
		
		/******************************************2**************************************************/
		/************************************��ʼ��ȡ�����Ϣ********************************************/
		System.out.println("--��ʼ��ȡ�����Ϣ");
		long loadPlugins_start_time = DateUtil.getDate();
		Config.pluginsEntryList = ServiceInstance.cs.loadPluginsEntry();
		long loadPlugins_end_time = DateUtil.getDate();
		System.out.println("--��ȡ�����Ϣ���,��ʱ��" + DateUtil.getFormatTime((loadPlugins_end_time - loadPlugins_start_time)));
		/************************************��ȡ�����Ϣ���********************************************/
		
		/******************************************3**************************************************/
		/**************************���ڼ����ڲ�����ʼ��,˳��Ҫ��************************/
		System.out.println("--��ʼ���ڲ�����ʼ��");
		long initView_start_time = DateUtil.getDate();
		Parameter.screen_width = screen.getWidth();
		Parameter.screen_height = screen.getHeight();
		Parameter.taskHeight = taskHeight;
		Parameter.inputField = new InputField("����Ҫ����ĵ���Ŷ");
		//		Parameter.keyrecordField = new KeyRecordField("¼�밴��");           //�ѷ���
		Parameter.keySetView = new KeySetView("�����Զ���������");
		Parameter.combineListener = new CombineListener();
		Parameter.hotkeyListener = new MyHotkeyListener();
		long initView_end_time = DateUtil.getDate();
		System.out.println("--���ڲ�����ʼ�����,��ʱ��" + DateUtil.getFormatTime((initView_end_time - initView_start_time)));
		/***********************************************************************************/

		/**ȫ���ȼ���������������Parameter.listener��ʼ��֮�����ע�ᣬ����ƽ̨����(Windows)**/
		System.out.println("--ע��ȫ�ּ�������");
		long registerHotKey_start_time = DateUtil.getDate();
		ServiceInstance.hotkey.registerDefaultHotKey();
		long registerHotKey_end_time = DateUtil.getDate();
		System.out.println("--ȫ�ּ�������ע�����,��ʱ��" + DateUtil.getFormatTime((registerHotKey_end_time - registerHotKey_start_time)));
		/**************************************************************************/

		/******************************************4**************************************************/
		/*****************************�����������ʼ������ʾ*************************************/
		System.out.println("--�����������ʼ������ʾ");
		long showView_start_time = DateUtil.getDate();
		RecycleWindow rw = new RecycleWindow();
		rw.setSize(128, 128);
		Parameter.window = rw;
		int x = (int) (screen.getWidth() - rw.getWidth());
		int y = (int) (screen.getHeight() - rw.getHeight() - taskHeight);
		//����ʼ��ǰ��
		rw.setAlwaysOnTop(true);
		rw.setLocation(x, y);
		rw.getContentPane().setBackground(Color.black);
		rw.setVisible(true);
		Parameter.optionDialog = new OptionDialog(new JFrame(),false);
		long showView_end_time = DateUtil.getDate();
		System.out.println("--�����������ʼ������ʾ���,��ʱ��" + DateUtil.getFormatTime((showView_end_time - showView_start_time)));
		/************************************************************************/


		/******************************************5**************************************************/
		/*******************************�����嶯��Ч������*****************************************/
		System.out.println("--���������嶯��Ч��");
		Animation animation = new Animation();
		ThreadInstance.animation = animation;
		animation.start();
		System.out.println("--�����嶯��Ч���������");
		/************************************************************************/
	}

	@Override
	public Thread end() {
		return null;
	}

}
