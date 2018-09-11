package com.softwinner.un.tool.audio;

import com.softwinner.un.tool.service.UNService;
import com.softwinner.un.tool.util.UNLog;

/**
 * 文件名:AudioWrapper.java V0.5 May 5,2014<br>
 * 描　述:音频数据录制、播放类<br>
 * 版　权:珠海全志科技BU3-PD2<br>
 * @author PD2-赵井满、陈永煌、彭罗榕
 */
public class AudioWrapper {

	public static final String TAG = "AudioWrapper";
	
	private AudioRecorder audioRecorder;
//	private AudioReceiver audioReceiver;
	private AudioPlayer audioPlayer;

	private static AudioWrapper instanceAudioWrapper;

	private AudioWrapper() {
	}

	public static AudioWrapper getInstance() {
		if (null == instanceAudioWrapper) {
			instanceAudioWrapper = new AudioWrapper();
		}
		return instanceAudioWrapper;
	}

	public void startRecord(UNService service,int sid) {
		if (null == audioRecorder) {
			audioRecorder = new AudioRecorder(service,sid);
		}
		
		audioRecorder.startRecording();
	}

	public void stopRecord() {
		if (audioRecorder != null){
			audioRecorder.stopRecording();
		}
	}

	public void startListen() {
		if (null == audioPlayer) {
			audioPlayer = AudioPlayer.getInstance();
		}
		audioPlayer.startPlaying();
	}
	
	public void addData(byte[] rawData,int size){
		if (null == audioPlayer) {
			UNLog.debug_print(UNLog.LV_ERROR, TAG, "audioPlayer == null");
			return;
		}
		audioPlayer.addData(rawData, size);
	}
	
	public void stopListen(){
		if(audioPlayer != null){
			audioPlayer.stopPlaying();
		}
	}
}
