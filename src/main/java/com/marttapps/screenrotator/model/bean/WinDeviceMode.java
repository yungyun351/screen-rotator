package com.marttapps.screenrotator.model.bean;

import com.sun.jna.Structure;

@Structure.FieldOrder({ "dmDeviceName", "dmSpecVersion", "dmDriverVersion", "dmSize", "dmDriverExtra", "dmFields",
		"dmPositionX", "dmPositionY", "dmDisplayOrientation", "dmDisplayFixedOutput", "dmColor", "dmDuplex",
		"dmYResolution", "dmTTOption", "dmCollate", "dmFormName", "dmLogPixels", "dmBitsPerPel", "dmPelsWidth",
		"dmPelsHeight", "dmDisplayFlags", "dmDisplayFrequency", "dmICMMethod", "dmICMIntent", "dmMediaType",
		"dmDitherType", "dmReserved1", "dmReserved2", "dmPanningWidth", "dmPanningHeight" })
public class WinDeviceMode extends Structure {

	public short dmSize;

	public byte[] dmDeviceName = new byte[32];
	public short dmSpecVersion;
	public short dmDriverVersion;
	public short dmDriverExtra;
	public int dmFields;

	public int dmPositionX;
	public int dmPositionY;
	public int dmDisplayOrientation;
	public int dmDisplayFixedOutput;

	public short dmColor;
	public short dmDuplex;
	public short dmYResolution;
	public short dmTTOption;
	public short dmCollate;
	public byte[] dmFormName = new byte[32];
	public short dmLogPixels;
	public int dmBitsPerPel;
	public int dmPelsWidth;
	public int dmPelsHeight;
	public int dmDisplayFlags;
	public int dmDisplayFrequency;
	public int dmICMMethod;
	public int dmICMIntent;
	public int dmMediaType;
	public int dmDitherType;
	public int dmReserved1;
	public int dmReserved2;
	public int dmPanningWidth;
	public int dmPanningHeight;

	public WinDeviceMode() {
		dmSize = (short) size();
	}

}
