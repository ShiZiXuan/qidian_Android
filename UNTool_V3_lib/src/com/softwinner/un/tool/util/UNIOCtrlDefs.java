package com.softwinner.un.tool.util;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * 文件名:UNIOCTLDEFs.java V2.0 Jan 20,2015<br>
 * 描　述:设备交互信令定义<br>
 * 版　权:珠海全志科技BU3-PD2<br>
 * @author PD2-彭罗榕、杨龙
 */
public class UNIOCtrlDefs {
	
	public static final String TAG     = "UNIOCTLDEFs";
	
	public static final String UID_TAG = "uid"; 
	public static final String PWD_TAG = "password";
	public static final int AW_IOTYPE_USER_IPCAM_POST_FILE_RESULT = 0x3021;
	
	/* AVAPIs IOCTRL Message Type */
	public static final int AW_IOTYPE_USER_IPCAM_START = 0x01FF;
	public static final int AW_IOTYPE_USER_IPCAM_STOP = 0x02FF;

	//SHA1校验固件文件
	public static final int NAT_CMD_CHECK_SHA1                     = 0xA065;
	public static final int NAT_CMD_CHECK_SHA1_RESP                = 0xA066;


	public static final int AW_IOTYPE_USER_IPCAM_AUDIOSTART = 0x0300;
	public static final int AW_IOTYPE_USER_IPCAM_AUDIOSTOP  = 0x0301;
	public static final int AW_IOTYPE_USER_IPCAM_AUDIODATA  = 0x0302;

	public static final int AW_IOTYPE_USER_IPCAM_SPEAKERSTART = 0x0350;
	public static final int AW_IOTYPE_USER_IPCAM_SPEAKERSTOP = 0x0351;

	public static final int AW_IOTYPE_USER_IPCAM_SETSTREAMCTRL_REQ = 0x0320;  // ok
	public static final int AW_IOTYPE_USER_IPCAM_SETSTREAMCTRL_RESP = 0x0321; // ok
	public static final int AW_IOTYPE_USER_IPCAM_GETSTREAMCTRL_REQ = 0x0322;  //ok
	public static final int AW_IOTYPE_USER_IPCAM_GETSTREAMCTRL_RESP = 0x0323; //ok

	public static final int AW_IOTYPE_USER_IPCAM_SETMOTIONDETECT_REQ = 0x0324;  //
	public static final int AW_IOTYPE_USER_IPCAM_SETMOTIONDETECT_RESP = 0x0325; //
	public static final int AW_IOTYPE_USER_IPCAM_GETMOTIONDETECT_REQ = 0x0326;  //
	public static final int AW_IOTYPE_USER_IPCAM_GETMOTIONDETECT_RESP = 0x0327; //

	public static final int AW_IOTYPE_USER_IPCAM_GETSUPPORTSTREAM_REQ = 0x0328; //
	public static final int AW_IOTYPE_USER_IPCAM_GETSUPPORTSTREAM_RESP = 0x0329; //

	public static final int AW_IOTYPE_USER_IPCAM_GETAUDIOOUTFORMAT_REQ = 0x032A; //
	public static final int AW_IOTYPE_USER_IPCAM_GETAUDIOOUTFORMAT_RESP = 0x032B; //

	public static final int AW_IOTYPE_USER_IPCAM_DEVINFO_REQ = 0x0330;  //
	public static final int AW_IOTYPE_USER_IPCAM_DEVINFO_RESP = 0x0331; //

	public static final int AW_IOTYPE_USER_IPCAM_SETPASSWORD_REQ = 0x0332; //
	public static final int AW_IOTYPE_USER_IPCAM_SETPASSWORD_RESP = 0x0333; //

	public static final int AW_IOTYPE_USER_IPCAM_LISTWIFIAP_REQ = 0x0340; //
	public static final int AW_IOTYPE_USER_IPCAM_LISTWIFIAP_RESP = 0x0341; //
	public static final int AW_IOTYPE_USER_IPCAM_SETWIFI_REQ = 0x0342;    //
	public static final int AW_IOTYPE_USER_IPCAM_SETWIFI_RESP = 0x0343;   //
	public static final int AW_IOTYPE_USER_IPCAM_GETWIFI_REQ = 0x0344; //
	public static final int AW_IOTYPE_USER_IPCAM_GETWIFI_RESP = 0x0345;//
	public static final int AW_IOTYPE_USER_IPCAM_SETWIFI_REQ_2 = 0x0346;
	public static final int AW_IOTYPE_USER_IPCAM_GETWIFI_RESP_2 = 0x0347;

	public static final int AW_IOTYPE_USER_IPCAM_SETRECORD_REQ = 0x0310; //
	public static final int AW_IOTYPE_USER_IPCAM_SETRECORD_RESP = 0x0311; //
	public static final int AW_IOTYPE_USER_IPCAM_GETRECORD_REQ = 0x0312; //
	public static final int AW_IOTYPE_USER_IPCAM_GETRECORD_RESP = 0x0313; //
 
	public static final int AW_IOTYPE_USER_IPCAM_SETRCD_DURATION_REQ = 0x0314; //
	public static final int AW_IOTYPE_USER_IPCAM_SETRCD_DURATION_RESP = 0x0315; //
	public static final int AW_IOTYPE_USER_IPCAM_GETRCD_DURATION_REQ = 0x0316; //
	public static final int AW_IOTYPE_USER_IPCAM_GETRCD_DURATION_RESP = 0x0317; //

	public static final int AW_IOTYPE_USER_IPCAM_LISTEVENT_REQ = 0x0318;//?
	public static final int AW_IOTYPE_USER_IPCAM_LISTEVENT_RESP = 0x0319;//

	public static final int AW_IOTYPE_USER_IPCAM_RECORD_PLAYCONTROL = 0x031A;//?
	public static final int AW_IOTYPE_USER_IPCAM_RECORD_PLAYCONTROL_RESP = 0x031B;//

	public static final int AW_IOTYPE_USER_IPCAM_GET_EVENTCONFIG_REQ = 0x0400;
	public static final int AW_IOTYPE_USER_IPCAM_GET_EVENTCONFIG_RESP = 0x0401;
	public static final int AW_IOTYPE_USER_IPCAM_SET_EVENTCONFIG_REQ = 0x0402;
	public static final int AW_IOTYPE_USER_IPCAM_SET_EVENTCONFIG_RESP = 0x0403;

	public static final int AW_IOTYPE_USER_IPCAM_SET_ENVIRONMENT_REQ = 0x0360;//
	public static final int AW_IOTYPE_USER_IPCAM_SET_ENVIRONMENT_RESP = 0x0361;//
	public static final int AW_IOTYPE_USER_IPCAM_GET_ENVIRONMENT_REQ = 0x0362;//
	public static final int AW_IOTYPE_USER_IPCAM_GET_ENVIRONMENT_RESP = 0x0363;//

	public static final int AW_IOTYPE_USER_IPCAM_SET_VIDEOMODE_REQ = 0x0370;  //
	public static final int AW_IOTYPE_USER_IPCAM_SET_VIDEOMODE_RESP = 0x0371; //
	public static final int AW_IOTYPE_USER_IPCAM_GET_VIDEOMODE_REQ = 0x0372;  //
	public static final int AW_IOTYPE_USER_IPCAM_GET_VIDEOMODE_RESP = 0x0373; //

	public static final int AW_IOTYPE_USER_IPCAM_FORMATEXTSTORAGE_REQ = 0x380;//
	public static final int AW_IOTYPE_USER_IPCAM_FORMATEXTSTORAGE_RESP = 0x381;//

	public static final int AW_IOTYPE_USER_IPCAM_PTZ_COMMAND = 0x1001; //

	public static final int AW_IOTYPE_USER_IPCAM_EVENT_REPORT = 0x1FFF; //

	public static final int AW_IOTYPE_USER_IPCAM_RECEIVE_FIRST_IFRAME = 0x1002;	// Send from client, used to talk to device that
	
	public static final int	AW_IOTYPE_USER_IPCAM_GET_FLOWINFO_REQ	= 0x0390; //
	public static final int	AW_IOTYPE_USER_IPCAM_GET_FLOWINFO_RESP	= 0x0391; //
	public static final int	AW_IOTYPE_USER_IPCAM_CURRENT_FLOWINFO	= 0x0392; //
	
