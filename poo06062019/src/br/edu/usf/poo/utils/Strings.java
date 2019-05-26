package br.edu.usf.poo.utils;

public class Strings {

	private Strings() {
		throw new UnsupportedOperationException("Can't instantiate class Strings");
	}
	
	public static boolean isNullOrEmpty(String text) {
		if (text == null) {
			return true;
		}
		
		return text.trim().length() == 0;
	}
}
