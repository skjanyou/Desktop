package com.skjanyou.recycle.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.ScrollPaneConstants;

import com.skjanyou.recycle.otherview.Initable;
import com.skjanyou.recycle.pojo.Config;
import com.skjanyou.recycle.pojo.Parameter;
import com.skjanyou.recycle.pojo.ServiceInstance;
import com.skjanyou.recycle.pojo.ShortcutKey;
import com.skjanyou.recycle.pojo.ShortcutKeyConstants;
import com.skjanyou.recycle.utils.XMLParseUtil;
import com.skjanyou.recycle.view.KeySetView.MapItem;
import com.skjanyou.utils.SwingUtil;

/**
 * 
 * @author skjanyou
 * 全局热键设置面板
 */
public class ShortcutKeySetPanel extends JPanel implements Initable,ShortcutKeyConstants{
	private static final long serialVersionUID = "ShortcutKeySetPanel".hashCode();
	private Vector<MapItem> keyCode = MapItem.getMapItem(Config.shortcutKey);
	private JScrollPane jsp = new JScrollPane(this);

	//鼠标相关
	private JPanel jp_mouse = new JPanel();
	private JLabel lb_mouseRecord = new JLabel(mouseRecord);
	private JComboBox jcb_mouseRecord1 = new JComboBox(keyCode);
	private JComboBox jcb_mouseRecord2 = new JComboBox(keyCode);
	private JLabel lb_mouseClickStart = new JLabel(mouseClickStart);
	private JComboBox jcb_mouseClickStart1 = new JComboBox(keyCode);
	private JComboBox jcb_mouseClickStart2 = new JComboBox(keyCode);
	private JLabel lb_mouseClickStop = new JLabel(mouseClickStop);
	private JComboBox jcb_mouseClickStop1 = new JComboBox(keyCode);
	private JComboBox jcb_mouseClickStop2 = new JComboBox(keyCode);

	//键盘相关
	private JPanel jp_key = new JPanel();
	private JLabel lb_keyRecord = new JLabel(keyRecord);
	private JComboBox jcb_keyRecord1 = new JComboBox(keyCode);
	private JComboBox jcb_keyRecord2 = new JComboBox(keyCode);
	private JLabel lb_keyClickStart = new JLabel(keyClickStart);
	private JComboBox jcb_keyClickStart1 = new JComboBox(keyCode);
	private JComboBox jcb_keyClickStart2 = new JComboBox(keyCode);
	private JLabel lb_keyClickStop = new JLabel(keyClickStop);
	private JComboBox jcb_keyClickStop1 = new JComboBox(keyCode);
	private JComboBox jcb_keyClickStop2 = new JComboBox(keyCode);

	//程序
	private JPanel jp_process = new JPanel();
	private JLabel lb_hide = new JLabel(hide);
	private JComboBox jcb_hide1 = new JComboBox(keyCode);
	private JComboBox jcb_hide2 = new JComboBox(keyCode);
	private JLabel lb_move = new JLabel(move);
	private JComboBox jcb_move1 = new JComboBox(keyCode);
	private JComboBox jcb_move2 = new JComboBox(keyCode);
	private JLabel lb_exit = new JLabel(exit);
	private JComboBox jcb_exit1 = new JComboBox(keyCode);
	private JComboBox jcb_exit2 = new JComboBox(keyCode);

	//其他
	private JPanel jp_other = new JPanel();
	private JLabel lb_getXY = new JLabel(getXY);
	private JComboBox jcb_getXY1 = new JComboBox(keyCode);
	private JComboBox jcb_getXY2 = new JComboBox(keyCode);
	private JLabel lb_getColor = new JLabel(getColor);
	private JComboBox jcb_getColor1 = new JComboBox(keyCode);
	private JComboBox jcb_getColor2 = new JComboBox(keyCode);
	private JLabel lb_getScreenImage = new JLabel(getScreenImage);
	private JComboBox jcb_getScreenImage1 = new JComboBox(keyCode);
	private JComboBox jcb_getScreenImage2 = new JComboBox(keyCode);
	private JLabel lb_translate = new JLabel(translate);
	private JComboBox jcb_translate1 = new JComboBox(keyCode);
	private JComboBox jcb_translate2 = new JComboBox(keyCode);

	//用于提交或者重置等
	private JPanel button_panel = new JPanel();
	private JLabel lb_tip = new JLabel("可以只设置单个键位");   //提示用
	private JButton jb_submit = new JButton("保存");
	private JButton jb_reset = new JButton("重置");
	private JToggleButton jtb = new JToggleButton("禁用/启用");


	public ShortcutKeySetPanel() {
		super();
		initView();
		initListener();
	}

