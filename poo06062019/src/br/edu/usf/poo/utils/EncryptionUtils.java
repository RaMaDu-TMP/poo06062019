package br.edu.usf.poo.utils;

import java.nio.charset.StandardCharsets;

import com.google.common.hash.Hashing;

public class EncryptionUtils {
	
	private EncryptionUtils() {
		throw new UnsupportedOperationException("Can't instantiate class Strings");
	}
	
	public static String toSHA256Hash(String toConvert) {
		return Hashing.sha256().hashString(toConvert, StandardCharsets.UTF_8).toString();
	}
	
}
