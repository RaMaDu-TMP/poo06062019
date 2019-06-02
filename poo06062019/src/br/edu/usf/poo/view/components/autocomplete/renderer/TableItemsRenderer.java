package br.edu.usf.poo.view.components.autocomplete.renderer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.util.Arrays;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;

import com.google.common.base.Strings;
	
public class TableItemsRenderer extends JLabel implements TableCellRenderer {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8929913954629194400L;
	
	private final static Color SELECTED_COLOR = new Color(057, 105, 138);
	
	private final static Color ROW_EVEN		  = new Color(220, 220, 220);
	private final static Color ROW_TWO		  = new Color(240, 240, 240);
	
	private static final Color SELECTED_FOREGROUND	 = Color.WHITE;
	private static final Color DEFAULT_FOREGROUND	 = Color.BLACK;
	
	private static final Font DEFAULT_FONT	  = new Font("Arial", Font.PLAIN, 14);
	
	public TableItemsRenderer() {
		setFont(DEFAULT_FONT);
		setOpaque(true);
		
		setBorder(new EmptyBorder(1, 2, 1, 2));
		
		setVerticalAlignment(JLabel.CENTER);
	}
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

		if (isSelected) {
			setForeground(SELECTED_FOREGROUND);
			setBackground(SELECTED_COLOR);

		} else {
			setForeground(DEFAULT_FOREGROUND);

			if (row % 2 == 0) {
				setBackground(ROW_TWO);
			} else {
				setBackground(ROW_EVEN);
			}
		}
		
		String text = value.toString();
		
		if (text.startsWith("Lavagem ou Ocultação de Bens, Direitos e Valores Provenientes de Crime")) {
			text = "Lavagem ou Ocultação de Bens, Direitos e Valores Provenientes<br> de Crime";
		}
		
		int width = table.getColumnModel().getColumn(column).getWidth();
		
		if (width > 0 && !Strings.isNullOrEmpty(text)) {
			FontMetrics fm = getFontMetrics(getFont());
			
			if (fm.stringWidth(text) > width) {
				Arrays.asList(text.toCharArray());
			}
//				char[] charArray = text.toCharArray();
//				int w = 0;
//				int c = 0;
//				for (char ch : charArray) {
//					w += fm.charWidth(ch);
//					if (w >= width) {
//						if (ch != ' ') {
//							boolean b = false; 
////							for (int i = charArray.length - 1; i >= 0; i--) {
//								if (charArray[i] == ' ') {
//									text = text.substring(0, i) + "<br>" + text.substring(i, text.length());
//									b = true;
//									break;
//								}
////							}
//							
//							if (b) {
//								break;
//							}
//							
//						} else {
//							text = text.substring(0, c) + "<br>" + text.substring(c, text.length());
//							break;
//						}
//					}
//					c++;
//				}
//
//				System.out.println(text);
//			}
		}
//		
//		setPreferredSize(new Dimension(width, height));
		setText("<html>" + text + "</html>");
		
		return this;
	}

}