	public static final int AW_IOTYPE_USER_IPCAM_GET_TIMEZONE_REQ	= 0x3A0; //
	public static final int AW_IOTYPE_USER_IPCAM_GET_TIMEZONE_RESP	= 0x3A1; //
	public static final int AW_IOTYPE_USER_IPCAM_SET_TIMEZONE_REQ	= 0x3B0; //
	public static final int	AW_IOTYPE_USER_IPCAM_SET_TIMEZONE_RESP	= 0x3B1; //
	
	public static final int	AW_IOTYPE_USER_IPCAM_CONNECT_SUCESS   =  0x3001;
	public static final int AW_IOTYPE_USER_IPCAM_CONNECT_FAILED   =  0x3002;
	public static final int AW_IOTYPE_USER_IPCAM_SEARCH_DEVICE	  =  0x3022;  //12322

	
	/* AVAPIs IOCTRL Event Type */
	public static final int AW_AVIOCTRL_EVENT_ALL = 0x00;
	public static final int AW_AVIOCTRL_EVENT_MOTIONDECT = 0x01;
	public static final int AW_AVIOCTRL_EVENT_VIDEOLOST = 0x02;
	public static final int AW_AVIOCTRL_EVENT_IOALARM = 0x03;
	public static final int AW_AVIOCTRL_EVENT_MOTIONPASS = 0x04;
	public static final int AW_AVIOCTRL_EVENT_VIDEORESUME = 0x05;
	public static final int AW_AVIOCTRL_EVENT_IOALARMPASS = 0x06;
	public static final int AW_AVIOCTRL_EVENT_EXPT_REBOOT = 0x10;
	public static final int AW_AVIOCTRL_EVENT_SDFAULT = 0x11;

	/* AVAPIs IOCTRL Play Record Command */
	public static final int AW_AVIOCTRL_RECORD_PLAY_PAUSE = 0x00;
	public static final int AW_AVIOCTRL_RECORD_PLAY_STOP = 0x01;
	public static final int AW_AVIOCTRL_RECORD_PLAY_STEPFORWARD = 0x02;
	public static final int AW_AVIOCTRL_RECORD_PLAY_STEPBACKWARD = 0x03;
	public static final int AW_AVIOCTRL_RECORD_PLAY_FORWARD = 0x04;
	public static final int AW_AVIOCTRL_RECORD_PLAY_BACKWARD = 0x05;
	public static final int AW_AVIOCTRL_RECORD_PLAY_SEEKTIME = 0x06;
	public static final int AW_AVIOCTRL_RECORD_PLAY_END = 0x07;
	public static final int AW_AVIOCTRL_RECORD_PLAY_START = 0x10;

	// AVIOCTRL PTZ Command Value
	public static final int AW_AVIOCTRL_PTZ_STOP = 0;
	public static final int AW_AVIOCTRL_PTZ_UP = 1;
	public static final int AW_AVIOCTRL_PTZ_DOWN = 2;
	public static final int AW_AVIOCTRL_PTZ_LEFT = 3;
	public static final int AW_AVIOCTRL_PTZ_LEFT_UP = 4;
	public static final int AW_AVIOCTRL_PTZ_LEFT_DOWN = 5;
	public static final int AW_AVIOCTRL_PTZ_RIGHT = 6;
	public static final int AW_AVIOCTRL_PTZ_RIGHT_UP = 7;
	public static final int AW_AVIOCTRL_PTZ_RIGHT_DOWN = 8;
	public static final int AW_AVIOCTRL_PTZ_AUTO = 9;
	public static final int AW_AVIOCTRL_PTZ_SET_POINT = 10;
	public static final int AW_AVIOCTRL_PTZ_CLEAR_POINT = 11;
	public static final int AW_AVIOCTRL_PTZ_GOTO_POINT = 12;
	public static final int AW_AVIOCTRL_PTZ_SET_MODE_START = 13;
	public static final int AW_AVIOCTRL_PTZ_SET_MODE_STOP = 14;
	public static final int AW_AVIOCTRL_PTZ_MODE_RUN = 15;
	public static final int AW_AVIOCTRL_PTZ_MENU_OPEN = 16;
	public static final int AW_AVIOCTRL_PTZ_MENU_EXIT = 17;
	public static final int AW_AVIOCTRL_PTZ_MENU_ENTER = 18;
	public static final int AW_AVIOCTRL_PTZ_FLIP = 19;
	public static final int AW_AVIOCTRL_PTZ_START = 20;

	public static final int AW_AVIOCTRL_LENS_APERTURE_OPEN = 21;
	public static final int AW_AVIOCTRL_LENS_APERTURE_CLOSE = 22;
	public static final int AW_AVIOCTRL_LENS_ZOOM_IN = 23;
	public static final int AW_AVIOCTRL_LENS_ZOOM_OUT = 24;
	public static final int AW_AVIOCTRL_LENS_FOCAL_NEAR = 25;
	public static final int AW_AVIOCTRL_LENS_FOCAL_FAR = 26;

	public static final int AW_AVIOCTRL_AUTO_PAN_SPEED = 27;
	public static final int AW_AVIOCTRL_AUTO_PAN_LIMIT = 28;
	public static final int AW_AVIOCTRL_AUTO_PAN_START = 29;

	public static final int AW_AVIOCTRL_PATTERN_START = 30;
	public static final int AW_AVIOCTRL_PATTERN_STOP = 31;
	public static final int AW_AVIOCTRL_PATTERN_RUN = 32;

	public static final int AW_AVIOCTRL_SET_AUX = 33;
	public static final int AW_AVIOCTRL_CLEAR_AUX = 34;
	public static final int AW_AVIOCTRL_MOTOR_RESET_POSITION = 35;

	/* AVAPIs IOCTRL Quality Type */
	public static final int AW_AVIOCTRL_QUALITY_UNKNOWN = 0x00;
	public static final int AW_AVIOCTRL_QUALITY_MAX = 0x01;
	public static final int AW_AVIOCTRL_QUALITY_HIGH = 0x02;
	public static final int AW_AVIOCTRL_QUALITY_MIDDLE = 0x03;
	public static final int AW_AVIOCTRL_QUALITY_LOW = 0x04;
	public static final int AW_AVIOCTRL_QUALITY_MIN = 0x05;

	/* AVAPIs IOCTRL WiFi Mode */
	public static final int AW_AVIOTC_WIFIAPMODE_ADHOC = 0x00;
	public static final int AW_AVIOTC_WIFIAPMODE_MANAGED = 0x01;

	/* AVAPIs IOCTRL WiFi Enc Type */
	public static final int AW_AVIOTC_WIFIAPENC_INVALID = 0x00;
	public static final int AW_AVIOTC_WIFIAPENC_NONE = 0x01;
	public static final int AW_AVIOTC_WIFIAPENC_WEP = 0x02;
	public static final int AW_AVIOTC_WIFIAPENC_WPA_TKIP = 0x03;
	public static final int AW_AVIOTC_WIFIAPENC_WPA_AES = 0x04;
	public static final int AW_AVIOTC_WIFIAPENC_WPA2_TKIP = 0x05;
	public static final int AW_AVIOTC_WIFIAPENC_WPA2_AES = 0x06;
	public static final int AW_AVIOTC_WIFIAPENC_WPA_PSK_TKIP  = 0x07;
	public static final int	AW_AVIOTC_WIFIAPENC_WPA_PSK_AES   = 0x08;
	public static final int	AW_AVIOTC_WIFIAPENC_WPA2_PSK_TKIP = 0x09;
	public static final int	AW_AVIOTC_WIFIAPENC_WPA2_PSK_AES  = 0x0A;

	/* AVAPIs IOCTRL Recording Type */
	public static final int AW_AVIOTC_RECORDTYPE_OFF = 0x00;
	public static final int AW_AVIOTC_RECORDTYPE_FULLTIME = 0x01;
	public static final int AW_AVIOTC_RECORDTYPE_ALAM = 0x02;
	public static final int AW_AVIOTC_RECORDTYPE_MANUAL = 0x03;

	public static final int AW_AVIOCTRL_ENVIRONMENT_INDOOR_50HZ = 0x00;
	public static final int AW_AVIOCTRL_ENVIRONMENT_INDOOR_60HZ = 0x01;
	public static final int AW_AVIOCTRL_ENVIRONMENT_OUTDOOR = 0x02;
	public static final int AW_AVIOCTRL_ENVIRONMENT_NIGHT = 0x03;

