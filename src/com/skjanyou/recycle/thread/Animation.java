package com.skjanyou.recycle.thread;

import java.awt.Image;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.skjanyou.recycle.function.AnimationCallBack;
import com.skjanyou.recycle.pojo.Action;
import com.skjanyou.recycle.pojo.Config;
import com.skjanyou.recycle.pojo.Parameter;
import com.skjanyou.recycle.pojo.ServiceInstance;
import com.skjanyou.recycle.services.ActionService;
import com.skjanyou.recycle.services.impl.ActionServiceImpl;
import com.skjanyou.recycle.utils.ImageCacheUtil;

/**动画效果**/
public class Animation extends TimerTask {
	private Timer timer = new Timer();
	private ActionService as = new ActionServiceImpl();
	private Action action = ServiceInstance.actionService.getActionByName("走路") ;
	private AnimationCallBack back;
	{
		//将TimeTask中的私有变量state值设置为true时可以重复使用
		try{
			Field field = TimerTask.class.getDeclaredField("state");
			field.setAccessible(true);
			field.set(this,0);
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
	//对于手动设定action
	public transient boolean auto = false;
	//是否已经完成
	public boolean isComplete = false;
	
	private DrawThread draw = null;
	
	public Animation(){
	}

	public void start(){
		timer.schedule(this, 1000,50);
	}

	@Override
	public boolean cancel() {
		timer.cancel();
		return super.cancel();
	}

	public void setAction(Action action,AnimationCallBack back){
		this.back = back;
		if(back != null){back.start();}
		this.action = action;
	}
	
	
	@SuppressWarnings("static-access")
	@Override
	public void run() {
//		System.out.println("control_thread");
		if(draw == null || draw.isComplete){
			int interval = Config.actionCollection.getInterval();
			if(auto){
				action =  as.getActionByRandom();
			}
			draw = new DrawThread(action,back);
			try {
				Thread.currentThread().sleep(interval);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
//			System.out.println(action.getName());
			draw.start();
		}
	}

	private  class DrawThread extends TimerTask{
		private Timer drawTimer = new Timer();
		
		private int interval;
		private String name;
		private String type;
		private List<String> images;
		private String id;

		private int img_size;
		private int img_index;
		
		private boolean isComplete;
		
		private AnimationCallBack back;

		public DrawThread(Action action,AnimationCallBack back){
			this.back = back;
			interval = action.getInterval();
			name = action.getName();
			type = action.getType();
			images = action.getImagePath();
			id = action.getId();

			img_size = images.size();
			img_index = 0;
		}
		@Override
		public void run() {
//			System.out.println("draw_thread");
			//表示全部都绘制完成，这个任务就可以结束了 
			if(img_index >= img_size){
				if(this.back != null){this.back.end();}
				isComplete = true;
				cancel();
				return;
			}
			Image img = ImageCacheUtil.getImage(images.get(img_index));
//			Parameter.imagePanel.setImg(img);
//			Parameter.imagePanel.paint(Parameter.imagePanel.getGraphics());
//			Parameter.imagePanel.updateUI();
			
			Parameter.window.setImg(img);
//			Parameter.window.paint(Parameter.window.getGraphics());
			Parameter.window.invalidate();
			Parameter.window.repaint();
			Parameter.window.setLocation(Parameter.window.getX() - 3, Parameter.window.getY());
			if(Parameter.window.getX() + Parameter.window.getWidth() <= 0){
				Parameter.window.setLocation((int) Parameter.screen_width, Parameter.window.getY());
			}
			img_index++;
		}
		
		public void start(){
			try{
				Field field = TimerTask.class.getDeclaredField("state");
				field.setAccessible(true);
				field.set(this,0);
			}catch(Exception e){
				throw new RuntimeException(e);
			}
			drawTimer.schedule(this, 0 ,interval);
		}

	}




	@SuppressWarnings("unused")
	private static class FetchdataThread extends TimerTask{
		@Override
		public void run() {

		}

	}

}
