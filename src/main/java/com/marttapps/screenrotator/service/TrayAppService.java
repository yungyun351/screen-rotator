package com.marttapps.screenrotator.service;

import com.marttapps.screenrotator.service.impl.TrayAppServiceImpl;

public interface TrayAppService {

	final TrayAppService INSTANCE = new TrayAppServiceImpl();

	void init();

}
