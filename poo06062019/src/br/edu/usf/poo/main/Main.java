package br.edu.usf.poo.main;

import java.awt.EventQueue;

import javax.swing.UIManager;

import br.edu.usf.poo.controller.DataBase;
import br.edu.usf.poo.view.DlgLogin;

public class Main {

	public static void main(String[] args) {
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		EventQueue.invokeLater(() -> {
			DlgLogin dlgLogin = new DlgLogin();
			dlgLogin.setVisible(true);
			
			DataBase.gi();
		});
	}
}
