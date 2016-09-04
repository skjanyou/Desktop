package com.skjanyou.recycle.function;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.SourceDataLine;

/**
 * AudioPlayer类的回调类
 * @author skjanyou
 *
 */
public interface AudioPlayerCallBack {
	public void start(SourceDataLine line,AudioInputStream ais,AudioFormat af);
	public void running(SourceDataLine line,AudioInputStream ais,AudioFormat af);
	public void progress(SourceDataLine line,AudioInputStream ais,AudioFormat af,long current,long count);
	public void end(SourceDataLine line,AudioInputStream ais,AudioFormat af);
}
