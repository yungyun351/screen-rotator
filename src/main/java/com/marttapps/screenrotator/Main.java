package com.marttapps.screenrotator;

import java.util.Locale;

import javax.swing.SwingUtilities;

public class Main {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new App() //
				.initLocale(Locale.TRADITIONAL_CHINESE) //
				.initTheme("/theme/Cobalt_2.theme.json") //
				.launch());
	}

}
