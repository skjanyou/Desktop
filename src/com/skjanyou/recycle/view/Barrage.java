package com.skjanyou.recycle.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.JWindow;

import sun.font.FontDesignMetrics;

import com.skjanyou.utils.ImageUtil;
import com.sun.awt.AWTUtilities;

/**弹幕**/
public class Barrage extends JWindow implements Runnable {
	private static final long serialVersionUID = "Barrage".hashCode();
	private Barrage barrage = this;
	public static final int DIRECTOR_LEFT = 1;
	public static final int DIRECTOR_RIGHT = 2;
	public static final int DIRECTOR_UP = 3;
	public static final int DIRECTOR_DOWN = 4;
	public static final int DIRECTOR_STOP = 5;

	private int director = DIRECTOR_STOP;     //方向
	private int step = 1;                     //移动的像素
	private boolean isStop = false;           //停止
	private Rectangle movebound = null;       //可移动的边界  
	
	private BufferedImage image = null;
	@SuppressWarnings("serial")
	private JPanel contendJp = new JPanel(){
		public void paint(java.awt.Graphics g) {
			super.paint(g);
			if(image != null){
				int bold = 5; 
				//以下5行为降锯齿代码
				((Graphics2D)g).setStroke(new BasicStroke(bold, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));   
				((Graphics2D)g).setStroke(new BasicStroke(bold, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND));   
				((Graphics2D)g).setStroke(new BasicStroke(bold));   
				((Graphics2D)g).setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);   
				((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				((Graphics2D)g).drawImage(image,0,0,null);
			}
		};
	};

	

	public Barrage(Color fontColor,Font font,String str){
		FontMetrics fm = FontDesignMetrics.getMetrics(font);
		int height = fm.getHeight();
		int width = fm.stringWidth(str);
		this.setLayout(new FlowLayout(FlowLayout.CENTER));
		contendJp.setBackground(Color.BLACK);
		this.setSize(width, height);
		AWTUtilities.setWindowOpaque(this, false);
		this.setContentPane(contendJp);
		this.getRootPane().setDoubleBuffered(true);
		BufferedImage img = ImageUtil.drawTranslucentStringPic(width,height,font,str,fontColor);
		this.image = img;
//		AWTUtilities.setWindowShape(this, ImageUtil.getImageShape(img));
//		this.getContentPane().setBackground(fontColor);
		//		AWTUtilities.setWindowOpacity(this, 0.1f);
		initListener();
	}

	
	private void initListener(){
		this.addMouseListener(new MouseAdapter() {
			Cursor hand_cursor = new Cursor(Cursor.HAND_CURSOR);
			Cursor default_cursor = new Cursor(Cursor.DEFAULT_CURSOR);
			
			@Override
			public void mouseEntered(MouseEvent e) {
				AWTUtilities.setWindowOpaque(barrage, true);
				AWTUtilities.setWindowOpacity(barrage, 0.5f);
				barrage.setCursor(hand_cursor);
				pause();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				AWTUtilities.setWindowOpaque(barrage, false);
				AWTUtilities.setWindowOpacity(barrage, 1f);
				start();
				barrage.setCursor(default_cursor);
			}
		});
	}
	
	
	@Override
	public void run() {
		while(!isStop){
			Point p = this.getLocation();
			switch (director) {
			case DIRECTOR_STOP:
				break;
			case DIRECTOR_LEFT:
				this.setLocation(new Point((int) (p.getX() - step), (int) p.getY()));
				break;
			case DIRECTOR_RIGHT:
				this.setLocation(new Point((int) (p.getX() + step), (int) p.getY()));
				break;
			case DIRECTOR_UP:
				this.setLocation(new Point((int) p.getX(), (int) (p.getY() - step)));
				break;
			case DIRECTOR_DOWN:
				this.setLocation(new Point((int) p.getX(), (int) (p.getY() + step)));
				break;
			default:
				break;
			}
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public int getDirector() {
		return director;
	}

	public void setDirector(int director) {
		this.director = director;
	}
	
	public void setPoint(Point p){
		this.setLocation(p);
	}

	public void start(){
		this.setVisible(true);
		new Thread(this).start();
		this.isStop = false;
	}
	public void pause(){
		this.isStop = true;
		
	}
	public void stop(){
		this.isStop = true;
		this.dispose();
	}

}
