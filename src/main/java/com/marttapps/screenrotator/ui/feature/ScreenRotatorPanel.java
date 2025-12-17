package com.marttapps.screenrotator.ui.feature;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import com.marttapps.screenrotator.model.bean.DeviceInfo;
import com.marttapps.screenrotator.model.enums.WinDisplayModeOrientation;
import com.marttapps.screenrotator.service.DeviceService;
import com.marttapps.screenrotator.service.StartupShortcutService;
import com.marttapps.screenrotator.util.ApplicationPropUtil;
import com.marttapps.screenrotator.util.DialogUtil;
import com.marttapps.screenrotator.util.PathUtil;

public class ScreenRotatorPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	/** 監聽事件id-F1 */
	private static final String ACTION_F1_PRESSED = "f1-pressed";
	/** 監聽事件id-F2 */
	private static final String ACTION_F2_PRESSED = "f2-pressed";

	private JComboBox<DeviceInfo> screenCombo;

	public ScreenRotatorPanel() {
		super();
		render();
	}

	@Override
	public void addNotify() {
		super.addNotify();
		registerKeyBinding();
	}

	@Override
	public void removeNotify() {
		unregisterKeyBinding();
		super.removeNotify();
	}

	private void render() {
		setLayout(new BorderLayout());

		DeviceService deviceService = DeviceService.INSTANCE;
		StartupShortcutService startupShortcutService = StartupShortcutService.INSTANCE;

		// 檢核裝置
		List<DeviceInfo> devices = deviceService.findDeviceInfo();
		if (devices == null || devices.isEmpty()) {
			DialogUtil.showInfoDialog("未偵測到螢幕裝置");
			return;
		}

		String fontName = "Noto Sans TC";
		Font labelStyle = new Font(fontName, Font.BOLD, 20);
		Font contentStyle = new Font(fontName, Font.PLAIN, 18);
		Font tipStyle = new Font(fontName, Font.ITALIC, 16);
		Font buttonStyle = new Font(fontName, Font.BOLD, 16);

		// ----- 內容區 -----
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		contentPanel.setBorder(BorderFactory.createEmptyBorder(30, 20, 5, 20));

		// 選擇螢幕
		JPanel screenRow = new JPanel();
		screenRow.setLayout(new BoxLayout(screenRow, BoxLayout.X_AXIS));
		JLabel labelSelectDevice = new JLabel("選擇螢幕：");
		labelSelectDevice.setFont(labelStyle);
		screenRow.add(labelSelectDevice);

		screenCombo = new JComboBox<>(devices.toArray(DeviceInfo[]::new));
		screenCombo.setFont(contentStyle);
		screenCombo.setMaximumSize(new Dimension(Integer.MAX_VALUE, screenCombo.getPreferredSize().height));
		screenCombo.setRenderer((list, value, index, isSelected, cellHasFocus) -> {
			JLabel label = new JLabel();
			if (value != null) {
				String monitorDeviceID = value.getMonitorDeviceID();
				String[] parts = monitorDeviceID.split("\\\\");
				String text = String.format("%s (%s x %s)", parts[1], value.getWidth(), value.getHeight());
				label.setText(text);
				label.setFont(contentStyle);
			}
			if (isSelected) {
				label.setBackground(list.getSelectionBackground());
				label.setForeground(list.getSelectionForeground());
				label.setOpaque(true);
			}
			return label;
		});
		screenRow.add(screenCombo);
		contentPanel.add(screenRow);
		contentPanel.add(Box.createVerticalStrut(10));

		// 旋轉角度
		JPanel orientateRow = new JPanel();
		orientateRow.setLayout(new BoxLayout(orientateRow, BoxLayout.X_AXIS));
		JLabel labelSelectOrientate = new JLabel("旋轉角度：");
		labelSelectOrientate.setFont(labelStyle);
		orientateRow.add(labelSelectOrientate);

		JComboBox<WinDisplayModeOrientation> orientateCombo = new JComboBox<>(WinDisplayModeOrientation.values());
		orientateCombo.setFont(contentStyle);
		orientateCombo.setMaximumSize(new Dimension(Integer.MAX_VALUE, orientateCombo.getPreferredSize().height));
		orientateCombo.setRenderer((list, value, index, isSelected, cellHasFocus) -> {
			JLabel label = new JLabel();
			if (value != null) {
				label.setText(value.getText());
				label.setFont(contentStyle);
			}
			if (isSelected) {
				label.setBackground(list.getSelectionBackground());
				label.setForeground(list.getSelectionForeground());
				label.setOpaque(true);
			}
			return label;
		});
		orientateRow.add(orientateCombo);
		contentPanel.add(orientateRow);
		contentPanel.add(Box.createVerticalStrut(10));

		// 按F1逆時針旋轉 / 按F2逆時針旋轉
		JPanel tipRow = new JPanel(new BorderLayout());
		JLabel tipLabel = new JLabel("F1順時針旋轉 / F2逆時針旋轉", SwingConstants.CENTER);
		tipLabel.setFont(tipStyle);
		tipLabel.setForeground(Color.getHSBColor(Float.valueOf("32"), Float.valueOf("0.63"), Float.valueOf("0.87")));
		tipRow.add(tipLabel, BorderLayout.CENTER);
		contentPanel.add(tipRow);
		contentPanel.add(Box.createVerticalStrut(10));

		// 其餘設定
		JPanel settingRow = new JPanel(new BorderLayout());
		JCheckBox autoStartCheckbox = new JCheckBox("開機自動啟用系統匣");
		String appName = ApplicationPropUtil.get("application", "name");
		autoStartCheckbox.setSelected(startupShortcutService.isExist(appName));
		settingRow.add(autoStartCheckbox);
		contentPanel.add(settingRow);
		contentPanel.add(Box.createVerticalStrut(10));

		add(contentPanel, BorderLayout.CENTER);

		// ----- 底部按鈕區 -----
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 10, 20));

		JButton applyButton = new JButton("套用");
		applyButton.setFont(buttonStyle);
		applyButton.addActionListener(e -> {
			// 螢幕旋轉
			String deviceName = ((DeviceInfo) screenCombo.getSelectedItem()).getDeviceName();
			int orientation = ((WinDisplayModeOrientation) orientateCombo.getSelectedItem()).getCode();
			deviceService.rotate(deviceName, orientation);

			// 開機自動啟用系統匣
			if (autoStartCheckbox.isSelected()) {
				startupShortcutService.add(appName, PathUtil.getCurrentExecutablePath());
			} else {
				startupShortcutService.remove(appName);
			}
		});
		buttonPanel.add(applyButton);

		add(buttonPanel, BorderLayout.SOUTH);
	}

	/**
	 * 按F1逆時針旋轉 / 按F2逆時針旋轉
	 */
	private void registerKeyBinding() {
		JRootPane rootPane = SwingUtilities.getRootPane(this);
		if (rootPane == null)
			return;

		DeviceService deviceService = DeviceService.INSTANCE;

		rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW) //
				.put(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0), ACTION_F1_PRESSED);
		rootPane.getActionMap().put(ACTION_F1_PRESSED, new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DeviceInfo selected = (DeviceInfo) screenCombo.getSelectedItem();
				deviceService.rotatePre(selected.getDeviceName());
			}
		});
		rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW) //
				.put(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0), ACTION_F2_PRESSED);
		rootPane.getActionMap().put(ACTION_F2_PRESSED, new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DeviceInfo selected = (DeviceInfo) screenCombo.getSelectedItem();
				deviceService.rotateNext(selected.getDeviceName());
			}
		});
	}

	private void unregisterKeyBinding() {
		JRootPane root = SwingUtilities.getRootPane(this);
		if (root == null)
			return;

		InputMap im = root.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap am = root.getActionMap();

		im.remove(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
		im.remove(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0));
		am.remove(ACTION_F1_PRESSED);
		am.remove(ACTION_F2_PRESSED);
	}

}
