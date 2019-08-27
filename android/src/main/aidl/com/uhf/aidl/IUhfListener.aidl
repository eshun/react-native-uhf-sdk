package com.uhf.aidl;

interface IUhfListener
{
	void onPowerOn();
	void onPowerOff();
	void onResponse(in byte[] resp);
}