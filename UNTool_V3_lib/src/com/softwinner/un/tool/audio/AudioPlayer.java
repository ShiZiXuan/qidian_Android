package com.softwinner.un.tool.audio;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import com.softwinner.un.tool.util.UNLog;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;

/**
 * 文件名:AudioPlayer.java V0.5 May 5,2014<br>
 * 描　述:音频数据播放类<br>
 * 版　权:珠海全志科技BU3-PD2<br>
 * @author PD2-赵井满、陈永煌、彭罗榕
 */
public class AudioPlayer implements Runnable{
	private final String TAG = "AudioPlayer ";
	private static AudioPlayer player;

	private List<AudioData> dataList = null;
	private AudioData playData;
	private boolean isPlaying = false;

	private AudioTrack audioTrack;

	//
//	private File file;
//	private FileOutputStream fos;

	public AudioPlayer() {	
//		file = new File("/sdcard/debugout/decodepcm.pcm");
////		file = new File(Util.AUDIO_TMP_FILE);
//		try {
//			if (!file.exists())
//				try {
//					file.createNewFile();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			fos = new FileOutputStream(file);
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//		addUNHandler();
	}

	public static AudioPlayer getInstance() {
		if (player == null) {
			player = new AudioPlayer();
		}
		return player;
	}

	public void addData(byte[] rawData, int size) {
		AudioData decodedData = new AudioData();
		decodedData.setSize(size);
		byte[] tempData = new byte[size];
		System.arraycopy(rawData, 0, tempData, 0, size);
		decodedData.setRealData(tempData);
		if(dataList == null){
			UNLog.debug_print(UNLog.LV_DEBUG, TAG, "dataList == 空啦！~~~~~");
			dataList = Collections.synchronizedList(new LinkedList<AudioData>());
		}
		dataList.add(decodedData);
		UNLog.debug_print(UNLog.LV_DEBUG, TAG, "Player添加一次数据 " + dataList.size());
	}

	/*
	 * init Player parameters
	 */
	private boolean initAudioTrack() {
		dataList = Collections.synchronizedList(new LinkedList<AudioData>());
		int bufferSize = AudioRecord.getMinBufferSize(AudioConfig.SAMPLERATE,
				AudioFormat.CHANNEL_CONFIGURATION_MONO,
				AudioConfig.AUDIO_FORMAT);
		if (bufferSize < 0) {
			UNLog.debug_print(UNLog.LV_DEBUG, TAG, TAG + "initialize error!");
			return false;
		}
		UNLog.debug_print(UNLog.LV_DEBUG, TAG, "Player初始化的 buffersize是 " + bufferSize);
		audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
				AudioConfig.SAMPLERATE, AudioFormat.CHANNEL_CONFIGURATION_MONO,
				AudioConfig.AUDIO_FORMAT, bufferSize, AudioTrack.MODE_STREAM);
		// set volume:设置播放音量
		audioTrack.setStereoVolume(audioTrack.getMaxVolume(), audioTrack.getMaxVolume());

		audioTrack.play();
		
		return true;
	}

	private void playFromList() throws IOException {
		while (isPlaying) {
			//UNGlobal.debug_print(UNGlobal.LV_DEBUG, TAG, "*******************播放中......");
			while (dataList.size() > 0) {
				playData = dataList.remove(0);
				UNLog.debug_print(UNLog.LV_DEBUG, TAG, "播放一次数据 " + dataList.size());
				audioTrack.write(playData.getRealData(), 0, playData.getSize());
				if(!isPlaying){
					dataList.clear();
					dataList = null;
					break;
				}
			}
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
			}
		}
	}

	public void startPlaying() {
		if (isPlaying) {
			UNLog.debug_print(UNLog.LV_DEBUG, TAG, "验证播放器是否打开" + isPlaying);
			return;
		}
		new Thread(this).start();
	}

	public void run() {
		this.isPlaying = true;
		if (!initAudioTrack()) {
			UNLog.debug_print(UNLog.LV_DEBUG, TAG, "播放器初始化失败");
			return;
		}
		UNLog.debug_print(UNLog.LV_DEBUG, TAG, "开始播放");
		try {
			playFromList();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// while (isPlaying) {
		// if (dataList.size() > 0) {
		// playFromList();
		// } else {
		//
		// }
		// }
		if (this.audioTrack != null) {
			if (this.audioTrack.getPlayState() == AudioTrack.PLAYSTATE_PLAYING) {
				this.audioTrack.stop();
				this.audioTrack.release();
				this.audioTrack = null;
				UNLog.debug_print(UNLog.LV_DEBUG, TAG, TAG + "audioTrack release");
			}
		}
		UNLog.debug_print(UNLog.LV_DEBUG, TAG, TAG + "end playing");
	}

	public void stopPlaying() {
		this.isPlaying = false;
	}
	
}
