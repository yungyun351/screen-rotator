package com.marttapps.screenrotator;

import java.awt.Image;
import java.awt.Toolkit;
import java.util.Locale;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.formdev.flatlaf.IntelliJTheme;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.marttapps.screenrotator.ui.feature.AppFrame;
import com.marttapps.screenrotator.util.DialogUtil;

import dorkbox.systemTray.Menu;
import dorkbox.systemTray.MenuItem;
import dorkbox.systemTray.Separator;
import dorkbox.systemTray.SystemTray;

public class App {

	private AppFrame appFrame;

	/**
	 * 初始化語言環境
	 * 
	 * @param locale 語言環境
	 * @return 方法鏈
	 */
	public App initLocale(Locale locale) {
		Locale.setDefault(locale);
		return this;
	}

	/**
	 * 初始化主題樣式
	 * 
	 * @param themePath 主題設定路徑
	 * @return 方法鏈
	 */
	public App initTheme(String themePath) {
		try {
			UIManager.setLookAndFeel(new FlatMacDarkLaf());
			IntelliJTheme.setup(App.class.getResourceAsStream(themePath));
		} catch (UnsupportedLookAndFeelException e) {
			DialogUtil.showErrorDialog("視窗樣式設定失敗。");
		}
		return this;
	}

	/**
	 * 啟動
	 */
	public void launch() {
		SystemTray tray = SystemTray.get();
		tray.setTooltip("螢幕旋轉工具");

		Image icon = Toolkit.getDefaultToolkit().createImage(getClass().getResource("/img/icon.png"));
		tray.setImage(icon);

		Menu menu = tray.getMenu();
		menu.add(new MenuItem("開啟工具", event -> {
			appFrame.setVisible(true);
			appFrame.toFront();
		}));
		menu.add(new Separator());
		menu.add(new MenuItem("結束", e -> System.exit(0)));

		appFrame = new AppFrame();
	}

}
