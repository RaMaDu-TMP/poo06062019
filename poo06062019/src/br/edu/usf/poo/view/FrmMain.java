package br.edu.usf.poo.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import br.edu.usf.poo.controller.SkateController;

import java.awt.FlowLayout;
import javax.swing.JButton;

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
	
	private void createSkate() {
		SkateController.gi().openCreateSkateForm();
	}

	private void initComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(1280, 720));
		setLocationRelativeTo(null);
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(0, 0));
		panel.setBackground(Color.LIGHT_GRAY);
		
		getContentPane().add(panel, BorderLayout.CENTER);
		
		JPanel panel_1 = new JPanel();
		panel_1.setOpaque(false);
		panel.add(panel_1, BorderLayout.CENTER);
		
		JPanel pnlButtons = new JPanel();
		pnlButtons.setBackground(Color.GRAY);
		
		panel.add(pnlButtons, BorderLayout.EAST);
		pnlButtons.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnCreate = new JButton("Criar Skate");
		btnCreate.addActionListener((e) -> {
			createSkate();
		});
		
		pnlButtons.add(btnCreate);
		
		pack();
	}
}
