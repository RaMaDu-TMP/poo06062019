
package br.edu.usf.poo.controller;

import javax.swing.JOptionPane;

import br.edu.usf.poo.view.DlgLogin;
import br.edu.usf.poo.view.FrmMain;

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
			
		} catch (Exception e) {}
		
		System.exit(0);
	}
	
	public void loginDialogClossing() {
		if (!LoginController.gi().hasUserLoggedIn()) {
			shutdown();
		}
	}

	public void dbConnectionError(Exception e) {
		String title = "Erro de conexão";
		String message = "Erro ao estabelecer conexão com o banco de dados.\n"
				+ "A aplicação será fechada";
		
		JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
		
		shutdown();
	}

	public void loadMainScreen() {
		DlgLogin.gi().dispose();
		
		FrmMain.gi().setVisible(true);
	}
	
}
