package br.edu.usf.poo.models;

import com.google.common.base.Strings;

public class Rolamento extends SkatePart {

	@Override
	protected String getPrefix() {
		return "Rolamento" + (Strings.isNullOrEmpty(getDesc()) ? "" : " de");
	}

}
