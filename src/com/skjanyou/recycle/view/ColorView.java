package com.skjanyou.recycle.view;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JLabel;

import com.sun.awt.AWTUtilities;

public class ColorView extends MoveableJWindow{
	private static final long serialVersionUID = "ColorView".hashCode();
	private Color lb_c;
	private JLabel lb_background = new JLabel();
	private String string = null;
	private boolean isFrist = true;
	public ColorView(Color c){
		super();
		getContentPane().setBackground(c);
		this.setLayout(null);
	}
	public ColorView(Color c,Color lb_c){
		this(c);
		this.lb_c = lb_c;
	}
	/**
	 * 放在Setsize()之后
	 */
	public void init(){
		if(isFrist){
			lb_background.setOpaque(true);
			lb_background.setBackground(lb_c);
			lb_background.setBounds(0, 0,getWidth() , getHeight());
			this.add(lb_background);
			this.validate();
			isFrist = false;
		}
	}
	
	/***
	 *    设置百分比,在init()方法之后调用
	 * @param Percent 0-100之间的数
	 */
	public synchronized void setPercent(float percent){
		if(isFrist){
			System.out.println("还未初始化");
			return;
		}
		int height = getHeight();
		int loc_y = (int) (height * (100 - percent) / 100);
		lb_background.setLocation(0, loc_y);
	}
	
	public synchronized void setString(String string){
		this.string = string;
	}
	
	public void setOpacity(float opacity){
		AWTUtilities.setWindowOpacity(this, opacity);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if(string != null){
			g.drawString(string, 10, 25);
		}
	}
	public static void main(String[] args) {
		ColorView cv = new ColorView(Color.BLUE, Color.RED);
//		SwingUtil.setCompontentsCenter(cv);
		cv.setLocationRelativeTo(null);
		cv.setSize(40, 40);
		cv.init();
		cv.setString("测试");
		cv.setVisible(true);
	}
}
