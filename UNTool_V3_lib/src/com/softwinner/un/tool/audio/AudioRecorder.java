package com.softwinner.un.tool.audio;

import com.softwinner.un.tool.service.UNService;
import com.softwinner.un.tool.util.UNJni;
import com.softwinner.un.tool.util.UNLog;

import android.media.AudioRecord;
/**
 * 文件名:AudioRecorder.java V0.5 May 5,2014<br>
 * 描　述:音频数据录制类<br>
 * 版　权:珠海全志科技BU3-PD2<br>
 * @author PD2-赵井满、陈永煌、彭罗榕
 */
public class AudioRecorder implements Runnable {

	private final String TAG = "AudioRecorder";

	private boolean isRecording = false;
	private AudioRecord audioRecord;

	private static final int BUFFER_FRAME_SIZE = 480;
	private int audioBufSize = 0;

	//
	private byte[] samples;// data
	// the size of audio read from recorder
	private int bufferRead = 0;

	
	private UNService service;
	private int       sid;
	

	public AudioRecorder(UNService service,int sid) {
		super();
		this.service = service;
		this.sid     = sid;
	}

	/*
	 * start recording
	 */
	public void startRecording() {
		int minBufferSize;
		minBufferSize = BUFFER_FRAME_SIZE;

		minBufferSize = AudioRecord.getMinBufferSize(AudioConfig.SAMPLERATE,
				AudioConfig.RECORDER_CHANNEL_CONFIG, AudioConfig.AUDIO_FORMAT);
		if (minBufferSize == AudioRecord.ERROR_BAD_VALUE) {
			UNLog.debug_print(UNLog.LV_DEBUG, TAG, "audioBufSize error");
			return;
		}

		if (minBufferSize < 2 * 320) {
			minBufferSize = 640;
		}

		// 初始化recorder
		if (null == audioRecord) {
			audioRecord = new AudioRecord(AudioConfig.AUDIO_RESOURCE,
					AudioConfig.SAMPLERATE,
					AudioConfig.RECORDER_CHANNEL_CONFIG,
					AudioConfig.AUDIO_FORMAT, minBufferSize);
		}

		audioBufSize = 320;		//aac frame count is 1024, amr use 640 for echo cancellation
		samples = new byte[audioBufSize];

		new Thread(this).start();
	}

	/*
	 * stop
	 */
	public void stopRecording() {
		this.isRecording = false;
	}

	public boolean isRecording() {
		return isRecording;
	}

	public void run() {
		// start encoder before recording
		
//		AudioEncoder encoder = AudioEncoder.getInstance();
//		encoder.startEncoding();
		UNLog.debug_print(UNLog.LV_DEBUG, TAG, "audioRecord startRecording()");
		audioRecord.startRecording();
		UNLog.debug_print(UNLog.LV_DEBUG, TAG, "start recording");
		this.isRecording = true;
		while (isRecording) {		
			bufferRead = audioRecord.read(samples, 0, audioBufSize);
			//UNGlobal.debug_print(UNGlobal.LV_DEBUG, TAG, "bufferRead is " + bufferRead);	
			UNLog.debug_print(UNLog.LV_DEBUG, TAG,  " bufferRead is " + bufferRead);
			if (bufferRead > 0) {
				// add data to encoder
//				encoder.addData(samples, bufferRead);
//				UNService_old.getUNServie().recorderAddData(samples);
//				UNJni.jni_awSendAudioData(this.sid, samples, samples.length, null, 0, null);
				
//				public native int jni_awSendAudioData(
//						int sid, 
//						byte[] cabAudioData,
//						int nAudioDataSize,
//						byte[] cabFrameInfo,
//						int nFrameInfoSize,
//						byte[] revert);
				
			}

			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		UNLog.debug_print(UNLog.LV_DEBUG, TAG, "end recording");
		audioRecord.stop();
//		encoder.stopEncoding();
	}
}
