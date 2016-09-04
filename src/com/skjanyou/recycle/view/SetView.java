package com.skjanyou.recycle.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Window;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import cn.com.tk.container.TK_JFrame;

import com.skjanyou.utils.PropertiesUtil;


public class SetView extends TK_JFrame {
	private static final long serialVersionUID = "SetView".hashCode();
	private JPanel top = new JPanel();
	private JPanel content = new JPanel();
	private JScrollPane jsp = new JScrollPane(content);
	private JButton jb_add = new JButton("添加一行");
	
	


	/**给设置窗口添加一行设置**/
	public static class LinePanel extends JPanel{
		private static Map<String,String> map = PropertiesUtil.loadProperties("config/keycode.properties").getMap();
		private int index = 1;                //行标
		private JLabel  lb_title = new JLabel("按键");
		private JComboBox jcb_keyCode = null;
		public LinePanel(Window w){
			setPreferredSize(new Dimension(w.getWidth(), 40));
			setLayout(null);
			Vector<MapItem> keyCode = MapItem.getMapItem(map);
			jcb_keyCode = new JComboBox(keyCode);
			lb_title.setBounds(30, 0, 60, 40);
			jcb_keyCode.setBounds(91, 0, 150, 40);
			this.add(lb_title);
			this.add(jcb_keyCode);
		}
		public JComboBox getJcb_keyCode() {
			return jcb_keyCode;
		}
		public void setJcb_keyCode(JComboBox jcb_keyCode) {
			this.jcb_keyCode = jcb_keyCode;
		}
//		@Override
//		protected void paintBorder(Graphics g) {
//			g.setColor(Color.RED);
//			g.drawRect(0, 0, this.getWidth(), this.getHeight());
//			super.paintBorder(g);
//		}

	}

	public static class MapItem {
		private String key;
		private String value;
		public MapItem(String key,String value){
			this.key = key;
			this.value = value;
		}

		public static Vector<MapItem> getMapItem(Map<String,String> map){
			Vector<MapItem> result = new Vector<MapItem>();
			Set<String> set =map.keySet();
			for (String key : set) {
				String value = map.get(key);
				result.add(new MapItem(key, value));
			}
			return result;
		}
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		@Override
		public String toString() {
			return key;
		}
	}

	public SetView(String title) {
		super(title);
		setSize(400, 700);
		setLayout(new FlowLayout());
		top.setPreferredSize(new Dimension(this.getWidth(), 50));
		top.setBackground(Color.WHITE);

		content.setBackground(Color.WHITE);
		content.setPreferredSize(new Dimension(this.getWidth() - 25, this.getHeight() - 105));
		jsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS  );
		jsp.setBounds(0, 51, this.getWidth(), 350);
		
		jb_add.setBounds(50, 0, 80, 50);
		top.add(jb_add);
		this.add(top);
		this.add(jsp);
		content.add(new LinePanel(this));
		content.add(new LinePanel(this));
		content.add(new LinePanel(this));
		content.add(new LinePanel(this));
		content.add(new LinePanel(this));

	}


	public static void main(String[] args) {
		SetView set = new SetView("设置窗口");
		set.setExitType(SetView.frame_exit);
		set.setLocationRelativeTo(null);
		set.setVisible(true);
	}
}
