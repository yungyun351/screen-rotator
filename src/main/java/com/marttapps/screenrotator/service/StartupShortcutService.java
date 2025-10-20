package com.marttapps.screenrotator.service;

import com.marttapps.screenrotator.service.impl.StartupShortcutServiceImpl;

public interface StartupShortcutService {

	final StartupShortcutService INSTANCE = new StartupShortcutServiceImpl();

	String getStartupFolder();

	boolean isExist(String name);

	void add(String name, String exePath);

	void remove(String name);

}
