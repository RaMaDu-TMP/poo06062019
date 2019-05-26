
package br.edu.usf.poo.controller;

import java.sql.SQLException;

import javax.swing.JOptionPane;

public class AppController {
	private static AppController instance;
	
	public static AppController gi() {

		if (instance == null) {
			instance = new AppController();
		}
		
		return instance;
	}
	
	private AppController() {
		super();
	}
	
	private void shutdown() {
		try {
			DataBase.close();
			
		} catch (SQLException e) {}
		
		System.exit(0);
	}
	
	public void loginDialogClossing() {
		if (!LoginController.gi().hasUserLoggedIn()) {
			shutdown();
		}
	}

	public void dbConnectionError(Exception e) {
		String message = "Erro ao estabelecer conexão com o banco de dados.\n"
				+ "A aplicação será fechada";
		String title = "Erro de conexão";
		
		JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
		
		shutdown();
	}
	
}
