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
 *  �����ƶ���JWindow
 */
public class MoveableJWindow extends JWindow {
	private static final long serialVersionUID = 4720316617766672775L;
	private MoveableJWindow view = this;
	/**�����϶�**/
	private Point pre_point = new Point();  //������¼����ƶ�ǰ���������
	private Point end_point = new Point();  //������¼����ƶ�����������
	private boolean isPressed = true;       //�����¼�����ʹ�ã�����������ʱ�������ƶ�
	private boolean isMove = true;        //�Ƿ�����ƶ���������Ϊfalseʱ�����岻���ƶ�
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
