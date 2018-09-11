package com.softwinner.un.tool.audio;

import android.media.AudioFormat;
import android.media.MediaRecorder;

/**
 * 文件名:AudioConfig.java V0.5 May 5,2014<br>
 * 描　述:语音等相关设置信息<br>
 * 版　权:珠海全志科技BU3-PD2<br>
 * PD2-赵井满、陈永煌、彭罗榕
 */
public class AudioConfig {

	/**
	 * Recorder Configure
	 */
	public static final int SAMPLERATE = 8000;// 8KHZ
	public static final int PLAYER_CHANNEL_CONFIG = AudioFormat.CHANNEL_CONFIGURATION_MONO;
	public static final int AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT;

	/**
	 * Recorder Configure
	 */
	public static final int AUDIO_RESOURCE = MediaRecorder.AudioSource.MIC;
	public static final int RECORDER_CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_MONO;
	
}
