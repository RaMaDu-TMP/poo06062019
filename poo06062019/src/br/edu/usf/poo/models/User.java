package br.edu.usf.poo.models;

public class User {

	private int userid;
	private String nome;
	private String login;
	
	public User(int userid, String nome, String login) {
		this.userid = userid;
		this.nome = nome;
		this.login = login; 
	}
	
	public int getUserid() {
		return userid;
	}

	public String getNome() {
		return nome;
	}
	
	public String getLogin() {
		return login;
	}
	
}