	/* AVIOCTRL VIDEO MODE */
	public static final int AW_AVIOCTRL_VIDEOMODE_NORMAL = 0x00;
	public static final int AW_AVIOCTRL_VIDEOMODE_FLIP = 0x01;
	public static final int AW_AVIOCTRL_VIDEOMODE_MIRROR = 0x02;
	public static final int AW_AVIOCTRL_VIDEOMODE_FLIP_MIRROR = 0x03;

	public static class SFrameInfo {

		short codec_id;
		byte flags;
		byte cam_index;
		byte onlineNum;
		byte[] reserved = new byte[3];
		int reserved2;
		int timestamp;

		public static byte[] combindContent(short codec_id, byte flags, byte cam_index, byte online_num, int timestamp) {

			byte[] result = new byte[16];

			byte[] codec = Packet.shortToByteArray_Little(codec_id);
			System.arraycopy(codec, 0, result, 0, 2);

			result[2] = flags;
			result[3] = cam_index;
			result[4] = online_num;

			byte[] time = Packet.intToByteArray_Little(timestamp);
			System.arraycopy(time, 0, result, 12, 4);

			return result;
		}
	}

	public static class AWAVStream {
		int channel = 0; // camera index
		byte[] reserved = new byte[4];

		public static byte[] combindContent(int channel) {
			byte[] result = new byte[8];
			byte[] ch = Packet.intToByteArray_Little(channel);
			System.arraycopy(ch, 0, result, 0, 4);

			return result;
		}
		
		@Override
	    public String toString() {
	        return "channel=[" + channel + "];";
	    }
	}

	public class AWEventConfig {
		long channel; // Camera Index
		byte mail; // enable send email
		byte ftp; // enable ftp upload photo
		byte localIO; // enable local io output
		byte p2pPushMsg; // enable p2p push msg
	}

/* AW_IOTYPE_USER_IPCAM_PTZ_COMMAND = 0x1001 */
	public static class AWPtzCmd {
		byte control;  // ptz control command
		byte speed;    // ptz control speed
		byte point;
		byte limit;
		byte aux;
		byte channel; // camera index
		byte[] reserved = new byte[2];

		public static int getTotalSize(){
			return 8;
		}
		
		public static byte[] combindContent(byte control, byte speed, byte point, byte limit, byte aux, byte channel) {
			byte[] result = new byte[getTotalSize()];

			result[0] = control;
			result[1] = speed;
			result[2] = point;
			result[3] = limit;
			result[4] = aux;
			result[5] = channel;

			return result;
		}
	}
	
/* AW_IOTYPE_USER_IPCAM_SETSTREAMCTRL_REQ = 0x0320 */
	public static class AWSetStreamCtrlReq {
		int channel;  // Camera Index
		byte quality; // AVIOCTRL_QUALITY_XXXX
		byte[] reserved = new byte[3];
		
		public static int getTotalSize(){
			return 8;
		}
		
		public static byte[] combindContent(int channel, byte quality) {
		
			byte[] result = new byte[getTotalSize()];
			byte[] ch = Packet.intToByteArray_Little(channel);
			System.arraycopy(ch, 0, result, 0, 4);
			result[4] = quality;

			return result;
		}
	}
	
/* AW_IOTYPE_USER_IPCAM_SETSTREAMCTRL_RESP = 0x0321 */
	public class AWSetStreamCtrlResp {
		int result;
		byte[] reserved = new byte[4];
	}
	
/* AW_IOTYPE_USER_IPCAM_GETSTREAMCTRL_REQ = 0x0322 */
	public static class AWGetStreamCtrlReq {
		int channel; // Camera Index
		byte[] reserved = new byte[4];
		
		public static int getTotalSize(){
			return 8;
		}

		public static byte[] combindContent(int channel) {
		
			byte[] result = new byte[getTotalSize()];
			byte[] ch = Packet.intToByteArray_Little(channel);
			System.arraycopy(ch, 0, result, 0, 4);

			return result;
		}
	}
	
/* AW_IOTYPE_USER_IPCAM_GETSTREAMCTRL_RESP = 0x0323 */
	public class AWGetStreamCtrlResp {
		int channel; // Camera Index
		byte quality; // AVIOCTRL_QUALITY_XXXX
		byte[] reserved = new byte[3];
	}
	
/* AW_IOTYPE_USER_IPCAM_SETMOTIONDETECT_REQ = 0x0324 */
	public static class AWSetMotionDetectReq {
		int channel; // Camera Index
		int sensitivity; /* 0(disbale) ~ 100(MAX) */
		
		public static int getTotalSize(){
			return 8;
		}
		
		public static byte[] combindContent(int channel, int sensitivity) {

			byte[] result = new byte[getTotalSize()];
			byte[] ch = Packet.intToByteArray_Little(channel);
			byte[] sen = Packet.intToByteArray_Little(sensitivity);

			System.arraycopy(ch, 0, result, 0, 4);
			System.arraycopy(sen, 0, result, 4, 4);

			return result;
		}
	}
	
/* AW_IOTYPE_USER_IPCAM_SETMOTIONDETECT_RESP = 0x0325 */
	public class AWSetMotionDetectResp {
		byte result;
		byte[] reserved = new byte[3];
	}
	
/* AW_IOTYPE_USER_IPCAM_GETMOTIONDETECT_REQ = 0x0326 */
	public static class AWGetMotionDetectReq {
		int channel; // Camera Index
		byte[] reserved = new byte[4];
		
		public static int getTotalSize(){
			return 8;
		}
		public static byte[] combindContent(int channel) {

			byte[] result = new byte[getTotalSize()];
			byte[] ch = Packet.intToByteArray_Little(channel);
			System.arraycopy(ch, 0, result, 0, 4);

			return result;
		}
	}
	
/* AW_IOTYPE_USER_IPCAM_GETMOTIONDETECT_RESP = 0x0327 */
	public class AWGetMotionDetectResp {
		int channel; // Camera Index
		int sensitivity; /* 0(disbale) ~ 100(MAX) */
	}
	
/* AW_IOTYPE_USER_IPCAM_DEVINFO_REQ = 0x0330 */
	public static class AWDeviceInfoReq {

		static byte[] reserved = new byte[4];;

		public static byte[] combindContent() {
			return reserved;
		}
	}

/* AW_IOTYPE_USER_IPCAM_DEVINFO_RESP = 0x0331 */
	public class AWDeviceInfoResp {
		byte[] model = new byte[16];
		byte[] vendor = new byte[16];
		int version;
		int channel;
		int total; /* MByte */
		int free; /* MByte */
		byte[] reserved = new byte[8];
	}

/* AW_IOTYPE_USER_IPCAM_SETPASSWORD_REQ = 0x0332 */
	public static class AWSetPasswdReq {
		byte[] oldPasswd = new byte[32];
		byte[] newPasswd = new byte[32];
		
		public static int getTotalSize(){
			return 64;
		}

		public static byte[] combindContent(String oldPwd, String newPwd) {

			byte[] oldpwd = oldPwd.getBytes();
			byte[] newpwd = newPwd.getBytes();
			byte[] result = new byte[getTotalSize()];

			System.arraycopy(oldpwd, 0, result, 0, oldpwd.length);
			System.arraycopy(newpwd, 0, result, 32, newpwd.length);

			return result;
		}
	}
	
/* AW_IOTYPE_USER_IPCAM_SETPASSWORD_RESP = 0x0333 */
	public class AWSetPasswdResp {
		byte result;
		byte[] reserved = new byte[3];
	}

/* AW_IOTYPE_USER_IPCAM_LISTWIFIAP_REQ = 0x0340 */
	public static class AWListWifiApReq {

		static byte[] reserved = new byte[4];

		public static byte[] combindContent() {

			return reserved;
		}
	}

	public static class SWifiAp {
		public byte[] ssid = new byte[32];
		public byte mode;
		public byte enctype;
		public byte signal;
		public byte status;

		public static int getTotalSize() {
			return 36;
		}

		public SWifiAp(byte[] data) {

			System.arraycopy(data, 1, ssid, 0, data.length);
			mode = data[32];
			enctype = data[33];
			signal = data[34];
			status = data[35];
		}

