package dev.codenation.caesar.cipher;

public class Cipher {

	public static String decrypt(final String encrypted, int displacement) {

		if (displacement > 26) {
			displacement = displacement % 26;
		}
		if (displacement < 0) {
			displacement = (displacement % 26) + 26;
		}

		final String lowerCaseText = encrypted.toLowerCase();

		final StringBuilder sb = new StringBuilder();

		for (int i = 0; i < encrypted.length(); i++) {

			final char anyChar = lowerCaseText.charAt(i);

			if (Character.isLetter(anyChar)) {

				final char letterChar = (char) (anyChar - displacement);

				if (letterChar < 'a') {
					sb.append((char) (anyChar + (26 - displacement)));
				} else {
					sb.append(letterChar);
				}
			} else {
				sb.append(anyChar);
			}
		}

		return sb.toString();
	}

	private Cipher() {
	}
}
