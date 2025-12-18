package com.marttapps.screenrotator.ui.feature;

import java.awt.BorderLayout;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import com.marttapps.screenrotator.util.ApplicationPropUtil;

public class AppFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	public AppFrame() {
		super(ApplicationPropUtil.get("application", "name"));
		render();
	}

	private void render() {
		setSize(400, 300);
		setLayout(new BorderLayout(10, 10));
		setResizable(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		ImageIcon icon = new ImageIcon(getClass().getResource("/img/icon.png"));
		setIconImage(icon.getImage());

		Box content = Box.createHorizontalBox();
		content.add(new ScreenRotatorPanel());
		add(content);

		setVisible(true);
	}

}
