package com.boogoob.uhf.aidl;

interface IUhfListener
{
	void onPowerOn();
	void onPowerOff();
	void onResponse(in byte[] resp);
}