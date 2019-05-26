package br.edu.usf.poo.view.components.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.plaf.basic.BasicTextFieldUI;
import javax.swing.text.JTextComponent;

import br.edu.usf.poo.utils.Strings;

public class HintTextFieldUI extends BasicTextFieldUI implements FocusListener {

	private String hintText;
    private Color hintColor;
    private Font hintFont;
    private boolean hideHintOnFocus;
    
	public HintTextFieldUI(String hintText) {
		this(hintText, Color.GRAY);
	}

	public HintTextFieldUI(String hintText, Color color) {
		this(hintText, color, null);
	}
	
	public HintTextFieldUI(String hintText, Color color, Font hintFont) {
		this.hintText = hintText;
		this.hintColor = color;
		this.hintFont = hintFont;
	}

    @Override
	protected void paintSafely(Graphics g) {
    	try {
    		super.paintSafely(g);
			
		} catch (Exception e) {}
    	
    	JTextComponent comp = getComponent();
    	
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		if (!Strings.isNullOrEmpty(hintText) && Strings.isNullOrEmpty(comp.getText())) {

			if (hideHintOnFocus && comp.hasFocus()) {
				return;
			}
			
			if (hintFont != null) {
				g2d.setFont(hintFont);
			}
			
			g2d.setColor(hintColor);
			
			int padding = (comp.getHeight() / 2) + (comp.getFont().getSize() / 2);
			
			int x = 2;
			int y = padding - 2;
			
			g2d.drawString(hintText, x, y);
		}
	}
    
	@Override
	public void focusGained(FocusEvent e) {
		refresh();
	}

	@Override
	public void focusLost(FocusEvent e) {
		refresh();
	}
	
	@Override
	protected void installListeners() {
		super.installListeners();
		getComponent().addFocusListener(this);
	}

	@Override
	protected void uninstallListeners() {
		super.uninstallListeners();
		getComponent().removeFocusListener(this);
	}
	
	private void refresh() {
		if (getComponent() != null) {
			getComponent().repaint();
		}
	}
	
	public Color getHintColor() {
		return hintColor;
	}

	public void setHintColor(Color color) {
		this.hintColor = color;
		refresh();
	}
	
	public String getHintText() {
		return hintText;
	}

	public void setHintText(String hintText) {
		this.hintText = hintText;
		refresh();
	}
	
	public Font getHintFont() {
		return hintFont;
	}
	
	public void setHintFont(Font hintFont) {
		this.hintFont = hintFont;
	}
	
	public boolean isHideHintOnFocus() {
		return hideHintOnFocus;
	}
	
	public void setHideHintOnFocus(boolean hideHintOnFocus) {
		this.hideHintOnFocus = hideHintOnFocus;
	}
	
}
