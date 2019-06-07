package br.edu.usf.poo.models;

public class Skate {

	private Integer codSkate;
	private Integer codLixa;
	private Integer codRoda;
	private Integer codRolamento;
	private Integer codShape;
	private Integer codTruck;
	private Integer userId;

	public Integer getCodSkate() {
		return codSkate;
	}

	public void setCodSkate(Integer codSkate) {
		this.codSkate = codSkate;
	}

	public Integer getCodLixa() {
		return codLixa;
	}

	public void setCodLixa(Integer codLixa) {
		this.codLixa = codLixa;
	}

	public Integer getCodRoda() {
		return codRoda;
	}
	
	public void setCodRoda(Integer codRoda) {
		this.codRoda = codRoda;
	}
	
	public Integer getCodRolamento() {
		return codRolamento;
	}

	public void setCodRolamento(Integer codRolamento) {
		this.codRolamento = codRolamento;
	}

	public Integer getCodShape() {
		return codShape;
	}

	public void setCodShape(Integer codShape) {
		this.codShape = codShape;
	}

	public Integer getCodTruck() {
		return codTruck;
	}

	public void setCodTruck(Integer codTruck) {
		this.codTruck = codTruck;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Skate() {
		super();
	}
	
}
