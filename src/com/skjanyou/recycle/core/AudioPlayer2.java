package com.skjanyou.recycle.core;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

import com.skjanyou.recycle.function.AudioPlayerCallBack;

/**≤ª π”√**/
public class AudioPlayer2 implements Runnable,Closeable{
	private static int PLAY = 0;
	private static int PAUSE = 1;
	private static int STOP = 2;
	
	private static AudioPlayer2 player = new AudioPlayer2();	
	private Clip clip = null;
	private AudioInputStream ais = null;
	private AudioFormat af = null;
	private File file = null;

	private AudioPlayerCallBack callback = null;
	private Thread thread = null;
	private boolean flag = true;
	private int positon = 0;
	private int length = 0;
	
	private int status = STOP;

	private AudioPlayer2(){

	}
	public static AudioPlayer2 createPlayer(){
		return player;
	}
	public  AudioPlayer2 loadFile(File file) {
		try {
			this.file = file;
			ais = AudioSystem.getAudioInputStream(file);
			af = ais.getFormat();
			if (af.getEncoding() != AudioFormat.Encoding.PCM_SIGNED) {
				AudioFormat newFormat = new AudioFormat(
						AudioFormat.Encoding.PCM_SIGNED, af.getSampleRate(), 16,
						af.getChannels(), af.getChannels() * 2, af.getSampleRate(),
						false);
				AudioInputStream newStream = AudioSystem.getAudioInputStream(
						newFormat, ais);
				af = newFormat;
				ais = newStream;
			}
			DataLine.Info info = new DataLine.Info(SourceDataLine.class, af);
			clip = AudioSystem.getClip();
			clip.open(ais);
			clip.start();
			this.length = (int) (clip.getMicrosecondLength() / 1000000);
			System.out.println(this.length);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
		}
		return this;
	}

	public void play(AudioPlayerCallBack callback){
		this.callback = callback;
		if(thread == null){
			thread = new Thread(this);
			thread.start();			
		}
		flag = true;
		positon = 0;
		status = PLAY;
	}
	public void rePlay(){
		flag = true;
		status = PLAY;
	}
	public void pause(){
		flag = false;
		status = PAUSE;
	}
	@SuppressWarnings("deprecation")
	public void stop(){
		if(thread != null){
			thread.stop();
			thread = null;
		}
	}


	@SuppressWarnings("static-access")
	@Override
	public void run() {
		while(true){
			System.out.println(positon);
			if(status == PLAY){
				clip.setMicrosecondPosition(positon * 1000000);
				while(flag){}
				positon = (int) (clip.getMicrosecondPosition() / 1000000);		
			}else if(status == PAUSE){
				clip.setMicrosecondPosition(length * 1000000);
			}else if(status == STOP){
				return ;
			}
		}
	}


	@Override
	public void close() throws IOException {
		if(ais != null){
			ais.close();	
		}
	}

}