		public SWifiAp(byte[] bytsSSID, byte bytMode, byte bytEnctype, byte bytSignal, byte bytStatus) {

			System.arraycopy(bytsSSID, 0, ssid, 0, bytsSSID.length);
			mode = bytMode;
			enctype = bytEnctype;
			signal = bytSignal;
			status = bytStatus;
		}
	}

/* AW_IOTYPE_USER_IPCAM_LISTWIFIAP_RESP = 0x0341 */
	public class AWListWifiApResp {
		int number; // MAX: 1024/36= 28
		SWifiAp stWifiAp;
	}

/* AW_IOTYPE_USER_IPCAM_SETWIFI_REQ = 0x0342 */
	public static class AWSetWifiReq {
		byte[] ssid = new byte[32];
		byte[] password = new byte[32];
		byte mode;
		byte enctype;
		byte[] reserved = new byte[10];

		public static int getTotalSize(){
			return 76;
		}
		
		public static byte[] combindContent(byte[] ssid, byte[] password, byte mode, byte enctype) {

			byte[] result = new byte[getTotalSize()];

			System.arraycopy(ssid, 0, result, 0, ssid.length);
			System.arraycopy(password, 0, result, 32, password.length);
			result[64] = mode;
			result[65] = enctype;

			return result;
		}
		
		
	}
	
/* AW_IOTYPE_USER_IPCAM_SETWIFI_RESP = 0x0343 */
	public class AWSetWifiResp {
		byte result;
		byte[] reserved = new byte[3];
	}

/* AW_IOTYPE_USER_IPCAM_GETWIFI_REQ = 0x0344 */
	public static class AWGetWifiReq {

		static byte[] reserved = new byte[4];

		public static byte[] combindContent() {
			return reserved;
		}
	}

/* AW_IOTYPE_USER_IPCAM_GETWIFI_RESP = 0x0345 */
	public class AWGetWifiResp {
		byte[] ssid = new byte[32];
		byte[] password = new byte[32];
		byte mode;
		byte enctype;
		byte signal;
		byte status;
	}

/* AW_IOTYPE_USER_IPCAM_SETRECORD_REQ = 0x0310 */
	public static class AWSetRecordReq {
		int channel; // Camera Index
		int recordType;
		byte[] reserved = new byte[4];

		public static int getTotalSize(){
			return 12;
		}
		
		public static byte[] combindContent(int channel, int recordType) {

			byte[] result = new byte[getTotalSize()];
			byte[] ch = Packet.intToByteArray_Little(channel);
			byte[] type = Packet.intToByteArray_Little(recordType);

			System.arraycopy(ch, 0, result, 0, 4);
			System.arraycopy(type, 0, result, 4, 4);

			return result;
		}
	}
	
/* AW_IOTYPE_USER_IPCAM_SETRECORD_RESP = 0x0311 */
	public class AWSetRecordResp {
		byte result;
		byte[] reserved = new byte[3];
	}

/* AW_IOTYPE_USER_IPCAM_GETRECORD_REQ = 0x0312 */
	public static class AWGetRecordReq {
		int channel; // Camera Index
		byte[] reserved = new byte[4];

		public static int getTotalSize(){
			return 8;
		}
		
		public static byte[] combindContent(int channel) {

			byte[] result = new byte[getTotalSize()];
			byte[] ch = Packet.intToByteArray_Little(channel);
			System.arraycopy(ch, 0, result, 0, 4);

			return result;
		}
	}

/* AW_IOTYPE_USER_IPCAM_GETRECORD_RESP = 0x0313 */
	public class AWGetRecordResp {
		int channel; // Camera Index
		int recordType;
	}

/* AW_IOTYPE_USER_IPCAM_SETRCD_DURATION_REQ = 0x0314 */
	public class AWSetRcdDurationReq {
		int channel; // Camera Index
		int presecond;
		int durasecond;
	}

/* AW_IOTYPE_USER_IPCAM_SETRCD_DURATION_RESP = 0x0315 */
	public class AWSetRcdDurationResp {
		byte result;
		byte[] reserved = new byte[3];
	}


/* AW_IOTYPE_USER_IPCAM_GETRCD_DURATION_REQ = 0x0316 */
	public class AWGetRcdDurationReq {
		int channel; // Camera Index
		byte[] reserved = new byte[4];
	}

/* AW_IOTYPE_USER_IPCAM_GETRCD_DURATION_RESP = 0x0317 */
	public class AWGetRcdDurationResp {
		int channel; // Camera Index
		int presecond;
		int durasecond;
	}
	
	public static class STimeDay {

		private byte[] mBuf;
		public short year;
		public byte month;
		public byte day;
		public byte wday;
		public byte hour;
		public byte minute;
		public byte second;

		public STimeDay(byte[] data) {

			mBuf = new byte[8];
			System.arraycopy(data, 0, mBuf, 0, 8);

			year = Packet.byteArrayToShort_Little(data, 0);
			month = data[2];
			day = data[3];
			wday = data[4];
			hour = data[5];
			minute = data[6];
			second = data[7];	
		}

		public long getTimeInMillis() {

			Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("gmt"));
			cal.set(year, month - 1, day, hour, minute, second);

			return cal.getTimeInMillis();
		}

		public String getLocalTime() {

			Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("gmt"));
			calendar.setTimeInMillis(getTimeInMillis());
			// calendar.add(Calendar.MONTH, -1);

			SimpleDateFormat dateFormat = new SimpleDateFormat();
			dateFormat.setTimeZone(TimeZone.getDefault());

			return dateFormat.format(calendar.getTime());
		}

		public byte[] toByteArray() {
			return mBuf;
		}

		public static byte[] combindContent(int year, int month, int day, int wday, int hour, int minute, int second) {

			byte[] result = new byte[8];

			byte[] y = Packet.shortToByteArray_Little((short) year);
			System.arraycopy(y, 0, result, 0, 2);

			result[2] = (byte) month;
			result[3] = (byte) day;
			result[4] = (byte) wday;
			result[5] = (byte) hour;
			result[6] = (byte) minute;
			result[7] = (byte) second;

			return result;
		}
	}
	
/* AW_IOTYPE_USER_IPCAM_LISTEVENT_REQ = 0x0318 */
	public static class AWListEventReq {

		int channel; // Camera Index
		byte[] startutctime = new byte[8];
		byte[] endutctime = new byte[8];
		byte event;
		byte status;
		byte[] reversed = new byte[2];

		public static byte[] combindConent(int channel, long startutctime, long endutctime, byte event, byte status) {

			Calendar startCal = Calendar.getInstance(TimeZone.getTimeZone("gmt"));
			Calendar stopCal = Calendar.getInstance(TimeZone.getTimeZone("gmt"));
			startCal.setTimeInMillis(startutctime);
			stopCal.setTimeInMillis(endutctime);

			System.out.println("search from " + startCal.get(Calendar.YEAR) + "/" + startCal.get(Calendar.MONTH) + "/" + startCal.get(Calendar.DAY_OF_MONTH)
					+ " " + startCal.get(Calendar.HOUR_OF_DAY) + ":" + startCal.get(Calendar.MINUTE) + ":" + startCal.get(Calendar.SECOND));
			System.out.println("       to   " + stopCal.get(Calendar.YEAR) + "/" + stopCal.get(Calendar.MONTH) + "/" + stopCal.get(Calendar.DAY_OF_MONTH) + " "
					+ stopCal.get(Calendar.HOUR_OF_DAY) + ":" + stopCal.get(Calendar.MINUTE) + ":" + stopCal.get(Calendar.SECOND));

			byte[] result = new byte[24];

			byte[] ch = Packet.intToByteArray_Little(channel);
			System.arraycopy(ch, 0, result, 0, 4);

			byte[] start = STimeDay.combindContent(startCal.get(Calendar.YEAR), startCal.get(Calendar.MONTH) + 1, startCal.get(Calendar.DAY_OF_MONTH),
					startCal.get(Calendar.DAY_OF_WEEK), startCal.get(Calendar.HOUR_OF_DAY), startCal.get(Calendar.MINUTE), 0);
			System.arraycopy(start, 0, result, 4, 8);

			byte[] stop = STimeDay.combindContent(stopCal.get(Calendar.YEAR), stopCal.get(Calendar.MONTH) + 1, stopCal.get(Calendar.DAY_OF_MONTH),
					stopCal.get(Calendar.DAY_OF_WEEK), stopCal.get(Calendar.HOUR_OF_DAY), stopCal.get(Calendar.MINUTE), 0);
			System.arraycopy(stop, 0, result, 12, 8);

			result[20] = event;
			result[21] = status;

			return result;
		}
	}

	public static class SAvEvent {
		byte[] utctime = new byte[8];
		byte event;
		byte status;
		byte[] reserved = new byte[2];

		public static int getTotalSize() {
			return 12;
		}
	}
	
