package com.marttapps.screenrotator.ui.feature;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import com.marttapps.screenrotator.model.bean.DeviceInfo;
import com.marttapps.screenrotator.model.constants.UiConstants;
import com.marttapps.screenrotator.model.enums.WinDisplayModeOrientation;
import com.marttapps.screenrotator.service.DeviceService;
import com.marttapps.screenrotator.service.StartupShortcutService;
import com.marttapps.screenrotator.ui.component.LabelComboBox;
import com.marttapps.screenrotator.util.ApplicationPropUtil;
import com.marttapps.screenrotator.util.PathUtil;

/** 螢幕旋轉功能 */
public class ScreenRotatorPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	/** 監聽事件id-F1 */
	private static final String ACTION_F1_PRESSED = "f1-pressed";
	/** 監聽事件id-F2 */
	private static final String ACTION_F2_PRESSED = "f2-pressed";

	/** 選擇螢幕欄位 */
	private LabelComboBox<DeviceInfo> screenComboBox;

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

		Box content = Box.createVerticalBox();
		content.setBorder(BorderFactory.createEmptyBorder(30, 20, 5, 20));

		int errorSize = 18;
		int comboBoxWidth = 320;
		int comboBoxLabelSize = 20;
		Font defaultStyle = new Font(UiConstants.STYLE_DEFAULT_FONT_NAME, Font.PLAIN,
				UiConstants.STYLE_DEFAULT_FONT_SIZE);

		// 檢核裝置
		List<DeviceInfo> devices = DeviceService.INSTANCE.findDeviceInfo();
		if (devices == null || devices.isEmpty()) {
			JLabel error = new JLabel("未偵測到螢幕裝置。");
			error.setFont(new Font(UiConstants.STYLE_DEFAULT_FONT_NAME, Font.BOLD, errorSize));
			error.setForeground(Color.RED);
			content.add(error);
			add(content);
			return;
		}

		screenComboBox = new LabelComboBox<>(comboBoxWidth, "選擇螢幕:", devices.toArray(DeviceInfo[]::new),
				comboBoxLabelSize);
		screenComboBox.setComboBoxRenderer((list, value, index, isSelected, cellHasFocus) -> {
			JLabel label = new JLabel();
			if (value != null) {
				String monitorDeviceID = value.getMonitorDeviceID();
				String[] parts = monitorDeviceID.split("\\\\");
				String text = String.format("%s (%s x %s)", parts[1], value.getWidth(), value.getHeight());
				label.setText(text);
				label.setFont(defaultStyle);
			}
			if (isSelected) {
				label.setBackground(list.getSelectionBackground());
				label.setForeground(list.getSelectionForeground());
				label.setOpaque(true);
			}
			return label;
		});
		content.add(screenComboBox);
		content.add(Box.createVerticalStrut(10));

		LabelComboBox<WinDisplayModeOrientation> orientateComboBox = new LabelComboBox<>(comboBoxWidth, "旋轉角度:",
				WinDisplayModeOrientation.values(), comboBoxLabelSize);
		orientateComboBox.setComboBoxRenderer((list, value, index, isSelected, cellHasFocus) -> {
			JLabel label = new JLabel();
			if (value != null) {
				label.setText(value.getText());
				label.setFont(defaultStyle);
			}
			if (isSelected) {
				label.setBackground(list.getSelectionBackground());
				label.setForeground(list.getSelectionForeground());
				label.setOpaque(true);
			}
			return label;
		});
		content.add(orientateComboBox);

		content.add(Box.createVerticalStrut(10));

		// 按F1逆時針旋轉 / 按F2逆時針旋轉
		JPanel tipRow = new JPanel(new BorderLayout());
		JLabel tipLabel = new JLabel("F1順時針旋轉 / F2逆時針旋轉", SwingConstants.CENTER);
		tipLabel.setFont(defaultStyle);
		tipLabel.setForeground(Color.getHSBColor(Float.valueOf("32"), Float.valueOf("0.63"), Float.valueOf("0.87")));
		tipRow.add(tipLabel, BorderLayout.CENTER);
		content.add(tipRow);
		content.add(Box.createVerticalStrut(10));

		// 其餘設定
		JPanel settingRow = new JPanel(new BorderLayout());
		JCheckBox autoStartCheckbox = new JCheckBox("開機自動啟用系統匣");
		autoStartCheckbox.setFont(defaultStyle);
		String appName = ApplicationPropUtil.get("application", "name");
		autoStartCheckbox.setSelected(StartupShortcutService.INSTANCE.isExist(appName));
		settingRow.add(autoStartCheckbox);
		content.add(settingRow);
		content.add(Box.createVerticalStrut(10));

		add(content, BorderLayout.CENTER);

		// ----- 底部按鈕區 -----
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 10, 20));

		JButton applyButton = new JButton("套用");
		applyButton
				.setFont(new Font(UiConstants.STYLE_DEFAULT_FONT_NAME, Font.BOLD, UiConstants.STYLE_DEFAULT_FONT_SIZE));
		applyButton.addActionListener(e -> {
			// 螢幕旋轉
			String deviceName = screenComboBox.getSelectedItem().getDeviceName();
			int orientation = orientateComboBox.getSelectedItem().getCode();
			DeviceService.INSTANCE.rotate(deviceName, orientation);

			// 開機自動啟用系統匣
			if (autoStartCheckbox.isSelected()) {
				StartupShortcutService.INSTANCE.add(appName, PathUtil.getCurrentExecutablePath());
			} else {
				StartupShortcutService.INSTANCE.remove(appName);
			}
		});
		buttonPanel.add(applyButton);

		add(buttonPanel, BorderLayout.SOUTH);
	}

	/**
	 * 註冊按鍵監聽
	 */
	private void registerKeyBinding() {
		JRootPane rootPane = SwingUtilities.getRootPane(this);
		if (rootPane == null)
			return;

		InputMap inputMap = rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap actionMap = rootPane.getActionMap();

		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0), ACTION_F1_PRESSED);
		actionMap.put(ACTION_F1_PRESSED, new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DeviceInfo selected = screenComboBox.getSelectedItem();
				DeviceService.INSTANCE.rotatePre(selected.getDeviceName());
			}
		});

		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0), ACTION_F2_PRESSED);
		actionMap.put(ACTION_F2_PRESSED, new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DeviceInfo selected = screenComboBox.getSelectedItem();
				DeviceService.INSTANCE.rotateNext(selected.getDeviceName());
			}
		});
	}

	/**
	 * 取消註冊按鍵監聽
	 */
	private void unregisterKeyBinding() {
		JRootPane rootPane = SwingUtilities.getRootPane(this);
		if (rootPane == null)
			return;

		InputMap inputMap = rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap actionMap = rootPane.getActionMap();

		inputMap.remove(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
		actionMap.remove(ACTION_F1_PRESSED);

		inputMap.remove(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0));
		actionMap.remove(ACTION_F2_PRESSED);
	}

}
