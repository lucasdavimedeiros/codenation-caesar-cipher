package dev.codenation.caesar.cipher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.CodeSource;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONObject;
import org.json.JSONTokener;

public class JsonFile {

	private static final Logger LOGGER = Logger.getLogger(JsonFile.class.getName());

	public static final String FILE = "answer.json";

	public static void saveFile(final JSONObject json) {

		try (final FileWriter file = new FileWriter(FILE)) {

			file.write(json.toString());
			file.flush();

		} catch (final IOException e) {
			LOGGER.log(Level.WARNING, "Cannot save file", e);
		}
	}

	public static JSONObject getJsonFromFile() {

		final StringBuilder sb = new StringBuilder();

		try (final BufferedReader reader = new BufferedReader(new FileReader(getFilePath()))) {
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}

			final JSONTokener jsonTokener = new JSONTokener(sb.toString());
			return new JSONObject(jsonTokener);

		} catch (final Exception e) {
			LOGGER.log(Level.WARNING, "Cannot find the file " + FILE, e);
		}
		return null;
	}

	public static String getJarDir() {
		try {
			final CodeSource codeSource = JsonFile.class.getProtectionDomain().getCodeSource();
			final File jarFile = new File(codeSource.getLocation().toURI().getPath());
			return jarFile.getParentFile().getPath();
		} catch (final URISyntaxException e) {
			LOGGER.log(Level.WARNING, "Cannot find the JAR location", e);
		}
		return null;
	}

	public static String getFilePath() {
		return getJarDir() + "/" + FILE;
	}

	private JsonFile() {
	}
}
