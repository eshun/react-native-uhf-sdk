package com.boogoob.uhf.aidl;
import com.boogoob.uhf.aidl.IUhfListener;

interface IUhfService
{
	boolean isPowerOn();
	void powerOn();
	void powerOff();
	void write(in byte[] cmd);
	void setListener(IUhfListener listener);
}