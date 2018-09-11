package com.softwinner.un.tool.domain;

import java.io.UnsupportedEncodingException;

public class IOCtrlReturnMsg {

//	int sid, byte[] uid, int IOCTRLType, byte[] data, int len
	
	private int      sid;
	private byte[]   uid;
	private int      IOCTRLType;
	private byte[]   data;
	private int      len;
	
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public byte[] getUid() {
		return uid;
	}
	public void setUid(byte[] uid) {
		this.uid = uid;
	}
	public int getIOCTRLType() {
		return IOCTRLType;
	}
	public void setIOCTRLType(int IOCTRLType) {
		this.IOCTRLType = IOCTRLType;
	}
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
	public int getLen() {
		return len;
	}
	public void setLen(int len) {
		this.len = len;
	}
	
	
	public IOCtrlReturnMsg(int sid, byte[] uid, int IOCTRLType, byte[] data,
			int len) {
		super();
		this.sid = sid;
		this.uid = uid;
		this.IOCTRLType = IOCTRLType;
		this.data = data;
		this.len = len;
	}
	
	@Override
	public String toString() {
		
		String uidStr = "";
		String dataStr = "";
		try {
			if (this.uid != null) {
				uidStr  = new String(this.uid,"UTF-8");
			}
			if (this.data != null) {
				dataStr = new String(this.data,"UTF-8");
			}
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return "IOCtrlReturnMsg [sid=" + sid + ", uid=" + uidStr
				+ ", IOCTRLType=" + IOCTRLType + ", data="
				+ dataStr + ", len=" + len + "]";
	}
	
	
	
	
	
	
}
