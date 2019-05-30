package br.edu.usf.poo.main;

import java.awt.EventQueue;

import javax.swing.UIManager;

import br.edu.usf.poo.controller.DataBase;
import br.edu.usf.poo.view.DlgLogin;

public class Main {

	public static void main(String[] args) {
		
		// For development only
		System.setProperty("poo.login", "admin");
		System.setProperty("poo.password", "admin");
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		EventQueue.invokeLater(() -> {
			DlgLogin.gi().setVisible(true);
			
			DataBase.gi();
		});
	}
}
