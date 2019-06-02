package br.edu.usf.poo.models;

import com.google.common.base.Strings;

public class Truck extends SkatePart {

	@Override
	protected String getPrefix() {
		
		return "Truck" + (Strings.isNullOrEmpty(getDesc()) ? "" : " de");
	}

}
