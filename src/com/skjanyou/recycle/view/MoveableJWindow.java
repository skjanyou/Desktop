package com.skjanyou.recycle.view;

import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JWindow;
/***
 * 
 * @author skjanyou
 *  可以移动的JWindow
 */
public class MoveableJWindow extends JWindow {
	private static final long serialVersionUID = 4720316617766672775L;
	private MoveableJWindow view = this;
	/**窗口拖动**/
	private Point pre_point = new Point();  //用来记录鼠标移动前点击的坐标
	private Point end_point = new Point();  //用来记录鼠标移动后点击的坐标
	private boolean isPressed = true;       //用于事件监听使用，按下鼠标左键时，可以移动
	private boolean isMove = true;        //是否可以移动，当此项为false时，窗体不能移动
	public MoveableJWindow() {
		super();
		initListener();
	}
	public MoveableJWindow(Frame owner) {
		super(owner);
		initListener();
	}
	public MoveableJWindow(GraphicsConfiguration gc) {
		super(gc);
		initListener();
	}
	public MoveableJWindow(Window owner, GraphicsConfiguration gc) {
		super(owner, gc);
		initListener();
	}
	public MoveableJWindow(Window owner) {
		super(owner);
		initListener();
	}
	
	private void initListener(){
		MoveListener listener = new MoveListener();
		this.addMouseListener(listener);
		this.addMouseMotionListener(listener);
	}
	
	private class MoveListener  implements MouseMotionListener,MouseListener{

		@Override
		public void mouseDragged(MouseEvent e) {
			if(isPressed && isMove)
			{
				int x = (int)(view.getX() + e.getX() - pre_point.getX());
				int y = (int) (view.getY() + e.getY() - pre_point.getY());
				end_point = new Point(x,y);
				view.setLocation(end_point);
			}
		}

		@Override
		public void mouseMoved(MouseEvent e) {
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			isPressed = true;
			pre_point = new Point(e.getX(),e.getY());
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			isPressed = false;
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}
		
	}

	public boolean isMove() {
		return isMove;
	}
	public void setMove(boolean isMove) {
		this.isMove = isMove;
	}
	
}
