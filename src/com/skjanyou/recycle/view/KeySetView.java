package com.skjanyou.recycle.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import cn.com.tk.container.TK_JFrame;

import com.skjanyou.recycle.pojo.Config;
import com.skjanyou.recycle.pojo.Parameter;
import com.skjanyou.utils.SwingUtil;

public class KeySetView extends TK_JFrame {
	private static final long serialVersionUID = "SetView".hashCode();
	private KeySetView set = this;
	private JPanel top = new JPanel();
	private JPanel content = new JPanel();
	private JScrollPane jsp = new JScrollPane(content);
	private JButton jb_add = new JButton("添加一行");
	private JButton jb_delete = new JButton("删除所选");
	private JButton jb_all = new JButton("全选/反选");
	private JButton jb_submit = new JButton("确定");
	
	
	


	/**给设置窗口添加一行设置**/
	public static class LinePanel extends JPanel{
		private LinePanel lp = this;
		private int index = 1;                //行标
		private JLabel  lb_title = new JLabel("按键");
		private JComboBox jcb_keyCode = null;
		private JLabel lb_delay = new JLabel("频率(毫秒)");
		private JTextField delay = new JTextField("100");
		private JCheckBox jcb_selected = new JCheckBox("选定"){
			Color c = lp.getBackground();
			public void setSelected(boolean b) {
				if(b){
					lp.setBackground(Color.RED);
					lp.setToolTipText("选定后点击删除会删除该行！");
				}else{
					lp.setBackground(c);
					lp.setToolTipText(null);
				}
				super.setSelected(b);
			};
		};
		
		
		public LinePanel(Window w){
			setPreferredSize(new Dimension(w.getWidth(), 40));
			initView();
			initListener();
		}
		private void initView(){
			setLayout(null);
			Vector<MapItem> keyCode = MapItem.getMapItem(Config.keyCodeMap);
			jcb_keyCode = new JComboBox(keyCode);
			lb_title.setBounds(20, 0, 40, 40);
			jcb_keyCode.setBounds(51, 0, 150, 40);
			lb_delay.setBounds(212, 0, 80, 40);
			delay.setBounds(282, 0, 50, 40);
			jcb_selected.setBounds(343, 0, 80, 40);
			jcb_keyCode.setOpaque(true);
			jcb_selected.setOpaque(false);
			this.add(lb_title);
			this.add(jcb_keyCode);
			this.add(lb_delay);
			this.add(delay);
			this.add(jcb_selected);
		}
		private void initListener(){
			jcb_selected.addActionListener(new ActionListener() {
				Color c = lp.getBackground();
				@Override
				public void actionPerformed(ActionEvent e) {
					if(jcb_selected.isSelected()){
						lp.setBackground(Color.RED);
						lp.setToolTipText("选定后点击删除会删除该行！");
					}else{
						lp.setBackground(c);
						lp.setToolTipText(null);
					}
				}
			});
			delay.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent e) {
					String str = delay.getText();
					try{
						Integer.parseInt(str);
					}catch(Throwable te){
						delay.setText("100");
						JOptionPane.showMessageDialog(null, "请输入数字，单位毫秒");
					}
				}
			});
		}
		public JComboBox getJcb_keyCode() {
			return jcb_keyCode;
		}
		public void setJcb_keyCode(JComboBox jcb_keyCode) {
			this.jcb_keyCode = jcb_keyCode;
		}
		public JLabel getLb_title() {
			return lb_title;
		}
		public void setLb_title(JLabel lb_title) {
			this.lb_title = lb_title;
		}
		public JCheckBox getJcb_selected() {
			return jcb_selected;
		}
		public void setJcb_selected(JCheckBox jcb_selected) {
			this.jcb_selected = jcb_selected;
		}
		public JTextField getDelay() {
			return delay;
		}
		public void setDelay(JTextField delay) {
			this.delay = delay;
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
		@Override
		public int hashCode() {
			return key.hashCode();
		}
		@Override
		public boolean equals(Object obj) {
			if(!(obj instanceof MapItem)){return false;}
			MapItem mi = (MapItem) obj;
			if(this.key.equals(mi.getKey())){
				return true;
			}else{
				return false;
			}
			
		}
	}

	public KeySetView(String title) {
		super(title);
//		setLocationRelativeTo(null);
		SwingUtil.setCompontentsCenter(this,(float)Parameter.screen_width,(float)Parameter.screen_height);
		initView();
		initListener();
	}
	private void initView(){
		setSize(400, 700);
		setExitType(frame_hide);
		setLayout(new FlowLayout());
		top.setPreferredSize(new Dimension(this.getWidth(), 50));
		top.setBackground(Color.WHITE);
		top.setLayout(null);

		content.setBackground(Color.WHITE);
		content.setPreferredSize(new Dimension(this.getWidth() - 25, this.getHeight() - 105));
		jsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS  );
		jsp.setBounds(0, 51, this.getWidth(), 350);
		
		
		jb_add.setBounds(10, 0, 90, 50);
		jb_delete.setBounds(110, 0, 90, 50);
		jb_all.setBounds(210, 0, 90, 50);
		jb_submit.setBounds(310, 0, 90,50);
		top.add(jb_add);
		top.add(jb_delete);
		top.add(jb_all);
		top.add(jb_submit);
		this.add(top);
		this.add(jsp);
	}

	private void initListener(){
		jb_add.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(content != null){
					content.add(new LinePanel(set));
					content.validate();
				}
			}
		});
		jb_delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Component[] cs = content.getComponents();
				for (Component c : cs) {
					if(c instanceof LinePanel){
						LinePanel lp = (LinePanel) c;
						JCheckBox jcb_selected = lp.getJcb_selected();
						if(jcb_selected.isSelected()){
							content.remove(lp);
							content.validate();
							content.repaint();
						}
					}
				}
			}
		});
		jb_all.addActionListener(new ActionListener() {
			int i = 0;
			@Override
			public void actionPerformed(ActionEvent e) {
				Component[] cs = content.getComponents();
				for (Component c : cs) {
					if(c instanceof LinePanel){
						LinePanel lp = (LinePanel) c;
						JCheckBox jcb_selected = lp.getJcb_selected();
						if(i % 2 == 0){
							jcb_selected.setSelected(true);
						}else{
							jcb_selected.setSelected(false);
						}
						
					}
				}
				i++;
			}
		});
		jb_submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Config.keymap.clear();
				Component[] cs = content.getComponents();
				for (Component c : cs) {
					if(c instanceof LinePanel){
						LinePanel lp = (LinePanel) c;
						MapItem mi = (MapItem) lp.getJcb_keyCode().getSelectedItem();
						String str =lp.getDelay().getText().trim();
						int value = Integer.parseInt(str);
						Config.keymap.put(mi, value);
					}
				}
				set.setVisible(false);
			}
		});
	}
	
	
	
	public static void main(String[] args) {
		KeySetView set = new KeySetView("设置窗口");
		set.setExitType(KeySetView.frame_exit);
		set.setVisible(true);
	}
}
