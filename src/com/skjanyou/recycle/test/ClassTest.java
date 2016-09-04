package com.skjanyou.recycle.test;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import javax.swing.JButton;
import javax.swing.UIManager;

import sun.awt.shell.ShellFolder;
import sun.awt.shell.ShellFolderColumnInfo;

import com.luohong.sis.SimilarImageSearch;
import com.skjanyou.utils.ImageUtil;



/**用于测试可能要使用到的类，以及方法，不会测试项目**/
public class ClassTest {

	public static void main(String[] args) throws Exception {
		test6();
	}
	@SuppressWarnings("static-access")
	public static void test1() throws Exception{
		String args[] = {"java","-jar","C:\\Windows\\Logs\\eclipse\\eclipse\\workspace\\recycle\\yoko.jar"};
		//		Process p = Runtime.getRuntime().exec("java -jar C:\\Windows\\Logs\\eclipse\\eclipse\\workspace\\recycle\\yoko.jar");
		Process p = Runtime.getRuntime().exec(args);
		Thread.currentThread().sleep(10000);
		//		p.destroy();
		System.out.println("System.exit(0)");
		System.exit(0);
	}
	public static void test2() throws Exception{
		ShellFolder shell = ShellFolder.getShellFolder(new File("C:\\Windows\\Logs\\eclipse\\eclipse\\eclipse\\eclipse.exe"));
		Image img = shell.getIcon(true);
		ImageUtil.saveImage((BufferedImage) img, "c:\\", "2.png");

		ShellFolderColumnInfo sc = new ShellFolderColumnInfo(null, 0, 0, false);
	}


	public static void test3() throws Exception{
		XMLEncoder e = new XMLEncoder(
				new BufferedOutputStream(
						new FileOutputStream("C:/Test.xml")));
		e.writeObject(new JButton("Hello, world"));
		e.close();
	}
	
	public static void test4() throws Exception{
//		AudioData ad = new AudioData(null);
//		AudioDevice ad = AudioDevice.device;
//		ad.openChannel(new FileInputStream(new File("C:\\Users\\Public\\Music\\Sample Music\\Sleep Away.mp3")));
//		ad.play();
//		ad.closeStreams();
//		AudioPlayer ap = AudioPlayer.player;
//		ap.start();
	}
	
	public static void test5() throws Exception{
//		Toolkit.getPCMConvertedAudioInputStream(null);
		AudioFileFormat aff = AudioSystem.getAudioFileFormat(new File("c:/1.mp3"));
		long total = 0;
		Map props = aff.properties();
		if(props.containsKey("duration")){
			total = (long)Math.round((Long)props.get("duration"));
		}
		System.out.println(total);
	}
	
	public static void test6() throws Exception{
		String str = UIManager.getSystemLookAndFeelClassName();
		System.out.println(str);
	}
	public static void test7() throws Exception{
		String pro1 = SimilarImageSearch.produceFingerPrint("c:/2.jpg");
		String pro2 = SimilarImageSearch.produceFingerPrint("c:/2_1.jpg");
		String pro3 = SimilarImageSearch.produceFingerPrint("c:/2_2.jpg");
		int i1 = SimilarImageSearch.hammingDistance(pro1, pro2);
		int i2 = SimilarImageSearch.hammingDistance(pro1, pro3);
		int i3 = SimilarImageSearch.hammingDistance(pro2, pro3);
		System.out.println(pro1 + "," + pro2 + "," + pro3);
		System.out.println(i1 + "," + i2 + "," + i3);
	}
	
	public static void test8() throws Exception{
		
	}
}

