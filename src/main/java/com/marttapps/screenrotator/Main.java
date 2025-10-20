package com.marttapps.screenrotator;

import com.marttapps.screenrotator.service.TrayAppService;

public class Main {

	public static void main(String[] args) {
		TrayAppService.INSTANCE.init();
	}

}
