package br.fatec.esiv.srcumboard.client;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

/**
 * 
 * @author italosanto
 *
 * @param <DTO> the response entity type, if exists
 */
public abstract class ApiClient<DTO> {

	private Optional<Class<DTO>> responseEntityType;

	@SuppressWarnings("unchecked")
	protected ApiClient() {
		try {
			this.responseEntityType = Optional.of(
					(Class<DTO>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
		} catch (Exception ex) {
			this.responseEntityType = Optional.empty();
		}
	}

	public Optional<DTO> doRequest() throws URISyntaxException, ClientProtocolException, IOException {

		CloseableHttpClient httpClient = HttpClients.createDefault();
		URI uri = new URIBuilder().setCharset(StandardCharsets.UTF_8).setScheme("http").setHost("localhost")
				.setPort(8081).setPath("/scrumboard-api" + getPath())
				.addParameters(getParameters().orElse(new ArrayList<>())).build();

		HttpUriRequest request = getRequest(uri);
		CloseableHttpResponse response = httpClient.execute(request);

		StatusLine statusLine = response.getStatusLine();
		if (statusLine.getStatusCode() >= 300) {
			throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
		}

		HttpEntity entity = response.getEntity();
		if (!Objects.isNull(entity) && responseEntityType.isPresent()) {
			String json = EntityUtils.toString(entity, StandardCharsets.UTF_8);

			httpClient.close();

			Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new JsonDeserializer<LocalDate>() {
				@Override
				public LocalDate deserialize(JsonElement json, Type type,
						JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
					return LocalDate.parse(json.getAsJsonPrimitive().getAsString());
				}
			}).create();

			return Optional.of(gson.fromJson(json, responseEntityType.get()));
		} else {

			httpClient.close();
			return Optional.empty();
		}

	}

	protected abstract Optional<List<NameValuePair>> getParameters();

	protected abstract HttpUriRequest getRequest(URI uri);

	protected abstract String getPath();
}
