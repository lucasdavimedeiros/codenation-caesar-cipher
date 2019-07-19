package dev.codenation.caesar.cipher;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.DatatypeConverter;

public class Sha1Cipher {

	private static final Logger LOGGER = Logger.getLogger(Sha1Cipher.class.getName());

	public static String getHash(final String text) {
		final byte[] textBytes = text.getBytes();
		String hash = null;
		try {
			final MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
			sha1.update(textBytes);
			final byte[] digested = sha1.digest();
			hash = DatatypeConverter.printHexBinary(digested).toLowerCase();
		} catch (final NoSuchAlgorithmException e) {
			LOGGER.log(Level.WARNING, "SHA-1 encrypt error", e);
		}

		return hash;
	}

	private Sha1Cipher() {
	}
}
