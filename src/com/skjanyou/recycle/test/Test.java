package com.skjanyou.recycle.test;


import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.SourceDataLine;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import com.skjanyou.recycle.core.AudioPlayer3;
import com.skjanyou.recycle.function.AudioPlayerCallBack;
import com.skjanyou.recycle.pojo.Action;
import com.skjanyou.recycle.pojo.ActionCollection;
import com.skjanyou.recycle.view.Barrage;
import com.skjanyou.recycle.view.FileView;
import com.skjanyou.recycle.view.FileView.FileButton;
import com.skjanyou.utils.Entry;
import com.skjanyou.utils.ImageFindUtil;
import com.skjanyou.utils.ImageUtil;
import com.skjanyou.utils.RobotUtil;



public class Test {
	
	public static void test1() throws Exception{
		List<ActionCollection> acs = new ArrayList<ActionCollection>();
		Document actions = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File("config/动作.xml"));
		Entry entry = new Entry(actions.getDocumentElement());
		for (final Entry list_entry : entry.selectChildren("动作合集")) {
			Map<String,String> list_map = list_entry.getAttributes();
			ActionCollection ac = new ActionCollection();
			ac.setName(list_map.get("名字"));
			ac.setId(list_map.get("编号"));
			ac.setInterval(Integer.parseInt(list_map.get("间隔").trim()));
			
			for (final Entry action_entry : list_entry.selectChildren("动作")) {
				Map<String,String> action_map = action_entry.getAttributes();
				Action action = new Action();
				action.setName(action_map.get("名字"));
				action.setType(action_map.get("运动"));
				action.setId(action_map.get("编号"));
				action.setInterval(Integer.parseInt(action_map.get("间隔").trim()));
				for(final Entry img_entry : action_entry.selectChildren("贴图")){
					Map<String,String>  img = img_entry.getAttributes();
					String path = img.get("路径");
					/******/
					Barrage b = new Barrage(Color.red,new Font("黑体",50,30),path);
					Random r = new Random();
					int i = r.nextInt(4);
					int d = 0;
					switch (i) {
					case 0:
						d = Barrage.DIRECTOR_DOWN;
						break;
					case 1:
						d = Barrage.DIRECTOR_LEFT;
						break;
					case 2:
						d = Barrage.DIRECTOR_RIGHT;
						break;
					case 3:
						d = Barrage.DIRECTOR_UP;
						break;
					default:
						break;
					}
					i = r.nextInt(100);
					b.setPoint(new Point(i * 10,i * 5));
					b.setDirector(d);
					b.start();
					/*****/
					action.getImagePath().add(path);
				}
				ac.getActions().add(action);
			}
			acs.add(ac);
		}
	}
	
	public static void test2(){
		Barrage b = new Barrage(Color.red,new Font("宋体",50,30),"这是一句测试的话看看颜色和");
//		b.setSize(500, 50);
//		b.setLocationRelativeTo(null);
//		b.setVisible(true);
		
		b.setPoint(new Point(800,75));
		b.setDirector(Barrage.DIRECTOR_DOWN);
		b.start();
	}
	
	public static void test3(){
//		SimpleMP3 mjw = new SimpleMP3();
//		mjw.setOnTop(true);
//		mjw.setLocationRelativeTo(null);
//		mjw.setVisible(true);
	}
	
	public static void test4(){
		FileView fv = new FileView(ImageUtil.getImage("images/mio.jpg"));
		fv.setDefaultCloseOperation(FileView.EXIT_ON_CLOSE);
		fv.setSize(1000, 700);
		fv.setUndecorated(true);
		fv.setLocationRelativeTo(null);
		fv.setVisible(true);
	}
	
	public static void test5() throws Exception{
		AudioPlayerCallBack callback = new AudioPlayerCallBack() {
			@Override
			public void start(SourceDataLine line, AudioInputStream ais, AudioFormat af) {
				System.out.println("开始播放音乐");
			}
			
			@Override
			public void running(SourceDataLine line, AudioInputStream ais,
					AudioFormat af) {
				
			}
			
			@Override
			public void progress(SourceDataLine line, AudioInputStream ais,
					AudioFormat af, long current, long count) {
				System.out.println("current=" + current + ",count=" + count + ",current/count=" + current/(count*1.0));
			}
			
			@Override
			public void end(SourceDataLine line, AudioInputStream ais, AudioFormat af) {
				System.out.println("音乐播放结束");				
			}
		};
		AudioPlayer3 ap = AudioPlayer3.createPlayer();
		ap.loadFile(new File("C:\\1.mp3")).play(callback);
//		Thread.currentThread().sleep(5000);
//		System.out.println("暂停");
//		ap.pause();
//		Thread.currentThread().sleep(5000);
//		System.out.println("启动");
//		ap.rePlay();
//		Thread.currentThread().sleep(5000);
//		System.out.println("结束");
//		ap.stop();
//		Thread.currentThread().sleep(5000);
//		System.out.println("再次启动");
//		ap.play(callback);
	}
	
	public static void test6() throws Exception{
		String keyImagePath = "c:/3.bmp";
		ImageFindUtil demo = new ImageFindUtil(keyImagePath);
//		ImageFindUtil demo = new ImageFindUtil(keyImagePath,new BufferedImage());
		List<Point> points = demo.findImage();
		System.out.println(points.get(0).getX() + "," + points.get(0).getY());
		System.out.println(points.get(1).getX() + "," + points.get(1).getY());
	}
	
	public static void test7() throws Exception{
//		XMLParseUtil.loadShortcutKey();
//		XMLParseUtil.saveShortcutKey(null);
	}
	
	public static void test8() throws Exception{
//		System.out.println(XMLParseUtil.loadPlugins());
	}
	
	public static void test9() throws Exception{
		RobotUtil rt = new RobotUtil();
		while(true){
			BufferedImage img1 = rt.createScreenCapture(0, 0, 500, 500);
			ImageFindUtil ifu = new ImageFindUtil("c:/3.bmp", img1);
			List<Point> list = ifu.findImage();
			if(list != null && list.size() == 2){
				Point p1 = list.get(0);
				Point p2 = list.get(1);
				rt.mouseMove((int)p1.getX(), (int)p1.getY());
				rt.mouseMove((int)p2.getX(), (int)p2.getY());
				Thread.sleep(1000);
			}
		}
	}
	
	public static void main(String[] args) throws Exception{
		test2();
	}
}
