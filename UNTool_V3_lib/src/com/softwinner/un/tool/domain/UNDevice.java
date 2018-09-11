package com.softwinner.un.tool.domain;

public class UNDevice {

	public static final int 	STATE_INIT          = 0;
	public static final int 	STATE_FAIL          = -1;
	public static final int    	STATE_CONNECTING    = 1;
	public static final int    	STATE_SUCCESS       = 2;
	public static final String 	DEFAULT_PWD			= "12345";   
	

	private String 		uid;
	private String 		password;
	private int    		sid;
	private String      ip;
	private int         port = -1;
	
	/**
	 * connectState
	 * 0  init;
	 * -1 connect fail;
	 * 1  connecting;
	 * 2  connect  success
	 */
	private int     state;

	
	@Override
	public String toString() {
		return "UNDevice [uid=" + uid + ", password=" + password + ", sid="
				+ sid + ", ip=" + ip + ", port=" + port + ", state=" + state
				+ "]";
	}

	public UNDevice(String uid, String password, int sid, String ip, int port,
			int state) {
		super();
		this.uid = uid;
		this.password = password;
		this.sid = sid;
		this.ip = ip;
		this.port = port;
		this.state = state;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
	
	
	
}
