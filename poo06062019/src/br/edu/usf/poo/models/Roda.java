package br.edu.usf.poo.models;

import com.google.common.base.Strings;

public class Roda extends SkatePart {

	@Override
	protected String getPrefix() {
		return "Roda" + (Strings.isNullOrEmpty(getDesc()) ? "" : " de");
	}

}
