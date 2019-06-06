package br.edu.usf.poo.models;

public class Skate {

	private int codSkate;
	private int codLixa;
	private int codRoda;
	private int codRolamento;
	private int codShape;
	private int codTruck;
	private int userId;

	public int getCodSkate() {
		return codSkate;
	}

	public void setCodSkate(int codSkate) {
		this.codSkate = codSkate;
	}

	public int getCodLixa() {
		return codLixa;
	}

	public void setCodLixa(int codLixa) {
		this.codLixa = codLixa;
	}

	public int getCodRoda() {
		return codRoda;
	}
	
	public void setCodRoda(int codRoda) {
		this.codRoda = codRoda;
	}
	
	public int getCodRolamento() {
		return codRolamento;
	}

	public void setCodRolamento(int codRolamento) {
		this.codRolamento = codRolamento;
	}

	public int getCodShape() {
		return codShape;
	}

	public void setCodShape(int codShape) {
		this.codShape = codShape;
	}

	public int getCodTruck() {
		return codTruck;
	}

	public void setCodTruck(int codTruck) {
		this.codTruck = codTruck;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Skate() {
		super();
	}
	
}