	private void initView(){
		setLayout(new GridLayout(0,1));
		jsp.setAutoscrolls(true);
		jsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
		jp_mouse.setBorder(BorderFactory.createTitledBorder("鼠标"));
		jp_mouse.setLayout(null);

		jp_key.setBorder(BorderFactory.createTitledBorder("键盘"));
		jp_key.setLayout(null);

		jp_process.setBorder(BorderFactory.createTitledBorder("程序"));
		jp_process.setLayout(null);

		jp_other.setBorder(BorderFactory.createTitledBorder("其他"));
		jp_other.setLayout(null);

		button_panel.setLayout(null);
		lb_tip.setForeground(Color.RED);
		this.add(jp_mouse);
		this.add(jp_key);
		this.add(jp_process);
		this.add(jp_other);
		this.add(button_panel);
	}

	private void initData(){
		List<ShortcutKey> list = Config.outerShortcutKey;
		for (ShortcutKey key : list) {
			try {
				String name = key.getName();
				String keyCode1 = key.getKeyCode1();
				String keyCode2 = key.getKeyCode2();
				if(name.equalsIgnoreCase(ShortcutKeyConstants.mouseRecord)){
					setJCBValue(keyCode1,keyCode2,jcb_mouseRecord1,jcb_mouseRecord2);
				}else if(name.equalsIgnoreCase(ShortcutKeyConstants.mouseClickStart)){
					setJCBValue(keyCode1,keyCode2,jcb_mouseClickStart1,jcb_mouseClickStart2);
				}else if(name.equalsIgnoreCase(ShortcutKeyConstants.mouseClickStop)){
					setJCBValue(keyCode1,keyCode2,jcb_mouseClickStop1,jcb_mouseClickStop2);
				}else if(name.equalsIgnoreCase(ShortcutKeyConstants.keyRecord)){
					setJCBValue(keyCode1,keyCode2,jcb_keyRecord1,jcb_keyRecord2);
				}else if(name.equalsIgnoreCase(ShortcutKeyConstants.keyClickStart)){
					setJCBValue(keyCode1,keyCode2,jcb_keyClickStart1,jcb_keyClickStart2);
				}else if(name.equalsIgnoreCase(ShortcutKeyConstants.keyClickStop)){
					setJCBValue(keyCode1,keyCode2,jcb_keyClickStop1,jcb_keyClickStop2);
				}else if(name.equalsIgnoreCase(ShortcutKeyConstants.exit)){
					setJCBValue(keyCode1,keyCode2,jcb_exit1,jcb_exit2);
				}else if(name.equalsIgnoreCase(ShortcutKeyConstants.hide)){
					setJCBValue(keyCode1,keyCode2,jcb_hide1,jcb_hide2);
				}else if(name.equalsIgnoreCase(ShortcutKeyConstants.move)){
					setJCBValue(keyCode1,keyCode2,jcb_move1,jcb_move2);
				}else if(name.equalsIgnoreCase(ShortcutKeyConstants.getXY)){
					setJCBValue(keyCode1,keyCode2,jcb_getXY1,jcb_getXY2);
				}else if(name.equalsIgnoreCase(ShortcutKeyConstants.getColor)){
					setJCBValue(keyCode1,keyCode2,jcb_getColor1,jcb_getColor2);
				}else if(name.equalsIgnoreCase(ShortcutKeyConstants.getScreenImage)){
					setJCBValue(keyCode1,keyCode2,jcb_getScreenImage1,jcb_getScreenImage2);
				}else if(name.equalsIgnoreCase(ShortcutKeyConstants.translate)){
					setJCBValue(keyCode1,keyCode2,jcb_translate1,jcb_translate2);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}


	}

	private void initListener(){
		jb_reset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});

		jb_submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});

		jtb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});
	}
	
	private void save(){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				Config.outerShortcutKey.clear();
				
				Config.outerShortcutKey.add(new ShortcutKey(mouseRecord, jcb_mouseRecord1.getSelectedItem().toString(),  jcb_mouseRecord2.getSelectedItem().toString()));
				Config.outerShortcutKey.add(new ShortcutKey(mouseClickStart, jcb_mouseClickStart1.getSelectedItem().toString(),  jcb_mouseClickStart2.getSelectedItem().toString()));
				Config.outerShortcutKey.add(new ShortcutKey(mouseClickStop, jcb_mouseClickStop1.getSelectedItem().toString(), jcb_mouseClickStop2.getSelectedItem().toString()));
				
				Config.outerShortcutKey.add(new ShortcutKey(keyRecord, jcb_keyRecord1.getSelectedItem().toString(), jcb_keyRecord2.getSelectedItem().toString()));
				Config.outerShortcutKey.add(new ShortcutKey(keyClickStart, jcb_keyClickStart1.getSelectedItem().toString(), jcb_keyClickStart2.getSelectedItem().toString()));
				Config.outerShortcutKey.add(new ShortcutKey(keyClickStop, jcb_keyClickStop1.getSelectedItem().toString(), jcb_keyClickStop2.getSelectedItem().toString()));
				
				Config.outerShortcutKey.add(new ShortcutKey(exit, jcb_exit1.getSelectedItem().toString(), jcb_exit2.getSelectedItem().toString()));
				Config.outerShortcutKey.add(new ShortcutKey(hide, jcb_hide1.getSelectedItem().toString(), jcb_hide2.getSelectedItem().toString()));
				Config.outerShortcutKey.add(new ShortcutKey(move, jcb_move1.getSelectedItem().toString(), jcb_move2.getSelectedItem().toString()));
				
				Config.outerShortcutKey.add(new ShortcutKey(getXY, jcb_getXY1.getSelectedItem().toString(), jcb_getXY2.getSelectedItem().toString()));
				Config.outerShortcutKey.add(new ShortcutKey(getColor, jcb_getColor1.getSelectedItem().toString(), jcb_getColor2.getSelectedItem().toString()));
				Config.outerShortcutKey.add(new ShortcutKey(getScreenImage, jcb_getScreenImage1.getSelectedItem().toString(), jcb_getScreenImage2.getSelectedItem().toString()));
				Config.outerShortcutKey.add(new ShortcutKey(translate, jcb_translate1.getSelectedItem().toString(), jcb_translate2.getSelectedItem().toString()));
				try {
					XMLParseUtil.saveShortcutKey(Config.outerShortcutKey);
					lb_tip.setText("保存成功");
					ServiceInstance.hotkey.removeHotKeyListener(Parameter.hotkeyListener);
					Config.outerShortcutKey = ServiceInstance.cs.loadOuterShortcutKey();
					ServiceInstance.hotkey.registerDefaultHotKey();
				} catch (Exception e) {
					lb_tip.setText("保存失败");
				}
			}
		}).start();
	
	}


	@SuppressWarnings("static-access")
	@Override
	public void init() {
		//鼠标
		SwingUtil.addToCompontents(
				new Component[]{lb_mouseRecord,jcb_mouseRecord1,new JLabel("+"),jcb_mouseRecord2
				}, jp_mouse, 20, 10, 30,70,25).addToCompontents(
				new Component[]{lb_mouseClickStart,jcb_mouseClickStart1,new JLabel("+"),jcb_mouseClickStart2
				}, jp_mouse, 350, 10, 30,70,25).addToCompontents(
				new Component[]{lb_mouseClickStop,jcb_mouseClickStop1,new JLabel("+"),jcb_mouseClickStop2
				}, jp_mouse, 20, 50, 30,70,25);
		//键盘
		SwingUtil.addToCompontents(
				new Component[]{lb_keyRecord,jcb_keyRecord1,new JLabel("+"),jcb_keyRecord2
				}, jp_key, 20, 10, 30,70,25).addToCompontents(
				new Component[]{lb_keyClickStart,jcb_keyClickStart1,new JLabel("+"),jcb_keyClickStart2
				}, jp_key, 350, 10, 30,70,25).addToCompontents(
				new Component[]{lb_keyClickStop,jcb_keyClickStop1,new JLabel("+"),jcb_keyClickStop2
				}, jp_key, 20, 50, 30,70,25);
		//程序
		SwingUtil.addToCompontents(
				new Component[]{lb_exit,jcb_exit1,new JLabel("+"),jcb_exit2
				}, jp_process, 20, 10, 30,70,25).addToCompontents(
				new Component[]{lb_hide,jcb_hide1,new JLabel("+"),jcb_hide2
				}, jp_process, 350, 10, 30,70,25).addToCompontents(
				new Component[]{lb_move,jcb_move1,new JLabel("+"),jcb_move2
				}, jp_process, 20, 50, 30,70,25);
		//其他
		SwingUtil.addToCompontents(
				new Component[]{lb_getXY,jcb_getXY1,new JLabel("+"),jcb_getXY2
				}, jp_other, 20, 10, 30,70,25).addToCompontents(
				new Component[]{lb_getColor,jcb_getColor1,new JLabel("+"),jcb_getColor2
				}, jp_other, 350, 10, 30,70,25).addToCompontents(
				new Component[]{lb_getScreenImage,jcb_getScreenImage1,new JLabel("+"),jcb_getScreenImage2
				}, jp_other, 20, 50, 30,70,25).addToCompontents(
				new Component[]{lb_translate,jcb_translate1,new JLabel("+"),jcb_translate2
				}, jp_other, 350, 50, 30,70,25);
		//用于提交或者重置等
		SwingUtil.addToCompontents(
				new Component[]{jb_submit,jb_reset,jtb
				}, button_panel, 70, 30, 30,150,25).addToCompontents(
				new Component[]{lb_tip
				}, button_panel, 270, 0, 30,150,25);   
		initData();

	}
	private void setJCBValue(String keyCode1,String keyCode2,JComboBox jcb_1,JComboBox jcb_2){
		jcb_1.setSelectedItem(new MapItem(keyCode1, keyCode1));
		jcb_2.setSelectedItem(new MapItem(keyCode2, keyCode2));
	}

}
