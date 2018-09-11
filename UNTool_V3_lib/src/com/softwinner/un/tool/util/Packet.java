package com.softwinner.un.tool.util;

/**
 * 文件名:Packet.java V2.0 Feb 2,2015<br>
 * 描　述:网络数据类型转换<br> 
 * 版　权:AW-BU3-PD2<br>
 * @author PD2-Roy
 */
public class Packet {

	public static final short byteArrayToShort_Little(byte byt[], int nBeginPos) {
		return (short) ((0xff & byt[nBeginPos]) | ((0xff & byt[nBeginPos + 1]) << 8));
	}

	public static final int byteArrayToInt_Little(byte byt[], int nBeginPos) {
		return (0xff & byt[nBeginPos]) | (0xff & byt[nBeginPos + 1]) << 8 | (0xff & byt[nBeginPos + 2]) << 16 | (0xff & byt[nBeginPos + 3]) << 24;
	}

	public static final int byteArrayToInt_Little(byte byt[]) {
		if (byt.length == 1)
			return 0xff & byt[0];
		else if (byt.length == 2)
			return (0xff & byt[0]) | ((0xff & byt[1]) << 8);
		else if (byt.length == 4)
			return (0xff & byt[0]) | (0xff & byt[1]) << 8 | (0xff & byt[2]) << 16 | (0xff & byt[3]) << 24;
		else
			return 0;
	}

	public static final long byteArrayToLong_Little(byte byt[], int nBeginPos) {
		return (0xff & byt[nBeginPos]) | (0xff & byt[nBeginPos + 1]) << 8 | (0xff & byt[nBeginPos + 2]) << 16 | (0xff & byt[nBeginPos + 3]) << 24
				| (0xff & byt[nBeginPos + 4]) << 32 | (0xff & byt[nBeginPos + 5]) << 40 | (0xff & byt[nBeginPos + 6]) << 48 | (0xff & byt[nBeginPos + 7]) << 56;
	}

	public static final int byteArrayToInt_Big(byte byt[]) {
		if (byt.length == 1)
			return 0xff & byt[0];
		else if (byt.length == 2)
			return (0xff & byt[0]) << 8 | 0xff & byt[1];
		else if (byt.length == 4)
			return (0xff & byt[0]) << 24 | (0xff & byt[1]) << 16 | (0xff & byt[2]) << 8 | 0xff & byt[3];
		else
			return 0;
	}

	public static final byte[] longToByteArray_Little(long value) {
		return new byte[] { (byte) value, (byte) (value >>> 8), (byte) (value >>> 16), (byte) (value >>> 24), (byte) (value >>> 32), (byte) (value >>> 40),
				(byte) (value >>> 48), (byte) (value >>> 56) };
	}

	public static final byte[] intToByteArray_Little(int value) {
		return new byte[] { (byte) value, (byte) (value >>> 8), (byte) (value >>> 16), (byte) (value >>> 24) };
	}

	public static final byte[] intToByteArray_Big(int value) {
		return new byte[] { (byte) (value >>> 24), (byte) (value >>> 16), (byte) (value >>> 8), (byte) value };
	}

	public static final byte[] shortToByteArray_Little(short value) {
		return new byte[] { (byte) value, (byte) (value >>> 8) };
	}

	public static final byte[] shortToByteArray_Big(short value) {
		return new byte[] { (byte) (value >>> 8), (byte) value };
	}

}