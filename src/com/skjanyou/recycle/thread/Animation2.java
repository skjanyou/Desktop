package com.skjanyou.recycle.thread;

import java.awt.Graphics;
import java.awt.Image;
import java.io.Serializable;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.skjanyou.recycle.pojo.Action;
import com.skjanyou.recycle.pojo.Config;
import com.skjanyou.recycle.services.AnimationService;
import com.skjanyou.recycle.services.impl.AnimationServiceImpl;
import com.skjanyou.recycle.utils.ImageCacheUtil;
import com.skjanyou.recycle.view.ImagePanel;



/***已废弃***/
public class Animation2 implements Serializable,Runnable{
	private static final long serialVersionUID = "Animation".hashCode();
    private ImagePanel panel = null;
    //以下两个是动作的数量以及当前动作
    private int action_size = 0;
    private int action_index = 0;
    private Action action = null;
    private List<String> imgPaths = null;
    //以下两个是一个动作中包含的图片总量以及当前页数
    private int img_size = 0;
    private int img_index = 0;
    //
    Image img = null;
    private Random rand = new Random();
    private AnimationService as = new AnimationServiceImpl();
    
    public Animation2(ImagePanel panel){
    	this.panel = panel;
    	action_size = Config.actionCollection.getActions().size();
    }
	
	@Override
	public void run() {
		Timer timer = new Timer();
		ImageTimerTask task = new ImageTimerTask();
		
		action_index = rand.nextInt(action_size);
		action = as.getAction(action_index);
		imgPaths = as.getImg(action);
		
		img_size = imgPaths.size();
		timer.schedule(task, 200,200);
	}


	private  class ImageTimer extends Timer{}
	private  class ImageTimerTask extends TimerTask{
		
		@Override
		public void run() {
			String  imgPath = imgPaths.get(img_index++);
			if(imgPath != null){
				img = ImageCacheUtil.getImage(imgPath);
				panel.setImg(img);
				Graphics g = panel.getGraphics();
				panel.paint(g);
				panel.updateUI();	
			}
			if(img_index >= img_size){
				action_index = rand.nextInt(action_size);
//				System.out.println(action_index);
				action = as.getAction(action_index);
				imgPaths = as.getImg(action);
				
				img_size = imgPaths.size();
				img_index = 0;
			}
		}
	}

}
