package com.softwinner.un.tool.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import com.softwinner.un.tool.audio.AudioWrapper;
import com.softwinner.un.tool.domain.IOCtrlMessage;
import com.softwinner.un.tool.domain.IOCtrlReturnMsg;
import com.softwinner.un.tool.util.UNIOCtrlDefs;
import com.softwinner.un.tool.util.UNJni;
import com.softwinner.un.tool.util.UNJni.UNServiceCallbackListener;
import com.softwinner.un.tool.util.UNLog;

public class UNService extends Service {

	private static final String TAG  = "UNService";
	public  static AudioWrapper  audioWrapper;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		UNLog.debug_print(UNLog.LV_DEBUG , TAG, "onCreate");
		super.onCreate();
		UNJni.jni_initNetClient();
		UNJni.setServiceCallbackListener(serviceCallbackListener);
	}
	
		
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		UNLog.debug_print(UNLog.LV_DEBUG, TAG, "Received start id " + startId + ": " + intent);
        return START_STICKY; // run until explicitly stopped.
	}
		
	@Override
	/**
	 * onbind 方法返回一个bindler
	 */
    public IBinder onBind(Intent intent) {
		UNLog.debug_print(UNLog.LV_DEBUG , TAG, "onBind");
	    //返回自己信使的bindler,以供客户端通过这个bindler得到服务端的信使对象（通过new Messenger(bindler)）  
        return unSerReceiver.getBinder();
    }
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		UNLog.debug_print(UNLog.LV_DEBUG, TAG, "Service Stopped");
	}

	
	public static final int SEND_REGISTER_CLIENT     	= 0;
	public static final int SEND_DEINIT_SERVICE       	= 1;
	public static final int SEND_SEARCH_DEVICE      	= 2;
	public static final int SEND_CONNECT_DEVICE      	= 3;
	public static final int SEND_DISCONNECT_DEVICE      = 4;
	public static final int SEND_SEND_IOCTRL_MSG     	= 5;
	public static final int SEND_START_VIDEO_STREAM  	= 6;
	public static final int SEND_STOP_VIDEO_STREAM   	= 7;
	public static final int SEND_START_SPEAKER       	= 8;
	public static final int SEND_STOP_SPEAKER        	= 9;
	public static final int SEND_START_AUDIO         	= 10;
	public static final int SEND_STOP_AUDIO          	= 11;
	
    private static Messenger mClient ;
    // Handler of incoming messages from clients.
    private Handler  mSerRecHandler = new Handler(){
    	 @Override
    	// UNService.SEND_REGISTER_CLIENT
         public void handleMessage(Message msg) {
    		 UNLog.debug_print(UNLog.LV_DEBUG, TAG, "handleMessage msg what = " + msg.what);
             switch (msg.what) {
             case SEND_REGISTER_CLIENT:
            	 UNLog.debug_print(UNLog.LV_DEBUG, TAG, " ---- CONTACT_REGISTER_CLIENT ----");
            	 if(msg.replyTo == null){
            		 UNLog.debug_print(UNLog.LV_DEBUG, TAG, "handleMessage msg what = " + msg.what + " replayto == null");
            	 }
            	 //把客户端的信使对象赋值给mClient
                 mClient = msg.replyTo;
                 break;
             case SEND_SEARCH_DEVICE:
            	 UNJni.jni_searchDevice();
            	 break;
             case SEND_CONNECT_DEVICE:
            	 connectDevice(msg.getData());
            	 break;
             case SEND_DISCONNECT_DEVICE:
            	 disConnectDevice(msg.arg1);
            	 break;
             case SEND_SEND_IOCTRL_MSG:
            	 sendIOCtrl((IOCtrlMessage)msg.obj);
            	 break;
             case SEND_START_VIDEO_STREAM:
            	 startVideoStream(msg.obj, msg.arg1);
            	 break;
             case SEND_STOP_VIDEO_STREAM:
            	 stopVideoStream(msg.arg1);
            	 break;
             case SEND_START_SPEAKER:
            	 startSpeaker(msg.arg1);
            	 break;
             case SEND_STOP_SPEAKER:
            	 stopSpeaker(msg.arg1);
            	 break;
             case SEND_START_AUDIO:
            	 startAudio(msg.arg1);
            	 break;
             case SEND_STOP_AUDIO:
            	 stopAudio(msg.arg1);
            	 break;
             case SEND_DEINIT_SERVICE:
            	 deInitService();
            	 break;
//            	public static final int CONTACT_EXIT_SERVICE  = 1;
             default:
            	 break;
             
             }
         }
    };
    
    /**
     * 自己的信使对象
     */
    private Messenger unSerReceiver = new Messenger(mSerRecHandler); 

    
    
    
    public static final int RESP_CALLBACK_MSG        = 0;
    public static final int RESP_DEINIT_SERVICE      = 1;
    
    private Handler  mHandler = new Handler(){
    	
    	public void handleMessage(Message msg) {
    		
    		switch (msg.what) {
    		case RESP_CALLBACK_MSG:
    			respCallbackMsg((IOCtrlReturnMsg)msg.obj);
    			break;
    		case RESP_DEINIT_SERVICE:
    			respDeinitService();
    			break;
			default:
				break;
			}
    		
    	};
    	
    };
    
    public void callbackMsgRtn(int sid, byte[] uid, int IOCTRLType,	byte[] data, int len) {
		// TODO Auto-generated method stub
		
		IOCtrlReturnMsg returnMsg ;
		Message rthdlMsg;
		returnMsg = new IOCtrlReturnMsg(sid, uid, IOCTRLType, data, len);
	    rthdlMsg = mSerRecHandler.obtainMessage();
	    rthdlMsg.obj  = returnMsg;
	    rthdlMsg.what = -1;
		switch (IOCTRLType) {
			case UNIOCtrlDefs.AW_IOTYPE_USER_IPCAM_AUDIODATA:
				if (audioWrapper == null) {
					UNLog.debug_print(UNLog.LV_ERROR, TAG, "------- audioWrapper == null --------");
				}else {
					audioWrapper.addData(data, len);
				}
			break;
			default:
					rthdlMsg.what = RESP_CALLBACK_MSG;
				break;
		}
		if (rthdlMsg.what != -1) {
			mSerRecHandler.sendMessage(rthdlMsg);
		}
	}
    
    private UNServiceCallbackListener serviceCallbackListener = new UNServiceCallbackListener() {
		
		@Override
		public void callbackMsgRtn(int sid, byte[] uid, int IOCTRLType,	byte[] data, int len) {
			// TODO Auto-generated method stub
			
			UNLog.debug_print(UNLog.LV_DEBUG, TAG, "serviceCallbackListener() callbackMsgRtn()");
			IOCtrlReturnMsg rtnMsg = new IOCtrlReturnMsg(sid, uid, IOCTRLType, data, len);
			Message msg = mHandler.obtainMessage();
			msg.what = RESP_CALLBACK_MSG;
			msg.obj  = rtnMsg;
			mHandler.sendMessage(msg);
			
		}
	}; 
    
    private void sendIOCtrl(IOCtrlMessage ioCtrlMessage){
    	
    	UNLog.debug_print(UNLog.LV_DEBUG, TAG, "sendIOCtrl");
    	
    	UNJni.jni_awSendIOCtrl(ioCtrlMessage.getSid(),
    			         ioCtrlMessage.getIOCtrlType(),
    			         ioCtrlMessage.getIOCtrlData(),
    			         ioCtrlMessage.getIOCtrlDataSize());
    	
    }
    
    private void connectDevice(Bundle bundle){
		UNLog.debug_print(UNLog.LV_DEBUG, TAG, "connectDevice()");
		String uid = bundle.getString(UNIOCtrlDefs.UID_TAG);
		String pwd = bundle.getString(UNIOCtrlDefs.PWD_TAG);
		
		if (null == uid || "".equals(uid)) {
			UNLog.debug_print(UNLog.LV_ERROR, TAG, "connectDevice() UID = NULL!");
			return;
		}
		
		if (null == pwd || "".equals(pwd)) {
			UNLog.debug_print(UNLog.LV_ERROR, TAG, "connectDevice() PWD = NULL!");
			return;
		}
		
    	int sid = UNJni.jni_connectDevice(uid, pwd);
//    	UNLog.debug_print(UNLog.LV_DEBUG, TAG, "connectDevice devices = " + device.getUid() + " sid = " + sid);
//    	device.setSid(sid);
//    	if (sid == -1) {
//			UNLog.debug_print(UNLog.LV_ERROR, TAG, "connectDevice devices name = " + device.getUid() + " sid = -1");
//		}else {
//			device.setState(UNDevice.STATE_CONNECTING);
//		}
    }
    
    private void disConnectDevice(int sid){
    	
    	UNLog.debug_print(UNLog.LV_DEBUG, TAG, "disConnectDevice() sid = " + sid);
    	UNJni.jni_disConnectDevice(sid);
    	
    }
    
    private void startVideoStream(final Object videoHelper, final int sid){
    	
    	UNLog.debug_print(UNLog.LV_DEBUG, TAG, "startVideoStream sid = " + sid);
    	
    	new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				UNJni.jni_startIpcamStream(videoHelper, sid);
			}
		}).run();
    	
    	
    }
    
    private void stopVideoStream(final int sid){
    	
    	UNLog.debug_print(UNLog.LV_DEBUG, TAG, "stopVideoStream sid = " + sid);
    	
    	new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				UNJni.jni_stopIpcamStream(sid);
				
			}
    	}).run();
    	
    	
    }

    public void release(){
        UNJni.setServiceCallbackListener(null);
        serviceCallbackListener = null;
        mHandler.removeCallbacksAndMessages(0);
        mHandler = null;
        mSerRecHandler.removeCallbacksAndMessages(0);
        mSerRecHandler = null;
        stopSelf();
    }
    
    private void deInitService(){
    	UNLog.debug_print(UNLog.LV_DEBUG, TAG, "deInitService()");


    	UNJni.jni_deInitNetClient();
    	
    	mHandler.sendEmptyMessage(RESP_DEINIT_SERVICE);
//		exitServiceResp();
    }
    
    private void respCallbackMsg(IOCtrlReturnMsg msg){
    	UNLog.debug_print(UNLog.LV_DEBUG, TAG, "respCallbackMsg()");
    	if (mClient == null) {
    		UNLog.debug_print(UNLog.LV_DEBUG, TAG, "respCallbackMsg unClient == null!");
			return;
		}
    	Message respMsg = Message.obtain(null, RESP_CALLBACK_MSG);
    	respMsg.obj     = msg;
    	try {
			mClient.send(respMsg);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    private void respDeinitService(){
    	
    	UNLog.debug_print(UNLog.LV_DEBUG, TAG, "respDeinitService()");
    	if (mClient == null) {
    		UNLog.debug_print(UNLog.LV_DEBUG, TAG, "respDeinitService unClient == null!");
			return;
		}
    	Message respMsg = Message.obtain(null, RESP_DEINIT_SERVICE);
    	try {
			mClient.send(respMsg);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	release();
    }
    
//----------------------speaker part----------------------------------
    
    private void startSpeaker(int sid){
    	int ret = UNJni.jni_startIpcamSpeeker(sid);
    	if (ret == 0) {
    		UNLog.debug_print(UNLog.LV_DEBUG, TAG, "-------- startSpeaker Success! --------");
			initMic(sid);
		}else {
			UNLog.debug_print(UNLog.LV_ERROR, TAG, "-------- startSpeaker Fail!    --------");
		}
    }
    
    private void initMic(int sid){
    	UNLog.debug_print(UNLog.LV_DEBUG, TAG, "initMic ");
    	if (audioWrapper == null) {
			audioWrapper = AudioWrapper.getInstance();
		}
    	audioWrapper.startRecord(UNService.this,sid);
    }
    
    private void stopSpeaker(int sid){
    	UNJni.jni_stopIpcamSpeeker(sid);
    	deinitMic();
    }
    
    private void stopSpeakerRtn(){
    	UNLog.debug_print(UNLog.LV_DEBUG, TAG, "stopSpeakerRtn ");
    }
    
    private void deinitMic(){
    	UNLog.debug_print(UNLog.LV_DEBUG, TAG, "deinitMic ");
    	if (audioWrapper != null) {
			audioWrapper.stopRecord();
		}
    	
    }
    
//----------------------audio part----------------------------------
    
    private void startAudio(int sid){
    	int ret = UNJni.jni_startIpcamAudio(sid);
    	if (ret == 0) {
    		UNLog.debug_print(UNLog.LV_DEBUG, TAG, "-------- StartAudio Success! --------");
			initSound();
		}else {
			UNLog.debug_print(UNLog.LV_ERROR, TAG, "-------- StartAudio Fail!    --------");
		}
    }
    
    private void initSound(){
    	UNLog.debug_print(UNLog.LV_DEBUG, TAG, "initSound ");
    	if (audioWrapper == null) {
			audioWrapper = AudioWrapper.getInstance();
		}
    	audioWrapper.startListen();
    }
    
    private void stopAudio(int sid){
    	UNJni.jni_stopIpcamAudio(sid);
    	deinitSound();
    }
    
    private void deinitSound(){
    	UNLog.debug_print(UNLog.LV_DEBUG, TAG, "deinitSound ");
    	if (audioWrapper != null) {
    		audioWrapper.stopListen();
		}
    }

	
}
