package com.marttapps.screenrotator.model.bean;

public class DeviceInfo {

	/** 顯示設備名稱 */
	private String deviceName;
	/** 顯示卡名稱 */
	private String deviceString;

	/** 顯示設備完整名稱 */
	private String monitorDeviceName;
	/** 顯示器ID */
	private String monitorDeviceID;

	/** 螢幕寬度(像素) */
	private int width;
	/** 螢幕高度(像素) */
	private int height;

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getDeviceString() {
		return deviceString;
	}

	public void setDeviceString(String deviceString) {
		this.deviceString = deviceString;
	}

	public String getMonitorDeviceName() {
		return monitorDeviceName;
	}

	public void setMonitorDeviceName(String monitorDeviceName) {
		this.monitorDeviceName = monitorDeviceName;
	}

	public String getMonitorDeviceID() {
		return monitorDeviceID;
	}

	public void setMonitorDeviceID(String monitorDeviceID) {
		this.monitorDeviceID = monitorDeviceID;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

}
