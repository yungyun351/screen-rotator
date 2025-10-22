package com.marttapps.screenrotator.model.enums;

/**
 * 顯示器方向
 */
public enum WinDisplayModeOrientation {

	/** 預設方向0度 */
	DMDO_DEFAULT(0, "0°"),
	/** 順時針旋轉90度 */
	DMDO_90(1, "90°"),
	/** 順時針旋轉180度 */
	DMDO_180(2, "180°"),
	/** 順時針旋轉270度 */
	DMDO_270(3, "270°"),
	//
	;

	private WinDisplayModeOrientation(int code, String text) {
		this.code = code;
		this.text = text;
	}

	private final int code;
	private final String text;

	public int getCode() {
		return code;
	}

	public String getText() {
		return text;
	}

}
