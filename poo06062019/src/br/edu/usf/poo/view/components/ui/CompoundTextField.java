package br.edu.usf.poo.view.components.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.DefaultFocusTraversalPolicy;
import java.awt.Dimension;
import java.awt.FocusTraversalPolicy;
import java.awt.Font;
import java.awt.event.FocusListener;
import java.awt.event.KeyListener;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.Document;

public class CompoundTextField extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 797966949210693916L;
	
	private static final Font DEFAULT_HINT_FONT = new Font("Arial", Font.ITALIC, 12);
	private static final Color DEFAULT_HINT_FOREGROUND = Color.GRAY;
	
	private HintTextFieldUI textFieldUI;
	
	private boolean isPasswordField;
	private boolean hintUppercase;
	
	public CompoundTextField() {
		this(null, null, DEFAULT_HINT_FOREGROUND, DEFAULT_HINT_FONT, false);
	}
	
	public CompoundTextField(Icon icon, String hint, boolean isPasswordField) {
		this(icon, hint, DEFAULT_HINT_FOREGROUND, DEFAULT_HINT_FONT, false);
	}
	
	public CompoundTextField(Icon icon, String hint, Color hintColor, Font hintFont, boolean isPasswordField) {
		initComponents();
		
		this.isPasswordField = isPasswordField;
		
		textFieldUI = new HintTextFieldUI(hint, hintColor, hintFont);
		
		setIcon(icon);
		setHint(hint);
		setHintColor(hintColor);
		setHintFont(hintFont);
		
		createTextComponent();
		
		FocusTraversalPolicy traversalPolicy = new DefaultFocusTraversalPolicy() {
			private static final long serialVersionUID = 1360929224508084816L;
			
			@Override
			public Component getFirstComponent(Container aContainer) {
				return textField;
			}
			
			@Override
			public Component getLastComponent(Container aContainer) {
				return textField;
			}
		};
		
		setFocusTraversalPolicyProvider(true);
		setFocusTraversalPolicy(traversalPolicy);
	}
	
	private void createTextComponent() {
		
		if (isPasswordField) {
			textField = new JPasswordField();
			textFieldUI = new HintPasswordFieldUI(getHint(), getHintColor(), getHintFont());
		} else {
			textField = new JTextField();
			textFieldUI = new HintTextFieldUI(getHint(), getHintColor(), getHintFont());
		}
		
		textField.setUI(textFieldUI);
		textField.setFont(getFont());
		textField.setBorder(null);
		
		add(textField, BorderLayout.CENTER);
	}
	
	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		textField.setEnabled(enabled);
	}
	
	public void setEditable(boolean editable) {
		textField.setEditable(editable);
	}
	
	@Override
	public void setFont(Font font) {
		super.setFont(font);
		
		if (textField != null) {
			textField.setFont(font);
		}
	}
	
	public Font getHintFont() {
		return textFieldUI.getHintFont();
	}
	
	public void setHintFont(Font hintFont) {
		textFieldUI.setHintFont(hintFont);
	}
	
	public Icon getIcon() {
		return lblIcon.getIcon();
	}
	
	public void setIcon(Icon icon) {
		lblIcon.setIcon(icon);
	}
	
	public String getHint() {
		return textFieldUI.getHintText();
	}
	
	public void setHint(String hint) {
		if (hintUppercase) {
			hint = hint.toUpperCase();
		}
		
		textFieldUI.setHintText(hint);
	}
	
	public Color getHintColor() {
		return textFieldUI.getHintColor();
	}
	
	public void setHintColor(Color hintColor) {
		textFieldUI.setHintColor(hintColor);
	}
	
	public String getText() {
		if (textField != null) {
			return textField.getText();
		}
		return null;
	}
	
	public void setText(String text) {
		textField.setText(text);
	}
	
	public boolean isHintUppercase() {
		return hintUppercase;
	}
	
	public void setHintUppercase(boolean hintUppercase) {
		this.hintUppercase = hintUppercase;
		
		setHint(getHint());
		createTextComponent();
	}
	
	public char[] getPassword() {
		if (!(textField instanceof JPasswordField)) {
			throw new IllegalArgumentException("Text component isn't instance of JPasswordField");
		}
		return ((JPasswordField) textField).getPassword();
	}
	
	public boolean isPasswordField() {
		return isPasswordField;
	}
	
	public void setPasswordField(boolean isPasswordField) {
		this.isPasswordField = isPasswordField;
		createTextComponent();
	}
	
	public Dimension getIconDimension() {
		return lblIcon.getPreferredSize();
	}
	
	public void setIconDimension(Dimension dimension) {
		lblIcon.setPreferredSize(dimension);
	}
	
	public Document getDocument() {
		return textField.getDocument();
	}
	
	public void setDocument(Document document) {
		textField.setDocument(document);
	}

	public void setCaretColor(Color color) {
		textField.setCaretColor(color);
	}

	public void setCaretPosition(int position) {
		textField.setCaretPosition(position);
	}
	
	@Override
	public synchronized KeyListener[] getKeyListeners() {
		return textField.getKeyListeners();
	}
	
	@Override
	public synchronized void addKeyListener(KeyListener l) {
		textField.addKeyListener(l);
	}
	
	@Override
	public synchronized FocusListener[] getFocusListeners() {
		return textField.getFocusListeners();
	}
	
	@Override
	public synchronized void addFocusListener(FocusListener l) {
		textField.addFocusListener(l);
	}
	
	@Override
	public void requestFocus() {
		textField.requestFocus();
	}
	
	@Override
	public void setBackground(Color bg) {
		super.setBackground(bg);
		if (textField != null) {
			textField.setBackground(bg);
		}
	}
	
	public boolean isHideHintOnFocus() {
		return textFieldUI.isHideHintOnFocus();
	}
	
	public void setHideHintOnFocus(boolean hideHintOnFocus) {
		textFieldUI.setHideHintOnFocus(hideHintOnFocus);
	}
	
	private void initComponents() {
		lblIcon = new JLabel();
		textField = new JTextField();

		//======== this ========
		setBackground(Color.white);
		setBorder(null);
		setLayout(new BorderLayout());

		//---- lblIcon ----
		lblIcon.setBackground(Color.white);
		lblIcon.setHorizontalAlignment(SwingConstants.CENTER);
		lblIcon.setBorder(null);
		lblIcon.setOpaque(true);
		lblIcon.setFocusable(false);
		add(lblIcon, BorderLayout.WEST);

		//---- textField ----
		textField.setBorder(null);
		add(textField, BorderLayout.CENTER);
	}

	private JLabel lblIcon;
	private JTextField textField;
}
