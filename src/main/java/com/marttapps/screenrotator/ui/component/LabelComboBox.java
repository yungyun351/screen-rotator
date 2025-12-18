package com.marttapps.screenrotator.ui.component;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import com.formdev.flatlaf.FlatClientProperties;
import com.marttapps.screenrotator.model.constants.UiConstants;

/**
 * 選擇器欄位元件
 */
public class LabelComboBox<T> extends JPanel {

	private static final long serialVersionUID = 1L;

	/** 主欄位區域 */
	private Box content;
	/** 標籤 */
	private JLabel label;
	/** 輸入框 */
	private JComboBox<T> comboBox;
	/** 錯誤標籤 */
	private JLabel errorLabel;
	/** 錯誤訊息 */
	private String errorText;

	public LabelComboBox(int width, String labelText, T[] options, int labelSize) {
		this(width, labelText, options, labelSize, 0, "");
	}

	public LabelComboBox(int width, String labelText, T[] options, int labelSize, int errorSize, String errorText) {
		this.errorText = errorText;

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setAlignmentX(Component.CENTER_ALIGNMENT);

		content = Box.createHorizontalBox();
		label = new JLabel(labelText);
		label.setFont(new Font(UiConstants.STYLE_DEFAULT_FONT_NAME, Font.BOLD, labelSize));
		label.setAlignmentY(Component.TOP_ALIGNMENT);
		content.add(label);

		content.add(Box.createHorizontalStrut(10));

		Box inputBox = Box.createVerticalBox();
		inputBox.setAlignmentY(Component.TOP_ALIGNMENT);

		comboBox = new JComboBox<>(options);
		comboBox.setFont(new Font(UiConstants.STYLE_DEFAULT_FONT_NAME, Font.PLAIN, labelSize - 6));
		comboBox.setAlignmentX(Component.LEFT_ALIGNMENT);
		inputBox.add(comboBox);

		errorLabel = new JLabel();
		errorLabel.setFont(new Font(UiConstants.STYLE_DEFAULT_FONT_NAME, Font.PLAIN, errorSize));
		errorLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		errorLabel.setForeground(Color.RED);
		errorLabel.setPreferredSize(new Dimension(0, errorSize + 2));
		inputBox.add(errorLabel);

		content.add(inputBox);

		add(content);

		Dimension dimension = new Dimension(width, getPreferredSize().height);
		setPreferredSize(dimension);
		setMaximumSize(dimension);
		setMinimumSize(dimension);
	}

	/**
	 * 取得選取的項目
	 * 
	 * @return 選取的項目
	 */
	@SuppressWarnings("unchecked")
	public T getSelectedItem() {
		return (T) comboBox.getSelectedItem();
	}

	/**
	 * 設定選取的項目
	 * 
	 * @param item 選取的項目
	 */
	public void setSelectedItem(T item) {
		comboBox.setSelectedItem(item);
	}

	/**
	 * 顯示錯誤訊息
	 */
	public void showError() {
		errorLabel.setText(errorText);
		comboBox.putClientProperty(FlatClientProperties.OUTLINE, FlatClientProperties.OUTLINE_ERROR);
	}

	/**
	 * 設定錯誤訊息並顯示
	 * 
	 * @param message 錯誤訊息
	 */
	public void showError(String message) {
		errorLabel.setText(message);
		comboBox.putClientProperty(FlatClientProperties.OUTLINE, FlatClientProperties.OUTLINE_ERROR);
	}

	/**
	 * 隱藏錯誤訊息
	 */
	public void hideError() {
		errorLabel.setText("");
		comboBox.putClientProperty(FlatClientProperties.OUTLINE, null);
	}

	/**
	 * 取得JComboBox
	 * 
	 * @return JComboBox
	 */
	public JComboBox<T> getComboBox() {
		return comboBox;
	}

	/**
	 * 清空JComboBox
	 */
	public void clearComboBox() {
		comboBox.setSelectedItem(null);
	}

	public void setComboBoxRenderer(ListCellRenderer<? super T> aRenderer) {
		comboBox.setRenderer(aRenderer);
	}
}
