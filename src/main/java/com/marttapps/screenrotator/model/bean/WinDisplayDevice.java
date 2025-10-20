package com.marttapps.screenrotator.model.bean;

import com.sun.jna.Structure;

@Structure.FieldOrder({ "cb", "DeviceName", "DeviceString", "StateFlags", "DeviceID", "DeviceKey" })
public class WinDisplayDevice extends Structure {
	public int cb;
	public byte[] DeviceName = new byte[32];
	public byte[] DeviceString = new byte[128];
	public int StateFlags;
	public byte[] DeviceID = new byte[128];
	public byte[] DeviceKey = new byte[128];
}
