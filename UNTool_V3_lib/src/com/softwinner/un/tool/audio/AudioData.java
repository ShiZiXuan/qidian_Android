package com.softwinner.un.tool.audio;

/**
 * 文件名:AudioData.java V0.5 May 5,2014<br>
 * 描　述:音频数据类实体<br>
 * 版　权:珠海全志科技BU3-PD2<br>
 * @author PD2-赵井满、陈永煌、彭罗榕
 */
public class AudioData {
	int size;
	byte[] realData;

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public byte[] getRealData() {
		return realData;
	}

	public void setRealData(byte[] realData) {
		this.realData = realData;
	}

}