/* AW_IOTYPE_USER_IPCAM_LISTEVENT_RESP = 0x319 */
	public class AWListEventResp {
		int channel; // Camera Index
		int total;
		byte index;
		byte endflag;
		byte count;
		byte reserved;
		SAvEvent stEvent;
	}
	
/* AW_IOTYPE_USER_IPCAM_RECORD_PLAYCONTROL_REQ = 0x031A */
	public static class AWPlayRecordReq {
		int channel; // Camera Index
		int command; // play record command
		int Param;
		byte[] stTimeDay = new byte[8];
		byte[] reserved = new byte[4];

		public static byte[] combindContent(int channel, int command, int param, long time) {

			byte[] result = new byte[24];

			byte[] ch = Packet.intToByteArray_Little(channel);
			System.arraycopy(ch, 0, result, 0, 4);

			byte[] cmd = Packet.intToByteArray_Little(command);
			System.arraycopy(cmd, 0, result, 4, 4);

			byte[] p = Packet.intToByteArray_Little(param);
			System.arraycopy(p, 0, result, 8, 4);

			Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("gmt"));
			cal.setTimeInMillis(time);
			cal.add(Calendar.DAY_OF_MONTH, -1);
			cal.add(Calendar.DATE, 1);
			byte[] timedata = STimeDay.combindContent(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH),
					cal.get(Calendar.DAY_OF_WEEK), cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND));
			System.arraycopy(timedata, 0, result, 12, 8);

			return result;
		}

		public static byte[] combindContent(int channel, int command, int param, byte[] time) {

			byte[] result = new byte[24];

			byte[] ch = Packet.intToByteArray_Little(channel);
			System.arraycopy(ch, 0, result, 0, 4);

			byte[] cmd = Packet.intToByteArray_Little(command);
			System.arraycopy(cmd, 0, result, 4, 4);

			byte[] p = Packet.intToByteArray_Little(param);
			System.arraycopy(p, 0, result, 8, 4);

			System.arraycopy(time, 0, result, 12, 8);

			return result;
		}
	}
	
	// only for play record start command
/* AW_IOTYPE_USER_IPCAM_RECORD_PLAYCONTROL_RESP = 0x031B */
	public class AWPlayRecordResp {
		int channel;
		int result;
		byte[] reserved = new byte[4];
	} 
	
/* AW_IOTYPE_USER_IPCAM_EVENT_REPORT = 0x1FFF*/

	public class AWEvent {
		STimeDay stTime; // 8 bytes
		int channel; // Camera Index
		int event; // Event Type
		byte[] reserved = new byte[4];
	}

/* AW_IOTYPE_USER_IPCAM_SET_VIDEOMODE_REQ = 0x0370 */
	public static class AWSetVideoModeReq {
		int channel; // Camera Index
		byte mode; // Video mode
		byte[] reserved = new byte[3];
		
		public static int getTotalSize() {
			return 8;
		}

		public static byte[] combindContent(int channel, byte mode) {
			byte[] result = new byte[getTotalSize()];

			byte[] ch = Packet.intToByteArray_Little(channel);
			System.arraycopy(ch, 0, result, 0, 4);

			result[4] = mode;

			return result;
		}
	}

/* AW_IOTYPE_USER_IPCAM_SET_VIDEOMODE_RESP = 0x0371 */
	public class AWSetVideoModeResp {
		int channel; // Camera Index
		byte result; // 1 - succeed, 0 - failed
		byte[] reserved = new byte[3];
	}

/* AW_IOTYPE_USER_IPCAM_GET_VIDEOMODE_REQ = 0x372 */
	public static class AWGetVideoModeReq {
		int channel; // Camera Index
		byte[] reserved = new byte[4];

		public static int getTotalSize() {
			return 8;
		}
		
		public static byte[] combindContent(int channel) {
			byte[] result = new byte[getTotalSize()];

			byte[] ch = Packet.intToByteArray_Little(channel);
			System.arraycopy(ch, 0, result, 0, 4);

			return result;
		}
	}

/* AW_IOTYPE_USER_IPCAM_GET_VIDEOMODE_RESP = 0x373 */
	public class AWGetVideoModeResp {
		int channel; // Camera Index
		byte mode; // Video Mode
		byte[] reserved = new byte[3];
	}

/* AW_IOTYPE_USER_IPCAM_SET_ENVIRONMENT_REQ = 0x0360 */
	public static class AWSetEnvironmentReq {
		int channel; // Camera Index
		byte mode; // Environment mode
		byte[] reserved = new byte[3];
		
		public static int getTotalSize() {
			return 8;
		}

		public static byte[] combindContent(int channel, byte mode) {

			byte[] result = new byte[getTotalSize()];

			byte[] ch = Packet.intToByteArray_Little(channel);
			System.arraycopy(ch, 0, result, 0, 4);

			result[4] = mode;

			return result;
		}
	}

/* AW_IOTYPE_USER_IPCAM_SET_ENVIRONMENT_RESP = 0x0361 */
	public class AWSetEnvironmentResp {

		int channel; // Camera Index
		byte result; // 1 - succeed, 0 - failed
		byte[] reserved = new byte[3];
	}

/* AW_IOTYPE_USER_IPCAM_GET_ENVIRONMENT_REQ = 0x362 */
	public static class AWGetEnvironmentReq {
		int channel; // Camera Index
		byte[] reserved = new byte[4];

		public static int getTotalSize() {
			return 8;
		}
		
		public static byte[] combindContent(int channel) {

			byte[] result = new byte[getTotalSize()];

			byte[] ch = Packet.intToByteArray_Little(channel);
			System.arraycopy(ch, 0, result, 0, 4);

			return result;
		}
	}

/* AW_IOTYPE_USER_IPCAM_GET_ENVIRONMENT_RESP = 0x363 */
	public class AWGetEnvironmentResp {
		int channel; // Camera Index
		byte mode; // Environment Mode
		byte[] reserved = new byte[3];
	}

/* AW_IOTYPE_USER_IPCAM_FORMATEXTSTORAGE_REQ = 0x380 */
	public static class AWFormatExtStorageReq {

		int storage; // Storage index (ex. sdcard slot = 0, internal flash = 1,
						// ...)
		byte[] reserved = new byte[4];

		public static int getTotalSize() {
			return 8;
		}
		
		public static byte[] combindContent(int storage) {

			byte[] result = new byte[getTotalSize()];

			byte[] ch = Packet.intToByteArray_Little(storage);
			System.arraycopy(ch, 0, result, 0, 4);

			return result;
		}
	}

/* AW_IOTYPE_USER_IPCAM_FORMATEXTSTORAGE_RESP = 0x381 */
	public class AWFormatExtStorageResp {

		int storage; // Storage index
		byte result; // 0: success;
						// -1: format command is not supported.
						// otherwise: failed.

		byte[] reserved = new byte[3];
	}

	public static class SStreamDef {

		public int index; // the stream index of camera
		public int channel; // the channel index used in AVAPIs

		public SStreamDef(byte[] data) {

			index = Packet.byteArrayToShort_Little(data, 0);
			channel = Packet.byteArrayToShort_Little(data, 2);
		}

		public String toString() {
			return ("CH" + String.valueOf(index + 1));
		}
	}

/* AW_IOTYPE_USER_IPCAM_GETSUPPORTSTREAM_REQ = 0x0328 */
	public static class AWGetSupportStreamReq {

		public static byte[] combindContent() {

			return new byte[4];
		}

		public static int getTotalSize() {
			return 4;
		}
	}

/* AW_IOTYPE_USER_IPCAM_GETSUPPORTSTREAM_RESP = 0x0329 */
	public class AWGetSupportStreamResp {

		public SStreamDef mStreamDef[];
		public long number;
	}

