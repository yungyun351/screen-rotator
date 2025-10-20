package com.marttapps.screenrotator.service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.marttapps.screenrotator.service.StartupShortcutService;

public class StartupShortcutServiceImpl implements StartupShortcutService {

	private static final String STARTUP_FOLDER = System.getenv("APPDATA")
			+ "\\Microsoft\\Windows\\Start Menu\\Programs\\Startup";

	@Override
	public String getStartupFolder() {
		return STARTUP_FOLDER;
	}

	@Override
	public boolean isExist(String fileName) {
		File shortcutFile = new File(getShortcutPath(fileName));
		return shortcutFile.exists();
	}

	@Override
	public void add(String fileName, String exePath) {
		try {
			String shortcutPath = getShortcutPath(fileName);
			String script = String.format("""
					Set oWS = WScript.CreateObject("WScript.Shell")
					sLinkFile = "%s"
					Set oLink = oWS.CreateShortcut(sLinkFile)
					oLink.TargetPath = "%s"
					oLink.WindowStyle = 1
					oLink.Save
					""", shortcutPath, exePath);

			File tempVbs = File.createTempFile("create_shortcut", ".vbs");
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempVbs))) {
				writer.write(script);
			}

			Runtime.getRuntime().exec(new String[] { "wscript", tempVbs.getAbsolutePath() });
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void remove(String fileName) {
		File shortcut = new File(getShortcutPath(fileName));
		if (shortcut.exists()) {
			shortcut.delete();
		}
	}

	private String getShortcutPath(String fileName) {
		return String.format("%s\\%s.lnk", STARTUP_FOLDER, fileName);
	}

}
