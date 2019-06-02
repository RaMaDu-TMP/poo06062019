package br.edu.usf.poo.models;

import com.google.common.base.Strings;

import br.edu.usf.poo.client.MarcaClient;

public abstract class SkatePart {

	private int codLixa;
	private int codMarca;
	private String desc;
	private float preco;

	public int getCod() {
		return codLixa;
	}

	public void setCod(int codLixa) {
		this.codLixa = codLixa;
	}

	public int getCodMarca() {
		return codMarca;
	}

	public void setCodMarca(int codMarca) {
		this.codMarca = codMarca;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public float getPreco() {
		return preco;
	}

	public void setPreco(float preco) {
		this.preco = preco;
	}

	@Override
	public String toString() {
		String ret = "";
		
		Marca marca = MarcaClient.gi().getMarcaByID(getCodMarca());
		if (marca != null) {
			ret += marca.getNome() + " - ";
		}
		
		return ret + getPrefix() + getSulfix();
	}

	private String getSulfix() {
		return " " + (Strings.isNullOrEmpty(getDesc()) ? "sem descrição" : getDesc());
	}
	
	protected abstract String getPrefix();
	
	public SkatePart() {
		super();
	}
}
