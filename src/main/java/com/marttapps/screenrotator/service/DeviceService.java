package com.marttapps.screenrotator.service;

import java.util.List;

import com.marttapps.screenrotator.model.bean.DeviceInfo;
import com.marttapps.screenrotator.service.impl.DeviceServiceImpl;

public interface DeviceService {

	final DeviceService INSTANCE = new DeviceServiceImpl();

	/**
	 * 查詢顯示設備資訊
	 * 
	 * @return 顯示設備資訊
	 */
	List<DeviceInfo> findDeviceInfo();

	/**
	 * 顯示器旋轉
	 * 
	 * @param deviceName  顯示設備名稱
	 * @param orientation 顯示器方向
	 */
	void rotate(String deviceName, int orientation);

	/**
	 * 顯示器順時針旋轉 90 度
	 * 
	 * @param deviceName 顯示設備名稱
	 */
	void rotateNext(String deviceName);

	/**
	 * 顯示器逆時針旋轉 90 度
	 * 
	 * @param deviceName 顯示設備名稱
	 */
	void rotatePre(String deviceName);

}
