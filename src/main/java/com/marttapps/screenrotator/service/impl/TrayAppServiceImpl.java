package com.marttapps.screenrotator.service.impl;

import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.formdev.flatlaf.IntelliJTheme;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.marttapps.screenrotator.Main;
import com.marttapps.screenrotator.service.ScreenSelectorUIService;
import com.marttapps.screenrotator.service.TrayAppService;

import dorkbox.systemTray.Menu;
import dorkbox.systemTray.MenuItem;
import dorkbox.systemTray.Separator;
import dorkbox.systemTray.SystemTray;

public class TrayAppServiceImpl implements TrayAppService {

	@Override
	public void init() {
		initGlobalStyle();

		SystemTray tray = SystemTray.get();
		tray.setTooltip("螢幕旋轉工具");

		Image icon = Toolkit.getDefaultToolkit().createImage(getClass().getResource("/img/icon.png"));
		tray.setImage(icon);

		Menu menu = tray.getMenu();

		MenuItem rotateSelect = new MenuItem("開啟工具", event -> ScreenSelectorUIService.INSTANCE.init());
		menu.add(rotateSelect);

		menu.add(new Separator());

		MenuItem exitItem = new MenuItem("結束", e -> System.exit(0));
		menu.add(exitItem);

		ScreenSelectorUIService.INSTANCE.init();
	}

	private void initGlobalStyle() {
		try {
			UIManager.setLookAndFeel(new FlatMacDarkLaf());
			IntelliJTheme.setup(Main.class.getResourceAsStream("/theme/Cobalt_2.theme.json"));
		} catch (UnsupportedLookAndFeelException e) {
			JOptionPane.showMessageDialog(null, "視窗樣式設定失敗: " + e.getStackTrace());
		}
	}

}
