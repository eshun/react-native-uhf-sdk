package com.example.serialportuhf.aidl;
import com.example.serialportuhf.aidl.IUhfListener;

interface IUhfService
{
	boolean isPowerOn();
	void powerOn();
	void powerOff();
	void write(in byte[] cmd);
	void setListener(IUhfListener listener);
}