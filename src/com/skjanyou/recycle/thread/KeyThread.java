package com.skjanyou.recycle.thread;

import java.util.Timer;
import java.util.TimerTask;

import com.skjanyou.utils.RobotUtil;



public class KeyThread extends TimerTask {
	private final static int DEFAULT_PERIOD = 100;
	private final static int DEFAULT_DELAY = 100;
	
	private int keyCode;
	private int period;
	private int delay;
	private Timer timer = new Timer();
	private RobotUtil robot = new RobotUtil();
	
	public KeyThread(int keyCode,int period,int delay){
		if(period == 0) {this.period = DEFAULT_PERIOD;}else{this.period = period;};
		if(delay == 0) {this.delay = DEFAULT_DELAY;}else{this.delay = delay;};
		this.keyCode = keyCode;
	}
	
	public void start(){
//		System.out.println("period=" + this.period + ",delay=" + this.delay);
		timer.schedule(this, this.period,this.delay);
	}
	
	public void stop(){
		timer.cancel();
	}
	@Override
	public void run() {
		robot.keyClick(keyCode);
	}
}
