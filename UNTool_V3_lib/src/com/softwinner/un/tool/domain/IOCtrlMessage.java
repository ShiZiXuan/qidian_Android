package com.softwinner.un.tool.domain;

import java.io.UnsupportedEncodingException;

public class IOCtrlMessage {
	
	int     sid;
	int     IOCtrlType;
	int     IOCtrlDataSize;
	byte[]  IOCtrlData;
	
	public IOCtrlMessage() {
		super();
		// TODO Auto-generated constructor stub
	}

	public IOCtrlMessage(
							int    sid, 
						    int    iOCtrlType, 
						    byte[] iOCtrlData,
						    int    iOCtrlDataSize) 
	{
		super();
		this.sid            = sid;
		this.IOCtrlType     = iOCtrlType;
		this.IOCtrlDataSize = iOCtrlDataSize;
		this.IOCtrlData     = iOCtrlData;
	}
	
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public int getIOCtrlType() {
		return IOCtrlType;
	}
	public void setIOCtrlType(int iOCtrlType) {
		IOCtrlType = iOCtrlType;
	}
	
	public byte[] getIOCtrlData() {
		return IOCtrlData;
	}

	public void setIOCtrlData(byte[] iOCtrlData) {
		IOCtrlData = iOCtrlData;
	}

	public int getIOCtrlDataSize() {
		return IOCtrlDataSize;
	}
	public void setIOCtrlDataSize(int iOCtrlDataSize) {
		IOCtrlDataSize = iOCtrlDataSize;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String dataStr = "";
		try {
			if (IOCtrlData != null) {
				dataStr = new String(IOCtrlData,"UTF-8").trim();
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String content = 
				  "[sid="            + this.sid                   + "]" +
				  "[IOCtrlType="     + this.IOCtrlType            + "]" +
				  "[IOCtrlData="     + dataStr                    + "]" +
				  "[IOCtrlDataSize=" + this.IOCtrlDataSize        + "]";
		
		return content;
		
	}
}