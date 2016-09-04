package com.skjanyou.recycle.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.RenderingHints;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.dnd.DropTarget;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JWindow;

import com.skjanyou.recycle.plugins.PluginsExecutor;
import com.skjanyou.recycle.pojo.Config;
import com.skjanyou.recycle.pojo.Parameter;
import com.skjanyou.recycle.pojo.PluginsEntry;
import com.skjanyou.utils.ImageUtil;
import com.sun.awt.AWTUtilities;

@SuppressWarnings("serial")
public class RecycleWindow extends JWindow{
	private float opactity = 0.9f;
	public transient boolean isMove = true;       //�����¼�����ʹ�ã�����������ʱ�������ƶ�,��isMoveForWindowΪfalse�Ǹ�ֵ����Ϊ�ζ������ƶ�
	public transient boolean isMoveForWindow = true; //�Ƿ�����ƶ�
	private transient Image img;
	private JPanel contentJp = new JPanel(){
		public void paint(Graphics g) {
			if(img != null){
				int bold = 5; 
				//����5��Ϊ����ݴ���
				((Graphics2D)g).setStroke(new BasicStroke(bold, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));   
				((Graphics2D)g).setStroke(new BasicStroke(bold, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND));   
				((Graphics2D)g).setStroke(new BasicStroke(bold));   
				((Graphics2D)g).setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);   
				((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				((Graphics2D)g).drawImage(img,-1,0,null);
			}
		}
	};
	public RecycleWindow(){
		try {
			initView();
		} catch (Exception e) {
			e.printStackTrace();
		}
		initListener();
		AWTUtilities.setWindowOpacity(this, opactity);
		AWTUtilities.setWindowOpaque(this, false);
	}
	
	private void initView() throws Exception{
		this.setContentPane(contentJp);
		if(SystemTray.isSupported()){
			SystemTray st = SystemTray.getSystemTray();
			TrayIcon ti = new TrayIcon(ImageUtil.drawTranslucentStringPic(18, 13, new Font("����",10,12), "Ҷ",Color.RED), "YOKO33");
			st.add(ti);
			PopupMenu jm = new PopupMenu();
			PopupMenu plugins = new PopupMenu("���");
			MenuItem exit = new MenuItem("�˳�");
			MenuItem set = new MenuItem("����");
			MenuItem show = new MenuItem("��ʾ/����");
			for(final PluginsEntry pe : Config.pluginsEntryList){
				MenuItem mi = new MenuItem(pe.getName());
				mi.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						PluginsExecutor.Execut(pe);
					}
				});
				plugins.add(mi);
			}
			exit.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			});
			set.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(!Parameter.optionDialog.isVisible()){
						Parameter.optionDialog.setVisible(true);	
					}else{
						Parameter.optionDialog.setVisible(false);
					}
				}
			});
			show.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(!Parameter.window.isVisible()){
						Parameter.window.setVisible(true);
					}else{
						Parameter.window.setVisible(false);
					}
				}
			});
			jm.add(plugins);
			jm.add(show);jm.add(set);jm.add(exit);
			ti.setPopupMenu(jm);
		}
	}

	private void initListener(){
		this.addMouseListener(Parameter.combineListener );
		this.addMouseMotionListener(Parameter.combineListener);
		this.addKeyListener(Parameter.combineListener);
		new DropTarget(this, Parameter.combineListener);
	}
	
	
	public float getOpactity() {
		return opactity;
	}
	public void setOpactity(float opactity) {
		this.opactity = opactity;
	}
	public Image getImg() {
		return img;
	}
	public void setImg(Image img) {
		this.img = img;
	}
}
