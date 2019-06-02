package br.edu.usf.poo.models;

public class Marca {

	private int cod;
	private String nome;
	
	public int getCod() {
		return cod;
	}
	public void setCod(int cod) {
		this.cod = cod;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Marca() {
		super();
	}
	
	public Marca(int cod, String nome) {
		this();
		setCod(cod);
		setNome(nome);
	}
	
}
