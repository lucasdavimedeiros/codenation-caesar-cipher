package dev.codenation.caesar.cipher;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class UploadJsonFile {

	private static final Logger LOGGER = Logger.getLogger(UploadJsonFile.class.getName());

	public static void doPost(final String token) {

		try (CloseableHttpClient httpclient = HttpClients.createDefault()) {

			final File file = new File(JsonFile.getFilePath());

			final HttpEntity data = MultipartEntityBuilder.create().setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
					.addBinaryBody("answer", file, ContentType.MULTIPART_FORM_DATA, file.getName()).build();

			final HttpUriRequest request = RequestBuilder
					.post("https://api.codenation.dev/v1/challenge/dev-ps/submit-solution?token=" + token)
					.setEntity(data).build();

			final ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

				@Override
				public String handleResponse(final HttpResponse response) throws IOException {
					final int statusCode = response.getStatusLine().getStatusCode();
					final HttpEntity entity = response.getEntity();
					if (entity == null) {
						throw new ClientProtocolException("Response returned no content");
					}
					if (statusCode == 429) {
						LOGGER.log(Level.INFO, "Wait at least 1 minute to send the file again");
					}
					if (statusCode == HttpURLConnection.HTTP_OK) {
						return EntityUtils.toString(entity);
					} else {
						throw new ClientProtocolException("Unexpected response status: " + statusCode);
					}
				}
			};

			final String responseBody = httpclient.execute(request, responseHandler);
			System.out.println();
			System.out.println("SCORE: " + JsonContent.getScore(responseBody));
			System.out.println();
		} catch (final IOException e) {
			LOGGER.log(Level.WARNING, "Cannot submit the file", e);
		}

	}

	private UploadJsonFile() {
	}
}
