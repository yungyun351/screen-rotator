package com.marttapps.screenrotator.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.marttapps.screenrotator.model.bean.DeviceInfo;
import com.marttapps.screenrotator.model.bean.WinDeviceMode;
import com.marttapps.screenrotator.model.bean.WinDisplayDevice;
import com.marttapps.screenrotator.model.constants.WinDisplayConstants;
import com.marttapps.screenrotator.service.DeviceService;
import com.marttapps.screenrotator.service.WinUser32Service;

public class DeviceServiceImpl implements DeviceService {

	@Override
	public List<DeviceInfo> findDeviceInfo() {
		List<DeviceInfo> result = new ArrayList<>();

		WinUser32Service winUser32Service = WinUser32Service.INSTANCE;

		int i = 0;
		while (true) {
			WinDisplayDevice displayDevice = new WinDisplayDevice();
			boolean hasNext = winUser32Service.EnumDisplayDevicesA(null, i, displayDevice, 0);
			if (!hasNext) {
				break;
			}

			i++;

			// 判斷是否啟用中
			if ((displayDevice.StateFlags & WinDisplayConstants.DISPLAY_DEVICE_STATE_FLAGS_ACTIVE) == 0)
				continue;

			String deviceName = new String(displayDevice.DeviceName);
			WinDisplayDevice monitorDevice = new WinDisplayDevice();
			winUser32Service.EnumDisplayDevicesA(deviceName, 0, monitorDevice, 0);
			WinDeviceMode deviceMode = findDeviceMode(deviceName);
			DeviceInfo deviceInfo = convertDeviceInfo(displayDevice, monitorDevice, deviceMode);
			result.add(deviceInfo);
		}
		return result;
	}

	@Override
	public void rotate(String deviceName, int orientation) {
		WinDeviceMode dm = findDeviceMode(deviceName);
		if (dm == null)
			return;

		rotate(deviceName, dm, orientation);
	}

	@Override
	public void rotateNext(String deviceName) {
		WinDeviceMode dm = findDeviceMode(deviceName);
		if (dm == null)
			return;

		int nextOrientation = (dm.dmDisplayOrientation + 3) % 4;
		rotate(deviceName, dm, nextOrientation);
	}

	@Override
	public void rotatePre(String deviceName) {
		WinDeviceMode dm = findDeviceMode(deviceName);
		if (dm == null)
			return;

		int nextOrientation = (dm.dmDisplayOrientation + 1) % 4;
		rotate(deviceName, dm, nextOrientation);
	}

	private WinDeviceMode findDeviceMode(String deviceName) {
		WinDeviceMode dm = new WinDeviceMode();
		return WinUser32Service.INSTANCE.EnumDisplaySettingsA(deviceName, -1, dm) ? //
				dm : null;
	}

	private DeviceInfo convertDeviceInfo(WinDisplayDevice displayDevice, WinDisplayDevice monitorDevice,
			WinDeviceMode deviceMode) {
		DeviceInfo deviceInfo = new DeviceInfo();
		deviceInfo.setDeviceName(new String(displayDevice.DeviceName).trim());
		deviceInfo.setDeviceString(new String(displayDevice.DeviceString).trim());
		deviceInfo.setMonitorDeviceName(new String(monitorDevice.DeviceName).trim());
		deviceInfo.setMonitorDeviceID(new String(monitorDevice.DeviceID).trim());
		deviceInfo.setWidth(deviceMode.dmPelsWidth);
		deviceInfo.setHeight(deviceMode.dmPelsHeight);
		return deviceInfo;
	}

	private void rotate(String deviceName, WinDeviceMode deviceMode, int orientation) {
		if (deviceName == null)
			return;

		if (deviceMode == null) {
			deviceMode = findDeviceMode(deviceName);
			if (deviceMode == null)
				return;
		}

		// 變更長寬
		if ((orientation + deviceMode.dmDisplayOrientation) % 2 == 1) {
			int temp = deviceMode.dmPelsWidth;
			deviceMode.dmPelsWidth = deviceMode.dmPelsHeight;
			deviceMode.dmPelsHeight = temp;
			deviceMode.dmFields |= WinDisplayConstants.DEVICE_MODE_FIELDS_PELSWIDTH
					| WinDisplayConstants.DEVICE_MODE_FIELDS_PELSHEIGHT;
		}

		deviceMode.dmDisplayOrientation = orientation;
		deviceMode.dmFields |= WinDisplayConstants.DEVICE_MODE_FIELDS_DISPLAYORIENTATION;

		int result = WinUser32Service.INSTANCE.ChangeDisplaySettingsExA(deviceName, deviceMode, null, 0, null);
		if (result == 0) {
			System.out.println("Display rotation successful!");
		} else {
			System.out.println("Failed to rotate display. Error code: " + result);
		}
	}

}
