package com.marttapps.screenrotator.service;

import java.util.List;

import com.marttapps.screenrotator.model.bean.WinDeviceMode;
import com.marttapps.screenrotator.model.bean.WinDisplayDevice;
import com.marttapps.screenrotator.service.impl.ScreenRotatorServiceImpl;

public interface ScreenRotatorService {

	final ScreenRotatorService INSTANCE = new ScreenRotatorServiceImpl();

	int getOrientation(String deviceName);

	WinDeviceMode getDeviceMode(String deviceName);

	List<WinDisplayDevice> getDisplayDevice();

	void rotate(String deviceName, int orientation);

}
