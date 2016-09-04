package com.skjanyou.recycle.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JWindow;

import org.json.JSONObject;

import com.skjanyou.recycle.pojo.Config;
import com.skjanyou.recycle.pojo.Parameter;
import com.skjanyou.recycle.utils.TranslateUtil;
import com.skjanyou.utils.ClipboardUtil;
import com.skjanyou.utils.ImageUtil;
import com.skjanyou.utils.PropertiesUtil;

public class InputField extends JDialog {
	private static final long serialVersionUID = "InputField".hashCode();
	private InputField field = this;
	private InputField input = this;
	private TextField inputField;
	private String str;

	private JWindow languages;
	private String from = "zh";
	private String to = "en";

	public InputField(final String str){
		this.str = str;
		inputField = new TextField(str);
		inputField.setFont(new Font("隶书", 0, 40));
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
			String transtype = "realtime";
			String simple_means_flag = "3";
			String msg = "";   //用于显示的提示
			JSONObject json = null;
			Map<String,String> dict_result = null;
			Map<String,String> trans_result = null;
			@Override
			public void keyPressed(KeyEvent e) {
				if(inputField.getText().equals(str)){
					inputField.setText("");	
				}
				int keyCode = e.getKeyCode();
				if(keyCode == e.VK_ENTER){
					inputField.setEditable(false);
					inputField.setForeground(Color.RED);
					final String query = inputField.getText();
					//					try {
					//						dict_result = TranslateUtil.getDict_result(json);
					//					} catch (Exception e1) {
					////						e1.printStackTrace();
					//					}
					new Thread(new Runnable() {
						public void run() {
							try {
								from = TranslateUtil.getLangdetect(query).get("lan");
//								if(!"zh".equals(from)){
//									to = "zh";
//								}
								json = TranslateUtil.getJsonObject(from, to, query, transtype, simple_means_flag);
								trans_result = TranslateUtil.getTrans_result(json);
								String say1 =  trans_result.get("src") + "可以翻译为\"" + trans_result.get("dst") + "\"哦,结果已经复制到剪贴板了";
								msg = say1;
								ClipboardUtil.setSystemClipboard(trans_result.get("dst"));
							} catch (Exception e1) {
								msg = "可能出现了一些问题，稍候再试吧";
								//						e1.printStackTrace();
							}finally{
								Bubble bubble = new Bubble(ImageUtil.getImage("images/bubble/bubble1.png"), msg ,Parameter.window.getX(),Parameter.window.getY(), 4);
								new Thread(bubble).start();
								input.setVisible(false);
								inputField.setEditable(true);
								inputField.setForeground(new Color(95,195,229));
							}
						}
					}).start();
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
		languages = new LanJWindow();
		languages.setLocation(x, y);
		languages.setAlwaysOnTop(true);
	}
	
	
	@Override
	public void setVisible(boolean b) {
		if(languages != null){
			languages.setVisible(b);
		}
		super.setVisible(b);
	}

	private class LanJWindow extends JWindow{
		private LanJWindow ljw = this;
		private int max_width = 180;
		private int max_height = 450;
		private int height = 0;
		private int width = 0;
		private int size = 0;
		private int reX = 0;
		private int reY = 0;
		private boolean isFirst = true;
		private LanJButton current_lan = new LanJButton("英语","en");
		/**宽度：90，高度：语言数量*50**/
		public LanJWindow(){
			this.size = Config.languages.size();
			this.setMaximumSize(new Dimension(max_width, max_height));
			this.add(current_lan);
			this.setSize(90, 50);
//			this.setVisible(true);
		}
		
		public void resetEn(){
			this.setLocation(reX, reY);
			this.getContentPane().removeAll();
			this.setLayout(new BorderLayout());
			this.add(current_lan);
			this.setSize(90, 50);
			this.update(this.getGraphics());
		}
		
		public void resetCompontent(){
			this.getContentPane().remove(current_lan);
			int height = 50 * size;
			int width = 90;
			while(height > max_height){
				height -= max_height;
				width += 90;
			}
			this.setSize(width, height);
			int x = this.getX();
			int y = this.getY() - this.getHeight() + 50;
//			System.out.println(x + "," + y);
			this.setLocation(x, y);
			int rows = width/90;
			int cols = 0;
			if(rows > 1){
				cols = 9;
			}else{
				cols = height / 50;
			}

			this.setLayout(new GridLayout(cols,rows,0,0));

			Map<String,String> map = Config.languages;
			Set<String> set = map.keySet();
			for (String name : set) {
				LanJButton ljb = new LanJButton(name, map.get(name));
				this.getContentPane().add(ljb);
			}
			this.update(this.getGraphics());
		}

		@Override
		public void setLocation(int x, int y) {
			if(isFirst){
				this.reX = x;
				this.reY = y;
				isFirst = false;
			}
			super.setLocation(x, y);
		}
		
		
		private class LanJButton extends JButton implements MouseListener{
			private String name;
			private String lan;
			public LanJButton(String name,String lan){
				this.setText(name);
				this.name = name;
				this.lan = lan;
				this.setFont(new Font("宋体", 0, 13));
				this.getInsets().left = 0;
				this.getInsets().right = 0;
				this.setBackground(Color.white);
				this.addMouseListener(this);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}
			@Override
			public void mousePressed(MouseEvent e) {
				if((e.getModifiers() & InputEvent.BUTTON3_MASK) != 0 && ljw.getHeight() == 50){
					resetCompontent();
				}else if((e.getModifiers() & InputEvent.BUTTON1_MASK) != 0){
					current_lan = this;
					to = lan;
					resetEn();
				}
			}
			@Override
			public void mouseReleased(MouseEvent e) {

			}
			@Override
			public void mouseEntered(MouseEvent e) {

			}
			@Override
			public void mouseExited(MouseEvent e) {

			}
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

		InputField ifd = new InputField("请输入要翻译的字哦");
		ifd.setVisible(true);
	}

}
