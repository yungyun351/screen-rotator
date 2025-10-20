package com.marttapps.screenrotator.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.marttapps.screenrotator.model.bean.WinDeviceMode;
import com.marttapps.screenrotator.model.bean.WinDisplayDevice;
import com.marttapps.screenrotator.model.constants.WinDisplayConstants;
import com.marttapps.screenrotator.service.ScreenRotatorService;
import com.marttapps.screenrotator.service.WinUser32Service;

public class ScreenRotatorServiceImpl implements ScreenRotatorService {

	public static final int ENUM_CURRENT_SETTINGS = -1;

	@Override
	public int getOrientation(String deviceName) {
		WinDeviceMode dm = getDeviceMode(deviceName);
		return (dm == null) ? 0 : dm.dmDisplayOrientation;
	}

	@Override
	public WinDeviceMode getDeviceMode(String deviceName) {
		WinDeviceMode dm = new WinDeviceMode();
		dm.dmSize = (short) dm.size();
		boolean success = WinUser32Service.INSTANCE.EnumDisplaySettingsA(deviceName, ENUM_CURRENT_SETTINGS, dm);
		return success ? dm : null;
	}

	@Override
	public List<WinDisplayDevice> getDisplayDevice() {
		List<WinDisplayDevice> result = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			WinDisplayDevice dd = new WinDisplayDevice();
			dd.cb = dd.size();
			boolean ok = WinUser32Service.INSTANCE.EnumDisplayDevicesA(null, i, dd, 0);
			if (!ok)
				continue;

			int flags = dd.StateFlags;
			if ((flags & 0x1) == 0)
				continue;

			result.add(dd);
		}
		return result;
	}

	@Override
	public void rotate(String deviceName, int orientation) {
		WinDeviceMode dm = getDeviceMode(deviceName);
		if (dm == null) {
			return;
		}

		// 變更長寬
		if ((orientation + dm.dmDisplayOrientation) % 2 == 1) {
			int temp = dm.dmPelsWidth;
			dm.dmPelsWidth = dm.dmPelsHeight;
			dm.dmPelsHeight = temp;
			dm.dmFields |= WinDisplayConstants.DM_FIELDS_PELSWIDTH | WinDisplayConstants.DM_FIELDS_PELSHEIGHT;
		}

		dm.dmDisplayOrientation = orientation;
		dm.dmFields |= WinDisplayConstants.DM_FIELDS_DISPLAYORIENTATION;

		int result = WinUser32Service.INSTANCE.ChangeDisplaySettingsExA(deviceName, dm, null, 0, null);
		if (result == 0) {
			System.out.println("Display rotation successful!");
		} else {
			System.out.println("Failed to rotate display. Error code: " + result);
		}
	}

}
