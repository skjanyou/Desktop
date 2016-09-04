package com.skjanyou.recycle.core;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

import com.skjanyou.recycle.function.AudioPlayerCallBack;

/**
 * 音频播放类
 * @author skjanyou
 *
 */
public class AudioPlayer implements Runnable,Closeable{
	private SourceDataLine line = null;
	private AudioInputStream ais = null;
	private AudioFormat af = null;
	private AudioFileFormat aff = null;                    //这个对象可以获得音频文件的信息
	private DataLine.Info info = null;
	private File file = null;
	private static AudioPlayer player = new AudioPlayer();
	private long count = 0;                                   //音频长度,单位微秒
	private AudioPlayerCallBack callback = null;
	private Thread thread = null;
	private boolean flag = true;
	private AudioPlayer(){

	}
	public static AudioPlayer createPlayer(){
		return player;
	}
	public  AudioPlayer loadFile(File file) {
		try {
			this.file = file;
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
	
	@SuppressWarnings("rawtypes")
	public void init() throws Exception{
		ais = AudioSystem.getAudioInputStream(this.file);
		aff = AudioSystem.getAudioFileFormat(ais);
		af = aff.getFormat();
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
		Map props = aff.properties();
		if(props.containsKey("duration")){
			count = (long)Math.round((Long)props.get("duration"));
		}
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
			byte[] buffer = new byte[5 * 1024];
			int bytesread = 0;
			while(bytesread >= 0){
				if(flag){
					bytesread = ais.read(buffer, 0, buffer.length);
					if(bytesread >= 0){
						line.write(buffer, 0, bytesread);
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
