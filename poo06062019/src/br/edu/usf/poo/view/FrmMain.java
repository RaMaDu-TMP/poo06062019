package br.edu.usf.poo.view;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class FrmMain extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static FrmMain instance;
	
	public static FrmMain gi() {
		
		if (instance == null) {
			instance = new FrmMain();
		}
		
		return instance;
	}
	
	private FrmMain() {
		initComponents();
	}

	private void initComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setMinimumSize(new Dimension(1280, 720));
		
		getContentPane().add(new JLabel("MENU"));
		
		pack();
		setLocationRelativeTo(null);
	}
}
