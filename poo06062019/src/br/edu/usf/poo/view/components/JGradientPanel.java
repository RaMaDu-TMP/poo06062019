
package br.edu.usf.poo.view.components;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;

import javax.swing.JPanel;

public class JGradientPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5773243201280095527L;
	
	private Color topLeftColor		 = Color.MAGENTA;
	private Color bottomRightColor	 = Color.BLUE;
    
	public JGradientPanel() {
		super();
	}

    @Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		
		int w = getWidth();
		int h = getHeight();

		Point p1 = new Point(0, 0);
		Point p2 = new Point(w, h);
		
		GradientPaint gp = new GradientPaint(p1, topLeftColor, p2, bottomRightColor);

		g2d.setPaint(gp);
		g2d.fillRect(0, 0, w, h);
	}

	public Color getTopLeftColor() {
		return topLeftColor;
	}
	
	public void setTopLeftColor(Color topLeftColor) {
		this.topLeftColor = topLeftColor;
	}

	public Color getBottomRightColor() {
		return bottomRightColor;
	}
	
	public void setBottomRightColor(Color bottomRightColor) {
		this.bottomRightColor = bottomRightColor;
	}

}
