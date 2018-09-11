package com.softwinner.un.tool.util;

public class UNJni {

	private boolean isLoadLibsOK = false;
	
	private static final String TAG = "UNJni";
	
	private static UNServiceCallbackListener serviceCallbackListener;
	
	public static void setServiceCallbackListener(
			UNServiceCallbackListener serviceCallbackListener) {
		UNJni.serviceCallbackListener = serviceCallbackListener;
	}

	static {
		
		UNLog.debug_print(UNLog.LV_DEBUG, TAG, "UNJni Load JNI...");
		
		try{
			System.loadLibrary("aw_net_client");
			System.loadLibrary("ipcamera");
			
		}catch(UnsatisfiedLinkError e){
			e.printStackTrace();
		}
		
	}
		
	/**
	 * 设备消息返回，回调到此处
	 * @param sid 链接标识
	 * @param uid     小机端的标识符
	 * @param IOCTRLType   信令类型
	 * @param data      信令内容
	 * @param len       信令长度
	 * @return
	 */
	public static int UNCallbackFunc(int sid, byte[] uid, int IOCTRLType, byte[] data, int len){
		UNLog.debug_print(UNLog.LV_DEBUG, TAG, "UNCallbackFunc IOCTRLType = " + IOCTRLType);
		if (null != serviceCallbackListener) {
			
			serviceCallbackListener.callbackMsgRtn(sid, uid, IOCTRLType, data, len);
		}else {
			
			UNLog.debug_print(UNLog.LV_DEBUG, TAG, "UNCallbackFunc  serviceCallbackListener == null!");
		}
		
		return 0;

	}
	
	public interface UNServiceCallbackListener{
		
		public void callbackMsgRtn(int sid, byte[] uid, int IOCTRLType, byte[] data, int len);
		
	}
	
	public native static int     jni_initNetClient();
	public native static int     jni_deInitNetClient();
	public native static int     jni_connectDevice(String UID, String password);
	public native static int     jni_disConnectDevice(int sid);
	public native static void    jni_getDevInfo(int sid);
	public native static int     jni_getNetInfo(int sid, byte[] NetInfo);
	public native static int     jni_startIpcamStream(Object winid, int sid);
	public native static int     jni_stopIpcamStream(int sid);
	public native static int     jni_startIpcamAudio(int sid);
	public native static int     jni_stopIpcamAudio(int sid);
	public native static int     jni_startIpcamSpeeker(int sid);
	public native static int     jni_stopIpcamSpeeker(int sid);
	public native static int     jni_startPlayBack(int sid, byte[] STimeDay);
	public native static int     jni_pausePlayBack(int sid);
	public native static int     jni_stopPlayBack(int sid);
	
	public native static int     jni_awSendVideoData( 
								byte[] cabFrameData, 
								int nFrameDataSize,
								byte[] cabFrameInfo,
								int nFrameInfoSize,
								byte[] revert);
	
	public native static int    jni_awSendAudioData(
								int sid, 
								byte[] cabAudioData,
								int nAudioDataSize,
								byte[] cabFrameInfo,
								int nFrameInfoSize,
								byte[] revert);
	
	public native static int     jni_awSendIOCtrl(
								int sid,
								int nIOCtrlType, 
								byte[] cabIOCtrlData,
								int nIOCtrlDataSize);
	
	public native static int     jni_searchDevice();
	
	
	public native static int jni_uploadFile(int sid, String filename);
	
}
