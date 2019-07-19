package dev.codenation.caesar.cipher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONObject;

public class JsonContent {

	private static final Logger LOGGER = Logger.getLogger(JsonContent.class.getName());

	public static JSONObject getJsonFromUrl(final String token) {

		JSONObject json = null;

		try {
			final URL url = new URL("https://api.codenation.dev/v1/challenge/dev-ps/generate-data?token=" + token);
			final HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				final InputStream stream = connection.getInputStream();
				final BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
				final String jsonString = reader.readLine();
				json = new JSONObject(jsonString);
			} else if (connection.getResponseCode() == HttpURLConnection.HTTP_NOT_FOUND) {
				final InputStream stream = connection.getErrorStream();
				final BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
				final String jsonString = reader.readLine();
				json = new JSONObject(jsonString);
			} else {
				LOGGER.log(Level.WARNING, "Unexpected response from server. " + "Status code: "
						+ connection.getResponseCode() + ". Message: " + connection.getResponseMessage() + "\n");
			}
		} catch (final IOException e) {
			LOGGER.log(Level.WARNING, "Cannot get Json from server", e);
		}

		return json;
	}

	public static String getScore(final String jsonScore) {
		final JSONObject jsonObject = new JSONObject(jsonScore);
		return jsonObject.get("score").toString();
	}

	private JsonContent() {
	}
}