/* AW_IOTYPE_USER_IPCAM_GETAUDIOOUTFORMAT_REQ = 0x032A */ 
	public static class AWGetAudioOutFormatReq {

		public static byte[] combindContent() {
			return new byte[8];
		}
	}

/* AW_IOTYPE_USER_IPCAM_GETAUDIOOUTFORMAT_RESP = 0x032B */ 
	public class AWGetAudioOutFormatResp {
		public int channel;
		public int format;
	}
	
/* AW_IOTYPE_USER_IPCAM_GET_FLOWINFO_REQ	= 0x0390 */
	public static class AWGetFlowInfoReq {
		public int channel;
		public int collect_interval;
		
	}

/* AW_IOTYPE_USER_IPCAM_GET_FLOWINFO_RESP	= 0x0391 */
	public static class AWGetFlowInfoResp {
		public int channel;
		public int collect_interval;
		
		public static int getTotalSize() {
			return 8;
		}
		
		public static byte[] combindContent(int channel, int collect_interval) {

			byte[] result = new byte[getTotalSize()];

			byte[] ch = Packet.intToByteArray_Little(channel);
			System.arraycopy(ch, 0, result, 0, 4);

			byte[] col = Packet.intToByteArray_Little(collect_interval);
			System.arraycopy(col, 0, result, 4, 4);

			return result;
		}
	}
	
/* AW_IOTYPE_USER_IPCAM_CURRENT_FLOWINFO	= 0x392 */
	public static class AWCurrentFlowInfo {
		public int channel;
		public int total_frame_count;
		public int lost_incomplete_frame_count;
		public int total_expected_frame_size;
		public int total_actual_frame_size;
		public int elapse_time_ms;
		
		public static int getTotalSize() {
			return 32;
		}
		
		public static byte[] combindContent(int channel, int total_frame_count,int lost_incomplete_frame_count,int total_expected_frame_size,int total_actual_frame_size,int elapse_time_ms) {

			byte[] result = new byte[getTotalSize()];

			byte[] ch = Packet.intToByteArray_Little(channel);
			System.arraycopy(ch, 0, result, 0, 4);

			byte[] total_frame = Packet.intToByteArray_Little(total_frame_count);
			System.arraycopy(total_frame, 0, result, 4, 4);
			
			byte[] lost_incomplete = Packet.intToByteArray_Little(lost_incomplete_frame_count);
			System.arraycopy(lost_incomplete, 0, result, 8, 4);

			byte[] total_expected = Packet.intToByteArray_Little(total_expected_frame_size);
			System.arraycopy(total_expected, 0, result, 12, 4);
			
			byte[] total_actual = Packet.intToByteArray_Little(total_actual_frame_size);
			System.arraycopy(total_actual, 0, result, 16, 4);

			byte[] elapse_time = Packet.intToByteArray_Little(elapse_time_ms);
			System.arraycopy(elapse_time, 0, result, 20, 4);
			
			return result;
		}
	}
	
	/* AW_IOTYPE_USER_IPCAM_GET_TIMEZONE_REQ               = 0x3A0
	 * AW_IOTYPE_USER_IPCAM_GET_TIMEZONE_RESP              = 0x3A1
	 * AW_IOTYPE_USER_IPCAM_SET_TIMEZONE_REQ               = 0x3B0
	 * AW_IOTYPE_USER_IPCAM_SET_TIMEZONE_RESP              = 0x3B1
	 */
	public static class AWTimeZone{
		public int cbSize;
		public int nIsSupportTimeZone;
		public int nGMTDiff;
		public byte[] szTimeZoneString = new byte[256];
		
		public static byte[] combindContent() {

			return new byte[268];
		}
		
		public static byte[] combindContent(int cbSize, int nIsSupportTimeZone, int nGMTDiff, byte[] szTimeZoneString) {

			byte[] result = new byte[12+256];

			byte[] size = Packet.intToByteArray_Little(cbSize);
			System.arraycopy(size, 0, result, 0, 4);
			
			byte[] isSupportTimeZone = Packet.intToByteArray_Little(nIsSupportTimeZone);
			System.arraycopy(isSupportTimeZone, 0, result, 4, 4);
			
			byte[] GMTDiff = Packet.intToByteArray_Little(nGMTDiff);
			System.arraycopy(GMTDiff, 0, result, 8, 4);

			System.arraycopy(szTimeZoneString, 0, result, 12, szTimeZoneString.length);

			return result;
		}
	}
	
