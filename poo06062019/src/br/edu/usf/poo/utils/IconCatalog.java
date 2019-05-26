package br.edu.usf.poo.utils;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class IconCatalog {
	private static IconCatalog instance;
	
	private static final String GLOBAL_PATH = "/br/edu/usf/poo/icon/";
	
	public static IconCatalog gi() {
		
		if (instance == null) {
			instance = new IconCatalog();
		}
		
		return instance;
	}
	
	public Icon getIcon(String iconName) {
		return new ImageIcon(getClass().getResource(GLOBAL_PATH + iconName));
	}
	
	public Icon getIcon(String iconName, int width, int height) {
		return getScaledImage(getIcon(iconName), width, height);
	}
	
	private Icon getScaledImage(Icon icon, int width, int height) {
		
		BufferedImage bufferedIcon = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = bufferedIcon.createGraphics();
		icon.paintIcon(null, g2d, 0, 0);
		g2d.dispose();
			
		BufferedImage resizedIcon = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		g2d = resizedIcon.createGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2d.drawImage(bufferedIcon, 0, 0, width, height, null);
		g2d.dispose();

		return new ImageIcon(resizedIcon);
	}
	
}
