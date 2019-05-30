package br.edu.usf.poo.controller;

import br.edu.usf.poo.models.User;

public class LoginController {
	private static LoginController instance;
	
	private User currentUser;
	
	public static LoginController gi() {
		
		if (instance == null) {
			instance = new LoginController();
		}
		
		return instance;
	}
	
	private LoginController() {
		super();
	}
	
	public boolean login(String login, String password) {
		if (currentUser != null) {
			throw new UnsupportedOperationException("Can't change user");
		}
		
		currentUser = DataBase.gi().validateLogin(login, password);
		return currentUser != null;
	}
	
	public User getCurrentUser() {
		return currentUser;
	}
	
	public boolean hasUserLoggedIn() {
		return currentUser != null;
	}
	
}