/* AW_IOTYPE_USER_IPCAM_RECEIVE_FIRST_IFRAME = 0x1002 */
	public static class AWReceiveFirstIFrame {
		int channel; // Camera Index
		int recordType;
		byte[] reserved = new byte[4];
		
		public static int getTotalSize() {
			return 12;
		}

		public static byte[] combindContent(int channel, int recordType) {

			byte[] result = new byte[getTotalSize()];
			byte[] ch = Packet.intToByteArray_Little(channel);
			byte[] type = Packet.intToByteArray_Little(recordType);

			System.arraycopy(ch, 0, result, 0, 4);
			System.arraycopy(type, 0, result, 4, 4);

			return result;
		}
	}


	public static class SetWiFiReq{
		
		byte[] ssid = new byte[32];
		byte[] password = new byte[32];
		byte mode;
		byte enctype;
		byte[] reserved = new byte[10];
		
		public static int getTotalSize(){
			
			return 76;
			
		}
		
		public static byte[] combindContent(byte[] ssidStr, byte[] passwordStr, byte mode,byte enctype) {

			byte[] result = new byte[getTotalSize()];
			System.arraycopy(ssidStr,     0, result,  0, ssidStr.length);
			System.arraycopy(passwordStr, 0, result, 32, passwordStr.length);
			result[64] = mode;
			result[65] = enctype;
			byte[] other = new byte[]{9,8,7,6,5,4,3,2,1,0};
			System.arraycopy(other,       0, result, 66, other.length);
			return result;
		}
		
		public SetWiFiReq(byte[] result){

			for (int i = 0; i < result.length; i++) {
				Log.e(TAG, i + " : " + result[i]);
			}
			
			System.arraycopy(result, 0          , ssid    , 0, ssid.length    );
			System.arraycopy(result, ssid.length, password, 0, password.length);
			mode     = result[ssid.length + password.length];
			enctype  = result[ssid.length + password.length + 1];
			System.arraycopy(result, ssid.length + password.length + 2 , reserved , 0, reserved.length);
			
		}

		@Override
		public String toString() {
			
			String msg      = "";
			String ssidStr  = "";
			String passwordStr = "";
			try {
				 ssidStr     = new String(this.ssid    ,"UTF-8").trim();
				 passwordStr = new String(this.password,"UTF-8").trim();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			return "SSetWiFiReq [ssid=" + ssidStr + ", password="
					+ passwordStr + ", mode=" + mode
					+ ", enctype=" + enctype + ", reserved="
					+ "]";
			
		}
	}

	
	public static class NetInfoResp{
		
		public byte    Mode;
		public byte[]  RemoteIP = new byte[17];//17
		public short   RemotePort;
		public byte    NatType;
		public byte    isSecure;
		
		public NetInfoResp(byte[] result){

			for (int i = 0; i < result.length; i++) {
				Log.e(TAG, i + " : " + result[i]);
			}
			
			this.Mode       = result[0];
			System.arraycopy(result, 1, this.RemoteIP, 0, 17);
			Log.e(TAG, "19 = " + result[18] +" and 20 = " + result[19]);
			
			this.RemotePort = Packet.byteArrayToShort_Little(result, 18);
			this.NatType    = result[20];
			this.isSecure   = result[21]; 
			
		}
		
		public static int getTotalSize(){
			return 22;
		}
		
		
//		public static int getTotalSize(){
//			
//			return 22;
//			
//		}
		
		
//		public static byte[] combindContent(byte Mode, byte[] RemoteIP, short RemotePort, byte isSecure) {
//
//			byte[] result = new byte[getTotalSize()];
//			result[0] = Mode;
//			System.arraycopy(RemoteIP, 0, result, 1, RemoteIP.length);
//			byte[] codec = Packet.shortToByteArray_Little(RemotePort);
//			System.arraycopy(codec, 0, result, 19, codec.length);
//			result[21] = isSecure;
//			
//			return result;
//		}
		
	}
	
	public static class LanSearchInfo{
		
		public byte[] UID       = new byte[64];
		public byte[] IP        = new byte[16];
		public short  port;
		public short  reserved2;
		
		public static int getTotalSize(){
			return 84;
		}
		
		public LanSearchInfo(byte[] result,int index){
			
			System.arraycopy(result, index * getTotalSize()                      , this.UID, 0, this.UID.length);
			System.arraycopy(result, this.UID.length + index * getTotalSize()    , this.IP , 0, this.IP.length);
			this.port = Packet.byteArrayToShort_Little(result, index * getTotalSize() + this.UID.length + this.IP.length); 
		}

		@Override
		public String toString() {
			
			String uIDString = new String(this.UID).trim();
			String iPString  = new String(this.IP).trim();
			
			return "LanSearchInfo [UID=" + uIDString + ", IP="
					+ iPString + ", port=" + port  + "]";
		}
		
	}
	
	/**
	 * CDR Private Cmd Part
	 */

	public static final int NAT_CMD_SET_RECORDER_QUALITY       = 0xA001; 
	public static final int NAT_CMD_SET_RECORDER_QUALITY_RESP  = 0xA002; // 
		
	public static final int NAT_CMD_SET_RECORDER_DURATION      = 0xA003; // 
	public static final int NAT_CMD_SET_RECORDER_DURATION_RESP = 0xA004; //
	
	public static final int NAT_CMD_SET_PHOTO_QUALITY          = 0xA005; //  
	public static final int NAT_CMD_SET_PHOTO_QUALITY_RESP     = 0xA006; //  
	
	public static final int NAT_CMD_SET_WHITE_BALANCE      	   = 0xA007;
	public static final int NAT_CMD_SET_WHITE_BALANCE_RESP     = 0xA008; 
	
	public static final int NAT_CMD_SET_EXPOSURE               = 0xA009;
	public static final int NAT_CMD_SET_EXPOSURE_RESP          = 0xA00A;
	
	public static final int NAT_CMD_SET_BOOT_RECORDER          = 0xA00B;  
	public static final int NAT_CMD_SET_BOOT_RECORDER_RESP	   = 0xA00C; 
	
	public static final int NAT_CMD_SET_MUTE			       = 0xA00D;  
	public static final int NAT_CMD_SET_MUTE_RESP			   = 0xA00E; 
	
	public static final int NAT_CMD_SET_REAR_VIEW_MIRROR	   = 0xA00F; 
	public static final int NAT_CMD_SET_REAR_VIEW_MIRROR_RESP  = 0xA010;
	
	public static final int NAT_CMD_SET_TIME			       = 0xA011;  
	public static final int NAT_CMD_SET_TIME_RESP			   = 0xA012;   
	
	public static final int NAT_CMD_SET_LANGUAGE			   = 0xA013;    
	public static final int NAT_CMD_SET_LANGUAGE_RESP		   = 0xA014;  
	
	public static final int NAT_CMD_SET_SMART_DETECT 	       = 0xA015;   
	public static final int NAT_CMD_SET_SMART_DETECT_RESP 	   = 0xA016;   
	
	public static final int NAT_CMD_SET_LANE_LINE_CALIBRATION      = 0xA017;    
	public static final int NAT_CMD_SET_LANE_LINE_CALIBRATION_RESP = 0xA018; 
	
	public static final int NAT_CMD_SET_IMPACT_SENSITIVITY 		   = 0xA019;   
	public static final int NAT_CMD_SET_IMPACT_SENSITIVITY_RESP    = 0xA01A;
	
	public static final int NAT_CMD_SET_MOTION_DETECT			   = 0xA01B;    
	public static final int NAT_CMD_SET_MOTION_DETECT_RESP		   = 0xA01C;   
	
	public static final int NAT_CMD_SET_WATERMARK  				   = 0xA01D;    
	public static final int NAT_CMD_SET_WATERMARK_RESP 			   = 0xA01E;    
	
	public static final int NAT_CMD_FORMAT_TF_CARD 				   = 0xA01F;   
	public static final int NAT_CMD_FORMAT_TF_CARD_RESP 	       = 0xA020;   
	
	public static final int NAT_CMD_SCREEN_SLEEP  				   = 0xA021; 
	public static final int NAT_CMD_SCREEN_SLEEP_RESP 		       = 0xA022;   
	
	public static final int NAT_CMD_PLAY_RECORDER  				   = 0xA023;  
	public static final int NAT_CMD_PLAY_RECORDER_RESP 			   = 0xA024; 
	
	public static final int NAT_CMD_GET_FILE_LIST			       = 0xA025;   
	public static final int NAT_CMD_GET_FILE_LIST_RESP 		       = 0xA026; 
	
	public static final int NAT_CMD_REQUEST_FILE   			       = 0xA027; 
	public static final int NAT_CMD_REQUEST_FILE_RESP 			   = 0xA028;   
	
	public static final int NAT_CMD_REQUEST_FILE_FINISH 		   = 0xA029;
	
	public static final int NAT_CMD_CHECK_TF_CARD       		   = 0xA02A;
	public static final int NAT_CMD_CHECK_TF_CARD_RESP	 		   = 0xA02B;
	
	public static final int NAT_CMD_DELETE_FILE         		   = 0xA02C;
	public static final int NAT_CMD_DELETE_FILE_RESP 			   = 0xA02D;
	
	public static final int NAT_CMD_RECORD_CTL          		   = 0xA02E;
	public static final int NAT_CMD_RECORD_CTL_RESP				   = 0xA02F;
	
	public static final int NAT_CMD_TURN_OFF_WIFI        		   = 0xA030;
	
	public static final int NAT_CMD_SET_WIFI_PWD        		   = 0xA031;
	
	public static final int NAT_CMD_SET_SYNC_FILE       		   = 0xA032;
	public static final int NAT_CMD_SET_SYNC_FILE_RESP			   = 0xA033;
	
	public static final int NAT_CMD_GET_CONFIG          		   = 0xA034; 
	public static final int NAT_CMD_GET_CONFIG_RESP				   = 0xA035;
	
	public static final int NAT_CMD_CHANGE_CAM          		   = 0xA036; 
	public static final int NAT_CMD_CHANGE_CAM_RESP     		   = 0xA037; 
	
	public static final int NAT_CMD_TAKE_PHOTO          		   = 0xA038; 
	public static final int NAT_CMD_TAKE_PHOTO_RESP				   = 0xA039;
	
	public static final int NAT_CMD_RECORD_ON_OFF       		   = 0xA03A; 
	public static final int NAT_CMD_RECORD_ON_OFF_RESP		       = 0xA03B;
	
	public static final int NAT_CMD_SET_VIDEO_SIZE       		   = 0xA03C;
	public static final int NAT_CMD_SET_VIDEO_SIZE_ACK   		   = 0xA03D;

	public static final int NAT_CMD_NET_CONNECT_STATE     		   = 0xA03E;
	public static final int NAT_CMD_NET_CONNECT_STATE_RESP		   = 0xA03F;

	public static final int NAT_CMD_NET_SOS_RESP                   = 0xA040;

	public static class AW_cdr_net_config{
		
		public int record_quality;
		public int record_duration;
		public int photo_quality;
		public int white_balance;
		public int exposure;
		public int boot_record;
		public int mute;
		public int rear_view_mirror;
		public int language;
		public int smart_detect;
		public int lane_line_calibration;
		public int impact_sensitivity;
		public int motion_detect;
		public int watermark;
		public int record_switch;
		public int dev_type;//16
		public byte firmware_version[] = new byte[32];
		
		
		public AW_cdr_net_config(byte[] result){
			
			record_quality        =  Packet.byteArrayToShort_Little(result, 0);
			record_duration       =  Packet.byteArrayToShort_Little(result, 4);
			photo_quality         =  Packet.byteArrayToShort_Little(result, 8);
			white_balance         =  Packet.byteArrayToShort_Little(result, 12);
			exposure              =  Packet.byteArrayToShort_Little(result, 16);
			boot_record		      =  Packet.byteArrayToShort_Little(result, 20);
			mute	              =  Packet.byteArrayToShort_Little(result, 24);
			rear_view_mirror      =  Packet.byteArrayToShort_Little(result, 28);
			language	          =  Packet.byteArrayToShort_Little(result, 32);
			smart_detect		  =  Packet.byteArrayToShort_Little(result, 36);
			lane_line_calibration =  Packet.byteArrayToShort_Little(result, 40);
			impact_sensitivity    =  Packet.byteArrayToShort_Little(result, 44);
			motion_detect	      =  Packet.byteArrayToShort_Little(result, 48);
			watermark		      =  Packet.byteArrayToShort_Little(result, 52);
			record_switch	      =  Packet.byteArrayToShort_Little(result, 56);
			dev_type              =  Packet.byteArrayToShort_Little(result, 60);
			System.arraycopy(result, 64, this.firmware_version, 0, this.firmware_version.length);
			
		}


		@Override
		public String toString() {
			
			String versionString = "";
			
			try {
				versionString =  new String(firmware_version,"UTF-8").trim();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return "AW_cdr_net_config [record_quality=" + record_quality
					+ ", record_duration=" + record_duration
					+ ", photo_quality=" + photo_quality + ", white_balance="
					+ white_balance + ", exposure=" + exposure
					+ ", boot_record=" + boot_record + ", mute=" + mute
					+ ", rear_view_mirror=" + rear_view_mirror + ", language="
					+ language + ", smart_detect=" + smart_detect
					+ ", lane_line_calibration=" + lane_line_calibration
					+ ", impact_sensitivity=" + impact_sensitivity
					+ ", motion_detect=" + motion_detect + ", watermark="
					+ watermark + ", record_switch=" + record_switch
					+ ", dev_type=" + dev_type + ", firmware_version="
					+ versionString + "]";
		}
		
		
		
	}
	
	public static final int MAX_PACKET_FILE_LIST_LEN = 992;
	
	public static class AW_cdr_get_file_list{
		
		public int totalCnt;
		public int currentIndex;
		public byte filelist[] = new byte[MAX_PACKET_FILE_LIST_LEN]; 
		
		public AW_cdr_get_file_list(byte[] result){
		
			totalCnt       =  Packet.byteArrayToShort_Little(result, 0);
			currentIndex   =  Packet.byteArrayToShort_Little(result, 4);
			System.arraycopy(result, 8, this.filelist, 0, this.filelist.length);
			
		}

		@Override
		public String toString() {
			
			String listString = "";
			
			try {
				listString = new String(this.filelist,"UTF-8").trim();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return "AW_cdr_get_file_list [totalCnt=" + totalCnt
					+ ", currentIndex=" + currentIndex + ", filelist="
					+ listString + "]";
		}
		
	}
	
	public static class AW_cdr_set_cmd{
		
		public int value;
		public static int getTotalSize(){
			return 4;
		}
		
		public static byte[] combindContent(int value) {
		
			byte[] result = new byte[getTotalSize()];
			byte[] valueByte = Packet.intToByteArray_Little(value);
			System.arraycopy(valueByte, 0, result, 0, 4);
			return result;
		}
		
	}
	
	public static class AW_cdr_cmd_resp{
		
		public int value;
		
		public AW_cdr_cmd_resp(byte[] result){
			value  =  Packet.byteArrayToShort_Little(result, 0);
		}

		@Override
		public String toString() {
			return "AW_cdr_set_cmd_resp [value=" + value + "]";
		}
		
	}
	
	public static class AW_cdr_set_time{
		public	int tm_sec; 		/* seconds */
		public  int tm_min; 		/* minutes */
		public	int tm_hour;		/* hours */
		public	int tm_mday;		/* day of the month */
		public	int tm_mon; 		/* month */
		public	int tm_year;		/* year */
		public	int tm_wday;		/* day of the week */
		public	int tm_yday;		/* day in the year */
		public	int tm_isdst;		/* daylight saving time */
		
		public static int getTotalSize(){
			return 36;
		}
		
		public static byte[] combindContent(int year, int month, int day, int hour, int min, int sec) {
			
			byte[] result = new byte[getTotalSize()];
			byte[] valueSec = Packet.intToByteArray_Little(sec);
			System.arraycopy(valueSec, 0, result, 0, 4);
			byte[] valueMin = Packet.intToByteArray_Little(min);
			System.arraycopy(valueMin, 0, result, 4, 4);
			byte[] valueHour = Packet.intToByteArray_Little(hour);
			System.arraycopy(valueHour, 0, result, 8, 4);
			byte[] valueDay = Packet.intToByteArray_Little(day);
			System.arraycopy(valueDay, 0, result, 12, 4);
			byte[] valueMonth = Packet.intToByteArray_Little(month);
			System.arraycopy(valueMonth, 0, result, 16, 4);
			byte[] valueYear = Packet.intToByteArray_Little(year);
			System.arraycopy(valueYear, 0, result, 20, 4);
			byte[] valueWDay = Packet.intToByteArray_Little(0);
			System.arraycopy(valueWDay, 0, result, 24, 4);
			byte[] valueYDay = Packet.intToByteArray_Little(0);
			System.arraycopy(valueYDay, 0, result, 28, 4);
			byte[] valueIsdst = Packet.intToByteArray_Little(0);
			System.arraycopy(valueIsdst, 0, result, 32, 4);
			return result;
		}
		
	}
	
	public static class AW_cdr_tf_capacity{
		public long remain;
		public long total;
		
		public AW_cdr_tf_capacity(byte[] result){
			remain  =  Packet.byteArrayToLong_Little(result, 0);
			total   =  Packet.byteArrayToLong_Little(result, 8);
		}

		@Override
		public String toString() {
			return "AW_cdr_tf_capacity [remain=" + remain + ", total=" + total
					+ "]";
		}
		
	}
	
	public static class AW_cdr_file_trans{
		
		int  from_to;
		byte filename[] 	= new byte[256];
		int  local_port;	
		byte local_ip[] 	= new byte[20];
		int  remote_port;
		byte remote_ip[]    = new byte[20];
		public byte url[]   = new byte[512];
		
		public static int getTotalSize(){
			return 820;
		}
		
		public static byte[] combindContent(byte[] filename) {

			byte[] result = new byte[getTotalSize()];
			System.arraycopy(filename,     0, result,  4, filename.length);
			return result;
		}
		
		public AW_cdr_file_trans(byte[] result){
			System.arraycopy(result, 308, url, 0, url.length);
		}

		
		@Override
		public String toString() {
			
			String filenameStr = "";
			String urlStr = "";
			try {
				filenameStr = new String(this.filename,"UTF-8").trim();
				urlStr      = new String(this.url,"UTF-8").trim();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return "AW_cdr_file_trans [filename=" + filenameStr
					+ ", url=" + urlStr + "]";
		}
		
		
	}
	
	public static class  AW_cdr_handle_file{
		
		public byte fileName[]    = new byte[256];
		
		public static int getTotalSize(){
			return 256;
		}
		
		public static byte[] combindContent(byte[] filename) {
			byte[] result = new byte[getTotalSize()];
			System.arraycopy(filename,     0, result,  0, filename.length);
			return result;
		}
		
		public AW_cdr_handle_file(byte[] result){
			System.arraycopy(result, 0, fileName, 0, 256);
		}
		
	}
	
	public static class AW_cdr_wifi_setting{
		
		byte[] old_pwd = new byte[64];
		byte[] new_pwd = new byte[64];
		
		public static int getTotalSize(){
			return 192;
		}

		public static byte[] combindContent(byte[] ssid, byte[] oldPwd, byte[] newPwd) {
			byte[] result = new byte[getTotalSize()];
			System.arraycopy(ssid,       0, result,          0,  ssid.length);
			System.arraycopy(oldPwd,     0, result,      64, oldPwd.length);
			System.arraycopy(newPwd,     0, result,       128, newPwd.length);
			return result;
		}
		
	}
	/**
	 * ota升级传输的数据结构
	 * @author AW-Roy
	 *
	 */
	public static class AW_cdr_ota_adress{
		public byte[] ipadress = new byte[64];
		public byte[] port	   = new byte[64];

		public static int getTotalSize() {
			return 128;
		}
		public static byte [] combindContent(byte[] ipadress, byte [] port){
			byte[] resultArr = new byte[getTotalSize()];
			System.arraycopy(ipadress, 0, resultArr, 0, ipadress.length);
			System.arraycopy(port, 0, resultArr, 64, port.length);
			return resultArr;
		}
	}

}
