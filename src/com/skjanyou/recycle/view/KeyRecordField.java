package com.skjanyou.recycle.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JDialog;
import javax.swing.JWindow;

import com.skjanyou.recycle.pojo.Config;
import com.skjanyou.recycle.pojo.Parameter;
import com.skjanyou.utils.PropertiesUtil;

public class KeyRecordField extends JDialog {
	private static final long serialVersionUID = "KeyRecordField".hashCode();
	private KeyRecordField field = this;
	private KeyRecordInputField inputField;
	private String str;


	public KeyRecordField(final String str){
		this.str = str;
		inputField = new KeyRecordInputField(str);
		inputField.setFont(new Font("Á¥Êé", 0, 40));
		inputField.setForeground(new Color(95,195,229));
		inputField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(inputField.getText().equals(str)){
					inputField.setText("");	
				}
			}
		});
		inputField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(inputField.getText().equals(str)){
					inputField.setText("");	
				}
				int keyCode = e.getKeyCode();
				if(keyCode == e.VK_ENTER){
					inputField.setEditable(false);
//					String string = inputField.getText();
//					Parameter.listener.keyCodes = field.getKeys(string);
					inputField.setEditable(true);
					field.setVisible(false);
				}
			}
		});
		getContentPane().setBackground(Color.white);
		setSize(400, 50);
		setLocation((int)(Parameter.screen_width - this.getWidth()) / 2, (int)(Parameter.screen_height - Parameter.taskHeight - this.getHeight()));
		
		this.add(inputField);
		this.setUndecorated(true);
		this.setAlwaysOnTop(true);
		int x,y;
		x = this.getX() + this.getWidth();
		y = this.getY();
	}

	@Override
	public void setVisible(boolean b) {
		if(inputField != null)inputField.setText("");
		super.setVisible(b);
	}
	
	public static class KeyRecordInputField extends TextField{
		private static final long serialVersionUID = "KeyRecordInputField".hashCode();

		public KeyRecordInputField() throws HeadlessException {
			super();
		}

		public KeyRecordInputField(int columns) throws HeadlessException {
			super(columns);
		}

		public KeyRecordInputField(String text, int columns)
				throws HeadlessException {
			super(text, columns);
		}

		public KeyRecordInputField(String text) throws HeadlessException {
			super(text);
		}
		
	}

	public static void main(String[] args) {
		/////*****////
		Config.languages = PropertiesUtil.loadProperties("config/lang.properties").getMap();
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		Insets taskSet = Toolkit.getDefaultToolkit().getScreenInsets((new JWindow()).getGraphicsConfiguration());
		int taskHeight = taskSet.bottom;
		Parameter.screen_width = screen.getWidth();
		Parameter.screen_height = screen.getHeight();
		Parameter.taskHeight = taskHeight;

		KeyRecordField ifd = new KeyRecordField("Â¼Èë°´¼ü");
		ifd.setVisible(true);
		
	}

}
