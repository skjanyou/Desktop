package com.skjanyou.recycle.listener;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

import com.skjanyou.recycle.function.AnimationCallBack;
import com.skjanyou.recycle.plugins.PluginsExecutor;
import com.skjanyou.recycle.pojo.Config;
import com.skjanyou.recycle.pojo.Parameter;
import com.skjanyou.recycle.pojo.PluginsEntry;
import com.skjanyou.recycle.pojo.ServiceInstance;
import com.skjanyou.recycle.pojo.ThreadInstance;
import com.skjanyou.recycle.view.Bubble;
import com.skjanyou.utils.ImageUtil;
import com.sun.awt.AWTUtilities;

/**
 * 将RecycleWindow需要的事件监听整合在一起
 * @author skjanyou
 * */
public class CombineListener implements MouseListener,MouseMotionListener,KeyListener,DropTargetListener{
	private Cursor cursor = null;
	private Point window_point;   //window的位置
	private Point pre_point = new Point();  //用来记录鼠标移动前点击的坐标
	private Point end_point = new Point();  //用来记录鼠标移动后点击的坐标
	private int mouse_x;    //当前鼠标的x坐标
	private int mouse_y;    //当前鼠标的y坐标
	
	
	private Bubble bubble;   //气泡提示窗口
	
	
	
	/***
	 * DropTargetListener
	 * 拖拽事件
	 * ****/
	@Override
	public void dragEnter(DropTargetDragEvent dtde) {
		
	}
	@Override
	public void dragOver(DropTargetDragEvent dtde) {
		
	}
	@Override
	public void dropActionChanged(DropTargetDragEvent dtde) {
		
	}
	@Override
	public void dragExit(DropTargetEvent dte) {
		
	}
	@Override
	public void drop(DropTargetDropEvent dtde) {
		dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
		Transferable tr = dtde.getTransferable();
		try {
			List list = (List) tr.getTransferData(DataFlavor.javaFileListFlavor);
			int size = list.size();
			for(int i = 0;i < size;i++){
				File f = (File) list.get(i);
				boolean b = f.delete();
			}
		} catch (UnsupportedFlavorException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	/***
	 * KeyListener
	 * 键盘事件
	 * ****/
	@Override
	public void keyTyped(KeyEvent e) {
		if (e.getKeyCode() == e.VK_X && e.isAltDown()){
			System.out.println("按下组合键");
		}
	}
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == e.VK_X && e.isAltDown()){
			System.out.println("按下组合键");
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
	}
	
	/***
	 * MouseListener
	 * 鼠标点击事件
	 * ****/
	@Override
	public void mouseClicked(MouseEvent e) {
		for (PluginsEntry pe : Config.pluginsEntryList) {
			PluginsExecutor.Execut(pe);
		}
	}
	@Override
	public void mousePressed(MouseEvent e) {
		if ((e.getModifiers() & InputEvent.BUTTON1_MASK) != 0) {
			if(e.getClickCount() == 1){
				Parameter.window.isMove = true;
				cursor = Parameter.window.getCursor();
				pre_point = new Point(e.getX(),e.getY());
				Parameter.window.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
			}else if(e.getClickCount() == 2){
				//双击
				ThreadInstance.animation.setAction(ServiceInstance.actionService.getActionByName("走路"),new AnimationCallBack() {
					@Override
					public void start() {
						ThreadInstance.animation.auto = false;
						ThreadInstance.animation.isComplete = false;
					}
					
					@Override
					public void end() {
//						ThreadInstance.animation.auto = true;
//						ThreadInstance.animation.isComplete = true;
//						ThreadInstance.animation.start();
					}
				});
			}
		}
		if ((e.getModifiers() & InputEvent.BUTTON2_MASK) != 0) {
			System.out.println("按下了中间键");
		}
		if ((e.getModifiers() & InputEvent.BUTTON3_MASK) != 0) {
			bubble = new Bubble(ImageUtil.getImage("images/bubble/bubble1.png"), "我要消失了哦" ,Parameter.window.getX(),Parameter.window.getY(),2);
			new Thread(bubble).start();
			//3s的时间让窗口消失
			new Thread(new Runnable() {
				@SuppressWarnings("static-access")
				@Override
				public void run() {
					int time = 30;
					float opcatity = Parameter.window.getOpactity();
					float sub_op = opcatity/30;
					while(time > 0 && opcatity > 0.02f){
						try {
							AWTUtilities.setWindowOpacity(Parameter.window, opcatity -= sub_op);
							Thread.currentThread().sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					Parameter.window.setVisible(false);
				}
			}).start();
		}
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		Parameter.window.isMove = false;
		Parameter.window.setCursor(cursor);
	}
	@Override
	public void mouseEntered(MouseEvent e) {
//		d = Parameter.window.getSize();
//		int width = (int) d.getWidth() + 4;
//		int height = (int) d.getHeight() + 4;
//		window_point = Parameter.window.getLocation();
//		end_point = new Point(Parameter.window.getX() - 2, Parameter.window.getY() - 2);
//		Parameter.window.setSize(new Dimension(width,height));
//		Parameter.window.setLocation(end_point);
	}
	@Override
	public void mouseExited(MouseEvent e) {
//		d = Parameter.window.getSize();
//		int width = (int) d.getWidth() - 4;
//		int height = (int) d.getHeight() - 4;
//		Parameter.window.setSize(new Dimension(width,height));
//		Parameter.window.setLocation(window_point);
	}
	
	
	/***
	 * MouseMotionListener
	 * 鼠标移动事件
	 * ****/
	@Override
	public void mouseDragged(MouseEvent e) {
		if(Parameter.window.isMove && Parameter.window.isMoveForWindow)
		{
			end_point = new Point((int)(Parameter.window.getX() + e.getX() - pre_point.getX()),(int)(Parameter.window.getY() + e.getY() - pre_point.getY()));
			Parameter.window.setLocation(end_point);
			window_point = end_point;
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
	}
	
	
}
