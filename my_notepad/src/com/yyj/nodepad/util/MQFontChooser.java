package com.yyj.nodepad.util;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

/**
 * 这是自己找的一份字体选择器的资料
 * 新建一个类引入系统字体的样式包
 * @author Yujie_Yang
 */
public class MQFontChooser extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 选择取消按钮的返回值
	 */
	public static final int CANCEL_OPTION = 0;
	/**
	 * 选择确定按钮的返回值
	 */
	public static final int APPROVE_OPTION = 1;
	/**
	 * 中文预览的字符串
	 */
	private static final String CHINA_STRING = "神马都是浮云！";
	/**
	 * 英文预览的字符串
	 */
	private static final String ENGLISH_STRING = "Hello Kitty！";
	/**
	 * 数字预览的字符串
	 */
	private static final String NUMBER_STRING = "0123456789";
	// 预设字体，也是将来要返回的字体
	private Font font = null;
	// 字体选择器组件容器
	private Box box = null;
	// 字体文本框
	private JTextField fontText = null;
	// 样式文本框
	private JTextField styleText = null;
	// 文字大小文本框
	private JTextField sizeText = null;
	// 预览文本框
	private JTextField previewText = null;
	// 中文预览
	private JRadioButton chinaButton = null;
	// 英文预览
	private JRadioButton englishButton = null;
	// 数字预览
	private JRadioButton numberButton = null;
	// 字体选择框
	@SuppressWarnings("rawtypes")
	private JList fontList = null;
	// 样式选择器
	@SuppressWarnings("rawtypes")
	private JList styleList = null;
	// 文字大小选择器
	@SuppressWarnings("rawtypes")
	private JList sizeList = null;
	// 确定按钮
	private JButton approveButton = null;
	// 取消按钮
	private JButton cancelButton = null;
	// 所有字体
	private String [] fontArray = null;
	// 所有样式
	private String [] styleArray = {"常规", "粗体", "斜体", "粗斜体"};
	// 所有预设字体大小
	private String [] sizeArray = {"8", "9", "10", "11", "12", "14", "16", "18", "20", "22", "24", "26", "28", "36", "48", "初号", "小初", "一号", "小一", "二号", "小二", "三号", "小三", "四号", "小四", "五号", "小五", "六号", "小六", "七号", "八号"};
	// 上面数组中对应的字体大小
	private int [] sizeIntArray = {8, 9, 10, 11, 12, 14, 16, 18, 20, 22, 24, 26, 28, 36, 48, 42, 36, 26, 24, 22, 18, 16, 15, 14, 12, 10, 9, 8, 7, 6, 5};
	// 返回的数值，默认取消
	private int returnValue = CANCEL_OPTION;
	/**
	 * 体构造一个字体选择器
	 */
	public MQFontChooser() {
		this(new Font("宋体", Font.PLAIN, 12));
	}
	/**
	 * 使用给定的预设字体构造一个字体选择器
	 * @param font 字体
	 */
	public MQFontChooser(Font font) {
		setTitle("字体选择器");
		this.font = font;
		// 初始化UI组件
		init();
		// 添加监听器
		addListener();
		// 按照预设字体显示
		setup();
		// 基本设置
		setModal(true);
		setResizable(false);
		// 自适应大小
		pack();
	}
	/**
	 * 初始化组件
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void init(){
		// 获得系统字体
		GraphicsEnvironment eq = GraphicsEnvironment.getLocalGraphicsEnvironment();
		fontArray = eq.getAvailableFontFamilyNames();
		// 主容器
		box = Box.createVerticalBox();
		box.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
		fontText = new JTextField();
		fontText.setEditable(false);
		fontText.setBackground(Color.WHITE);
		styleText = new JTextField();
		styleText.setEditable(false);
		styleText.setBackground(Color.WHITE);
		sizeText = new JTextField("12");
		// 给文字大小文本框使用的Document文档，制定了一些输入字符的规则
		Document doc = new PlainDocument(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void insertString(int offs, String str, AttributeSet a)
					throws BadLocationException {
				if (str == null) {
					return;
				}
				if (getLength() >= 3) {
					return;
				}
				if (!str.matches("[0-9]+") && !str.equals("初号") && !str.equals("小初") && !str.equals("一号") && !str.equals("小一") && !str.equals("二号") && !str.equals("小二") && !str.equals("三号") && !str.equals("小三") && !str.equals("四号") && !str.equals("小四") && !str.equals("五号") && !str.equals("小五") && !str.equals("六号") && !str.equals("小六") && !str.equals("七号") && !str.equals("八号")) {
					return;
				}
				super.insertString(offs, str, a);
				sizeList.setSelectedValue(sizeText.getText(), true);
			}
		};
		sizeText.setDocument(doc);
		previewText = new JTextField(20);
		previewText.setHorizontalAlignment(JTextField.CENTER);
		previewText.setEditable(false);
		previewText.setBackground(Color.WHITE);
		chinaButton = new JRadioButton("中文预览", true);
		englishButton = new JRadioButton("英文预览");
		numberButton = new JRadioButton("数字预览");
		ButtonGroup bg = new ButtonGroup();
		bg.add(chinaButton);
		bg.add(englishButton);
		bg.add(numberButton);
		fontList = new JList(fontArray);
		styleList = new JList(styleArray);
		sizeList = new JList(sizeArray);
		approveButton = new JButton("确定");
		cancelButton = new JButton("取消");
		Box box1 = Box.createHorizontalBox();
		JLabel l1 = new JLabel("字体:");
		JLabel l2 = new JLabel("字形:");
		JLabel l3 = new JLabel("大小:");
		l1.setPreferredSize(new Dimension(165, 14));
		l1.setMaximumSize(new Dimension(165, 14));
		l1.setMinimumSize(new Dimension(165, 14));
		l2.setPreferredSize(new Dimension(95, 14));
		l2.setMaximumSize(new Dimension(95, 14));
		l2.setMinimumSize(new Dimension(95, 14));
		l3.setPreferredSize(new Dimension(80, 14));
		l3.setMaximumSize(new Dimension(80, 14));
		l3.setMinimumSize(new Dimension(80, 14));
		box1.add(l1);
		box1.add(l2);
		box1.add(l3);
		Box box2 = Box.createHorizontalBox();
		fontText.setPreferredSize(new Dimension(160, 20));
		fontText.setMaximumSize(new Dimension(160, 20));
		fontText.setMinimumSize(new Dimension(160, 20));
		box2.add(fontText);
		box2.add(Box.createHorizontalStrut(5));
		styleText.setPreferredSize(new Dimension(90, 20));
		styleText.setMaximumSize(new Dimension(90, 20));
		styleText.setMinimumSize(new Dimension(90, 20));
		box2.add(styleText);
		box2.add(Box.createHorizontalStrut(5));
		sizeText.setPreferredSize(new Dimension(80, 20));
		sizeText.setMaximumSize(new Dimension(80, 20));
		sizeText.setMinimumSize(new Dimension(80, 20));
		box2.add(sizeText);
		Box box3 = Box.createHorizontalBox();
		JScrollPane sp1 = new JScrollPane(fontList);
		sp1.setPreferredSize(new Dimension(160, 100));
		sp1.setMaximumSize(new Dimension(160, 100));
		sp1.setMaximumSize(new Dimension(160, 100));
		box3.add(sp1);
		box3.add(Box.createHorizontalStrut(5));
		JScrollPane sp2 = new JScrollPane(styleList);
		sp2.setPreferredSize(new Dimension(90, 100));
		sp2.setMaximumSize(new Dimension(90, 100));
		sp2.setMinimumSize(new Dimension(90, 100));
		box3.add(sp2);
		box3.add(Box.createHorizontalStrut(5));
		JScrollPane sp3 = new JScrollPane(sizeList);
		sp3.setPreferredSize(new Dimension(80, 100));
		sp3.setMaximumSize(new Dimension(80, 100));
		sp3.setMinimumSize(new Dimension(80, 100));
		box3.add(sp3);
		Box box4 = Box.createHorizontalBox();
		Box box5 = Box.createVerticalBox();
		JPanel box6 = new JPanel(new BorderLayout());
		box5.setBorder(BorderFactory.createTitledBorder("字符集"));
		box6.setBorder(BorderFactory.createTitledBorder("示例"));
		box5.add(chinaButton);
		box5.add(englishButton);
		box5.add(numberButton);
		box5.setPreferredSize(new Dimension(90, 95));
		box5.setMaximumSize(new Dimension(90, 95));
		box5.setMinimumSize(new Dimension(90, 95));
		box6.add(previewText);
		box6.setPreferredSize(new Dimension(250, 95));
		box6.setMaximumSize(new Dimension(250, 95));
		box6.setMinimumSize(new Dimension(250, 95));
		box4.add(box5);
		box4.add(Box.createHorizontalStrut(4));
		box4.add(box6);
		Box box7 = Box.createHorizontalBox();
		box7.add(Box.createHorizontalGlue());
		box7.add(approveButton);
		box7.add(Box.createHorizontalStrut(5));
		box7.add(cancelButton);
		box.add(box1);
		box.add(box2);
		box.add(box3);
		box.add(Box.createVerticalStrut(5));
		box.add(box4);
		box.add(Box.createVerticalStrut(5));
		box.add(box7);
		getContentPane().add(box);
	}
	/**
	 * 按照预设字体显示
	 */
	private void setup() {
		String fontName = font.getFamily();
		int fontStyle = font.getStyle();
		int fontSize = font.getSize();
		/*
		 * 如果预设的文字大小在选择列表中，则通过选择该列表中的某项进行设值，否则直接将预设文字大小写入文本框
		 */
		boolean b = false;
		for (int i = 0; i < sizeArray.length; i++) {
			if (sizeArray[i].equals(String.valueOf(fontSize))) {
				b = true;
				break;
			}
		}
		if(b){
			// 选择文字大小列表中的某项
			sizeList.setSelectedValue(String.valueOf(fontSize), true);
		}else{
			sizeText.setText(String.valueOf(fontSize));
		}
		// 选择字体列表中的某项
		fontList.setSelectedValue(fontName, true);
		// 选择样式列表中的某项
		styleList.setSelectedIndex(fontStyle);
		// 预览默认显示中文字符
		chinaButton.doClick();
		// 显示预览
		setPreview();
	}
	/**
	 * 添加所需的事件监听器
	 */
	private void addListener() {
		sizeText.addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) {
				setPreview();
			}
			public void focusGained(FocusEvent e) {
				sizeText.selectAll();
			}
		});
		// 字体列表发生选择事件的监听器
		fontList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					fontText.setText(String.valueOf(fontList.getSelectedValue()));
					// 设置预览
					setPreview();
				}
			}
		});
		styleList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					styleText.setText(String.valueOf(styleList.getSelectedValue()));
					// 设置预览
					setPreview();
				}
			}
		});
		sizeList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					if(!sizeText.isFocusOwner()){
						sizeText.setText(String.valueOf(sizeList.getSelectedValue()));
					}
					// 设置预览
					setPreview();
				}
			}
		});
		// 编码监听器
		EncodeAction ea = new EncodeAction();
		chinaButton.addActionListener(ea);
		englishButton.addActionListener(ea);
		numberButton.addActionListener(ea);
		// 确定按钮的事件监听
		approveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 组合字体
				font = groupFont();
				// 设置返回值
				returnValue = APPROVE_OPTION;
				// 关闭窗口
				disposeDialog();
			}
		});
		// 取消按钮事件监听
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				disposeDialog();
			}
		});
	}
	/**
	 * 显示字体选择器
	 * @param owner 上层所有者
	 * @return 该整形返回值表示用户点击了字体选择器的确定按钮或取消按钮，参考本类常量字段APPROVE_OPTION和CANCEL_OPTION
	 */
	public final int showFontDialog(JFrame owner) {
		setLocationRelativeTo(owner);
		setVisible(true);
		return returnValue;
	}
	/**
	 * 返回选择的字体对象
	 * @return 字体对象
	 */
	public final Font getSelectFont() {
		return font;
	}
	/**
	 * 关闭窗口
	 */
	private void disposeDialog() {
		MQFontChooser.this.removeAll();
		MQFontChooser.this.dispose();
	}
	
	/**
	 * 显示错误消息
	 * @param errorMessage 错误消息
	 */
	private void showErrorDialog(String errorMessage) {
		JOptionPane.showMessageDialog(this, errorMessage, "错误", JOptionPane.ERROR_MESSAGE);
	}
	/**
	 * 设置预览
	 */
	private void setPreview() {
		Font f = groupFont();
		previewText.setFont(f);
	}
	/**
	 * 按照选择组合字体
	 * @return 字体
	 */
	private Font groupFont() {
		String fontName = fontText.getText();
		int fontStyle = styleList.getSelectedIndex();
		String sizeStr = sizeText.getText().trim();
		// 如果没有输入
		if(sizeStr.length() == 0) {
			showErrorDialog("字体（大小）必须是有效“数值！");
			return null;
		}
		int fontSize = 0;
		// 通过循环对比文字大小输入是否在现有列表内
		for (int i = 0; i < sizeArray.length; i++) {
			if(sizeStr.equals(sizeArray[i])){
				fontSize = sizeIntArray[i];
				break;
			}
		}
		// 没有在列表内
		if (fontSize == 0) {
			try{
				fontSize = Integer.parseInt(sizeStr);
				if(fontSize < 1){
					showErrorDialog("字体（大小）必须是有效“数值”！");
					return null;
				}
			}catch (NumberFormatException nfe) {
				showErrorDialog("字体（大小）必须是有效“数值”！");
				return null;
			}
		}
		return new Font(fontName, fontStyle, fontSize);
	}
	
	/**
	 * 编码选择事件的监听动作
	 *
	 */
	class EncodeAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(chinaButton)) {
				previewText.setText(CHINA_STRING);
			} else if (e.getSource().equals(englishButton)) {
				previewText.setText(ENGLISH_STRING);
			} else {
				previewText.setText(NUMBER_STRING);
			}
		}
	}
}
