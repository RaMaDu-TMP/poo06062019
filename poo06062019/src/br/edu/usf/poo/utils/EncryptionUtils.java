package br.edu.usf.poo.utils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptionUtils {
	
	private EncryptionUtils() {
		throw new UnsupportedOperationException("Can't instantiate class Strings");
	}
	
	public static String toSHA256Hash(String toConvert) throws NoSuchAlgorithmException {
		MessageDigest digestSHA256 = MessageDigest.getInstance("SHA-256");
		digestSHA256.reset();
		digestSHA256.update(toConvert.getBytes(StandardCharsets.UTF_8));

		byte[] digest = digestSHA256.digest();

		BigInteger bigInt = new BigInteger(1, digest);

		String hashtext = bigInt.toString(16);
		return hashtext;
	}
	
	// TODO
	public static String fromSHA256Hash(String toConvert) throws NoSuchAlgorithmException {
//		MessageDigest digestSHA256 = MessageDigest.getInstance("SHA-256");
//		digestSHA256.reset();
		return null;
	}

}
