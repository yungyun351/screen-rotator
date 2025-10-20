package com.marttapps.screenrotator.service;

import com.marttapps.screenrotator.service.impl.ScreenSelectorUIServiceImpl;

public interface ScreenSelectorUIService {

	final ScreenSelectorUIService INSTANCE = new ScreenSelectorUIServiceImpl();

	void init();

}
