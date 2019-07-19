package dev.codenation.caesar.cipher;

import java.util.Scanner;

import org.json.JSONObject;

public class Main {

	public static void main(String[] args) {

		System.out.println("======================================================================");
		System.out.println("|\\                                                                  /|");
		System.out.println("|\\                                                                  /|");
		System.out.println("|\\                Codenation Caesar Cipher Challenge                /|");
		System.out.println("|\\                                                                  /|");
		System.out.println("|\\                                          by: Lucas Davi Medeiros /|");
		System.out.println("======================================================================");

		Scanner scanner;
		String token;
		boolean isValidToken = false;
		do {
			System.out.println();
			System.out.print("USER TOKEN: ");
			scanner = new Scanner(System.in);
			token = scanner.nextLine().trim();

			System.out.println();

			JSONObject jsonObject = JsonContent.getJsonFromUrl(token);

			if (jsonObject == null || jsonObject.has("code") && "not_found".equals(jsonObject.get("code"))) {
				System.err.println("INVALID TOKEN!");
			} else {
				isValidToken = true;
				JsonFile.saveFile(jsonObject);

				jsonObject = JsonFile.getJsonFromFile();
				final JSONObject jsonEdit = jsonObject;

				final String decrypted = Cipher.decrypt(jsonEdit.get("cifrado").toString(),
						Integer.parseInt(jsonEdit.get("numero_casas").toString()));

				System.out.println("DECRYPTED TEXT: " + decrypted);
				System.out.println();

				jsonObject.put("decifrado", decrypted);

				JsonFile.saveFile(jsonObject);

				final String sha1 = Sha1Cipher.getHash(decrypted);

				System.out.println("SHA-1: " + sha1);

				jsonObject.put("resumo_criptografico", sha1);

				JsonFile.saveFile(jsonObject);

				UploadJsonFile.doPost(token);
			}
		} while (!isValidToken);

		scanner.close();
	}
}
