package org.wattpad4j.api;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.wattpad4j.models.WattpadError;
import org.wattpad4j.models.WattpadLists;
import org.wattpad4j.models.WattpadStories;
import org.wattpad4j.models.WattpadUser;
import org.wattpad4j.util.JacksonJson;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.Setter;

/**
 * This class provides a simplified interface to the Wattpad API.
 */
public class WattpadApi {

	private final String baseUrl;

	@Setter
	private Client apiClient;

	@SuppressWarnings("unused")
	public WattpadApi() {
		this(WattpadConstants.BASE_URL);
	}

	public WattpadApi(@Nonnull final String baseUrl) {
		this.baseUrl = baseUrl;
		final ClientConfig clientConfig = new ClientConfig();

		clientConfig.property(ClientProperties.FEATURE_AUTO_DISCOVERY_DISABLE, true);
		clientConfig.property(ClientProperties.METAINF_SERVICES_LOOKUP_DISABLE, true);

		clientConfig.register(JacksonJson.class);
		clientConfig.register(JacksonFeature.class);

		final ClientBuilder clientBuilder = new JerseyClientBuilder().withConfig(clientConfig);

		clientBuilder.register(JacksonJson.class);
		clientBuilder.register(JacksonFeature.class);

		this.apiClient = clientBuilder.build();
	}

	/**
	 * Get all the stories of a user.
	 *
	 * @param userName username of the user.
	 * @return WattpadStories containing all the stories of the user.
	 * @throws WattpadApiException if any error occurs.
	 */
	public WattpadStories getStories(@Nonnull final String userName) throws WattpadApiException {
		return getStories(userName, WattpadConstants.DEFAULT_LIMIT).all();
	}

	/**
	 * Get all the stories of a user. Optionally indicate to include the parts of the story.
	 *
	 * @param userName     username of the user.
	 * @param includeParts boolean whether to include the parts of the story.
	 * @return WattpadStories containing all the stories of the user with or without the parts.
	 * @throws WattpadApiException if any error occurs.
	 */
	public WattpadStories getStories(@Nonnull final String userName, final boolean includeParts)
	        throws WattpadApiException {
		return getStories(userName, WattpadConstants.DEFAULT_LIMIT, includeParts).all();
	}

	/**
	 * Get a limited amount of stories of a user. Optionally indicate to include the parts of the story.
	 *
	 * @param userName     username of the user.
	 * @param includeParts boolean whether to include the parts of the story.
	 * @param limit        maximum amount of stories to retrieve.
	 * @return WattpadStories containing a limited amount of the stories of the user with or without the parts.
	 * @throws WattpadApiException if any error occurs.
	 */
	public Pager<WattpadStories> getStories(@Nonnull final String userName, final int limit, final boolean includeParts)
	        throws WattpadApiException {
		return getStories(userName, limit,
		        includeParts ? WattpadConstants.STORY_ALL_FIELDS : WattpadConstants.STORY_NO_PART_FIELDS);
	}

	/**
	 * Get all the stories of a user. Limit to contain certain fields.
	 *
	 * @param userName username of the user.
	 * @param fields   the fields of the story to include in retrieval. Defaults to all fields.
	 * @return WattpadStories containing all the stories of the user limited to only include the provided fields.
	 * @throws WattpadApiException if any error occurs.
	 */
	public WattpadStories getStories(@Nonnull final String userName, @Nullable final String... fields)
	        throws WattpadApiException {
		return getStories(userName, WattpadConstants.DEFAULT_LIMIT, fields).all();
	}

	/**
	 * Get a limited amount the stories of a user. Limit to contain certain fields.
	 *
	 * @param userName username of the user.
	 * @param fields   the fields of the story to include in retrieval. Defaults to all fields.
	 * @param limit    maximum amount of stories to retrieve.
	 * @return WattpadStories containing a limited amount of the stories of the user limited to only include the
	 *         provided fields.
	 * @throws WattpadApiException if any error occurs.
	 */
	public Pager<WattpadStories> getStories(@Nonnull final String userName, final int limit,
	        @Nullable final String... fields)
	        throws WattpadApiException {
		return new Pager<>(this, WattpadStories.class, limit,
		        WattpadConstants.FIELDS_FUNCTION.apply("stories", getFields(WattpadConstants.STORY_ALL_FIELDS, fields)),
		        "v4",
		        "users", userName,
		        "stories", "published");

	}

	/**
	 * Get all the lists of a user.
	 *
	 * @param userName username of the user.
	 * @return WattpadLists containing all the lists of the user.
	 * @throws WattpadApiException if any error occurs.
	 */
	public WattpadLists getLists(@Nonnull final String userName) throws WattpadApiException {
		return getLists(userName, WattpadConstants.DEFAULT_LIMIT).all();
	}

	/**
	 * Get all the lists of a user. Limit to contain certain fields.
	 *
	 * @param userName username of the user.
	 * @param fields   the fields of the list to include in retrieval. Defaults to all fields.
	 * @return WattpadLists containing all the lists of the user limited to only include the provided fields.
	 * @throws WattpadApiException if any error occurs.
	 */
	public WattpadLists getLists(@Nonnull final String userName, @Nullable final String... fields)
	        throws WattpadApiException {
		return getLists(userName, WattpadConstants.DEFAULT_LIMIT, fields).all();
	}

