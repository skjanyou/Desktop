package com.skjanyou.recycle.core;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

import com.skjanyou.recycle.function.AudioPlayerCallBack;

/**不使用，仅用于尝试解决Clip.open()导致的OOM问题**/
public class AudioPlayer3 implements Runnable,Closeable{
	private SourceDataLine line = null;
	private AudioInputStream ais = null;
	private AudioFormat af = null;
	private DataLine.Info info = null;
	private File file = null;
	private static AudioPlayer3 player = new AudioPlayer3();

	private AudioPlayerCallBack callback = null;
	private Thread thread = null;
	private boolean flag = true;
	private AudioPlayer3(){

	}
	public static AudioPlayer3 createPlayer(){
		return player;
	}
	public  AudioPlayer3 loadFile(File file) {
		try {
			this.file = file;
//			init();
//			clip = AudioSystem.getClip();
//			clip.open(ais);
			init();
			line = (SourceDataLine) AudioSystem.getLine(info);
			line.open(af, line.getBufferSize());
			line.start();
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
		}
		return this;
	}
	
	public void init() throws Exception{
		ais = AudioSystem.getAudioInputStream(this.file);
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
		info = new DataLine.Info(SourceDataLine.class, af);
	}
	

	public void play(AudioPlayerCallBack callback){
		this.callback = callback;
		if(thread == null){
			//如果flag为false，则说明不是第一次启动，可能是调用stop方法关闭的，需要重新初始化数据
			if(!flag){
				try {
					init();
					line = (SourceDataLine) AudioSystem.getLine(info);
					line.open(af, line.getBufferSize());
					line.start();
				} catch (Exception e) {
					e.printStackTrace();
				} finally{
				}
			}
			thread = new Thread(this);
			thread.start();			
		}
		flag = true;
	}
	public void rePlay(){
		flag = true;
	}
	public void pause(){
		flag = false;
	}
	@SuppressWarnings("deprecation")
	public void stop(){
		if(thread != null){
			flag = false;
			thread.stop();
			thread = null;
		}
	}
	
	public void setPosition(){
		
	}
	
	@SuppressWarnings("static-access")
	@Override
	public void run() {
		if(callback!=null)callback.start(line, ais, af);
		try{
			long current = 0;
			long count = 0;
			byte[] buffer = new byte[5 * 1024];
			int bytesread = 0;
			while(bytesread >= 0){
				if(flag){
					bytesread = ais.read(buffer, 0, buffer.length);
					if(bytesread >= 0){
						line.write(buffer, 0, bytesread);
						current += bytesread;
					}
					if(callback!=null)callback.running(line, ais, af);
					if(callback!=null)callback.progress(line, ais, af, line.getMicrosecondPosition(),count);
				}else{
					Thread.currentThread().sleep(1000);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				this.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(callback!=null)callback.end(line, ais, af);
	}
	
	
	@Override
	public void close() throws IOException {
		if(line != null){
			line.close();			
		}
		if(ais != null){
			ais.close();	
		}
	}

}
