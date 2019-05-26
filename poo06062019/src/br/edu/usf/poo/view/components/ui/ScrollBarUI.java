package br.edu.usf.poo.view.components.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class ScrollBarUI extends BasicScrollBarUI {

	private static final int ROUND_SIZE = 10;
	private static final int SIZE = 12;

	private final Dimension dimension = new Dimension(0, 0);

	public static void config(JScrollPane scrPane) {
		if (scrPane != null) {
			scrPane.setComponentZOrder(scrPane.getVerticalScrollBar(), 0);
			scrPane.setComponentZOrder(scrPane.getViewport(), 1);
			scrPane.setComponentZOrder(scrPane.getHorizontalScrollBar(), 0);

			scrPane.getVerticalScrollBar().setOpaque(true);
			scrPane.getHorizontalScrollBar().setOpaque(true);

			scrPane.getVerticalScrollBar()
					.setPreferredSize(new Dimension(SIZE, scrPane.getHorizontalScrollBar().getPreferredSize().height));
			scrPane.getHorizontalScrollBar()
					.setPreferredSize(new Dimension(scrPane.getHorizontalScrollBar().getPreferredSize().width, SIZE));

			scrPane.getVerticalScrollBar().setUI(new ScrollBarUI());
			scrPane.getHorizontalScrollBar().setUI(new ScrollBarUI());
			
			scrPane.addMouseWheelListener((e) -> {
				int extent = scrPane.getVerticalScrollBar().getModel().getExtent();

				if (scrPane.getVerticalScrollBar().getValue() + extent == scrPane.getVerticalScrollBar().getMaximum()
						|| (scrPane.getVerticalScrollBar().getValue()) == scrPane.getVerticalScrollBar().getMinimum())
				{
					scrPane.getParent().dispatchEvent(e);
				}
			});
		}
	}
	
	// "Remove" Buttons
	@Override
	protected JButton createIncreaseButton(int orientation) {
		return new JButton() {
			private static final long serialVersionUID = -5990190421241069174L;

			@Override
			public Dimension getPreferredSize() {
				return dimension;
			}
		};
	}

	@Override
	protected JButton createDecreaseButton(int orientation) {
		return new JButton() {
			private static final long serialVersionUID = 1955463751276983863L;

			@Override
			public Dimension getPreferredSize() {
				return dimension;
			}
		};
	}

	@Override
	protected void paintThumb(Graphics g, JComponent c, Rectangle r) {

		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		Color thumbColor;

		JScrollBar sb = (JScrollBar) c;

		if (r.isEmpty() || !sb.isEnabled() || !sb.isVisible()) {
			return;
		} else if (isDragging || isThumbRollover()) {
			thumbColor = Color.DARK_GRAY;
		} else {
			thumbColor = Color.GRAY;
		}

		switch (sb.getOrientation()) {

		case VERTICAL:
			g2d.setPaint(thumbColor);
			g2d.fillRoundRect(r.x + 1, r.y, r.width - 2, r.height, ROUND_SIZE, ROUND_SIZE);
			break;

		case HORIZONTAL:
			g2d.setPaint(thumbColor);
			g2d.fillRoundRect(r.x, r.y + 1, r.width, r.height - 2, ROUND_SIZE, ROUND_SIZE);
			break;
		}
		g2d.dispose();
	}

	@Override
	protected void paintTrack(Graphics g, JComponent c, Rectangle r) {

		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		Color trackColor = new Color(244, 244, 244);

		g2d.setPaint(trackColor);
		g2d.fillRect(r.x, r.y, r.width, r.height);

		g2d.dispose();
	}

}