	/**
	 * Get a limited amount of lists of a user. Limit to contain certain fields.
	 *
	 * @param userName username of the user.
	 * @param fields   the fields of the list to include in retrieval. Defaults to all fields
	 * @param limit    maximum amount of lists to retrieve.
	 * @return WattpadLists containing a limited amount of the lists of the user limited to only include the provided
	 *         fields.
	 * @throws WattpadApiException if any error occurs.
	 */
	public Pager<WattpadLists> getLists(@Nonnull final String userName, int limit, @Nullable final String... fields)
	        throws WattpadApiException {
		return new Pager<>(this, WattpadLists.class, limit,
		        WattpadConstants.FIELDS_FUNCTION.apply("lists", getFields(WattpadConstants.LIST_ALL_FIELDS, fields)),
		        "api",
		        "v3", "users", userName,
		        "lists");
	}

	/**
	 * Get a user by username.
	 *
	 * @param userName username of the user.
	 * @return WattpadUser containing the user.
	 * @throws WattpadApiException if any error occurs.
	 */
	public WattpadUser getUser(@Nonnull final String userName) throws WattpadApiException {
		Response response = validate(get(Map.of(), "api", "v3", "users", userName));
		return readValue(response, WattpadUser.class);
	}

	/**
	 * Get the fields that should be used in the API. If it's null, or only contains null values, return the default
	 * fields; otherwise use the fields.
	 *
	 * @param defaultFields Default fields to use.
	 * @param fields        fields the user passed to include in the API call.
	 * @return fields to use in the API call;
	 */
	private String[] getFields(final String[] defaultFields, String... fields) {
		if (fields == null) {
			return defaultFields;
		}
		fields = Arrays.stream(fields).filter(Objects::nonNull).toArray(String[]::new);
		if (fields.length == 0) {
			return defaultFields;
		}
		return fields;
	}

	@SuppressWarnings("SameParameterValue")
	private <T> T readValue(final Response response, final Class<T> clazz) throws WattpadApiException {
		try {
			return JacksonJson.mapper.readValue((InputStream) response.getEntity(), clazz);
		} catch (IOException e) {
			throw new WattpadApiException(e);
		}
	}

	/**
	 * Perform an HTTP GET call with the specified query parameters and path objects, returning a ClientResponse
	 * instance with the data returned from the endpoint.
	 *
	 * @param queryParams multivalue map of request parameters.
	 * @param pathArgs    variable list of arguments used to build the URI.
	 * @return a ClientResponse instance with the data returned from the endpoint.
	 * @throws WattpadApiException if any error occurs.
	 */
	protected Response get(final Map<String, String> queryParams, final Object... pathArgs) throws WattpadApiException {
		try {
			final URL url = getApiUrl(pathArgs);
			return validate(get(queryParams, url));
		} catch (Exception e) {
			throw handle(e);
		}
	}

	/**
	 * Perform an HTTP GET call with the specified query parameters and URL, returning a ClientResponse instance with
	 * the data returned from the endpoint.
	 *
	 * @param queryParams multivalue map of request parameters.
	 * @param url         the fully formed path to the Wattpad API endpoint.
	 * @return a ClientResponse instance with the data returned from the endpoint.
	 */
	private Response get(final Map<String, String> queryParams, final URL url) {
		return invocation(url, queryParams).get();
	}

	private Invocation.Builder invocation(final URL url, final Map<String, String> queryParams) {

		WebTarget target = this.apiClient.target(url.toExternalForm());
		if (queryParams != null) {
			for (Map.Entry<String, String> param : queryParams.entrySet()) {
				target = target.queryParam(param.getKey(), param.getValue());
			}
		}
		Invocation.Builder builder = target.request();
		builder = builder.accept(MediaType.APPLICATION_JSON);
		builder = builder.header(HttpHeaders.USER_AGENT, WattpadConstants.USER_AGENT);
		return builder;
	}

	/**
	 * Validates response the response from the server is OK; if it's not will throw a GitLabApiException.
	 *
	 * @param response response.
	 * @return original response if the response status is expected.
	 * @throws WattpadApiException if HTTP status is not as expected.
	 */
	protected Response validate(Response response) throws WattpadApiException {
		int responseCode = response.getStatus();
		if (responseCode != Response.Status.OK.getStatusCode()) {
			try {
				throw new WattpadApiException(
				        JacksonJson.mapper.readValue((InputStream) response.getEntity(), WattpadError.class));
			} catch (IOException e) {
				throw new WattpadApiException(response.getStatusInfo().getReasonPhrase());
			}
		}
		return response;
	}

	/**
	 * Wraps an exception in a WattpadApiException if needed.
	 *
	 * @param thrown the exception that should be wrapped.
	 * @return either the untouched WattpadApiException or a new WattpadApiException wrapping a non-WattpadApiException.
	 */
	private WattpadApiException handle(final Exception thrown) {
		if (thrown instanceof WattpadApiException wattpadApiException) {
			return wattpadApiException;
		}

		return new WattpadApiException(thrown);
	}

	/**
	 * Construct a REST URL with the specified path arguments.
	 *
	 * @param pathArgs variable list of arguments used to build the URI.
	 * @return a REST URL with the specified path arguments.
	 * @throws IOException if an error occurs while constructing the URL.
	 */
	private URL getApiUrl(final Object... pathArgs) throws IOException {
		final String url = appendPathArgs(pathArgs);
		return URI.create(url).toURL();
	}

	private String appendPathArgs(final Object... pathArgs) {
		final StringBuilder urlBuilder = new StringBuilder(this.baseUrl);
		for (Object pathArg : pathArgs) {
			if (pathArg != null) {
				urlBuilder.append("/");
				urlBuilder.append(pathArg);
			}
		}
		return urlBuilder.toString();
	}
}
