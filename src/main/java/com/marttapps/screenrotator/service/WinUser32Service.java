package com.marttapps.screenrotator.service;

import com.marttapps.screenrotator.model.bean.WinDeviceMode;
import com.marttapps.screenrotator.model.bean.WinDisplayDevice;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.win32.StdCallLibrary;

public interface WinUser32Service extends StdCallLibrary {

	final WinUser32Service INSTANCE = Native.load("user32", WinUser32Service.class);

	boolean EnumDisplayDevicesA(String lpDevice, int iDevNum, WinDisplayDevice lpDisplayDevice, int dwFlags);

	boolean EnumDisplaySettingsA(String deviceName, int modeNum, WinDeviceMode devMode);

	int ChangeDisplaySettingsExA(String deviceName, WinDeviceMode devMode, Pointer hwnd, int flags, Pointer lParam);

}
