package org.wattpad4j.api;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.Map;

import net.bytebuddy.utility.nullability.UnknownNull;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.wattpad4j.models.WattpadLists;
import org.wattpad4j.models.WattpadStories;
import org.wattpad4j.models.WattpadUser;
import org.wattpad4j.util.JacksonJson;

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

	public WattpadApi(final String baseUrl) {
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
	public WattpadStories getStories(final String userName) throws WattpadApiException {
		return getStories(userName, null, WattpadConstants.DEFAULT_LIMIT).all();
	}

	/**
	 * Get a limited amount of stories of a user.
	 *
	 * @param userName username of the user.
	 * @param limit    maximum amount of stories to retrieve.
	 * @return WattpadStories containing a limited amount of the stories of the user.
	 * @throws WattpadApiException if any error occurs.
	 */
	public Pager<WattpadStories> getStories(final String userName, int limit) throws WattpadApiException {
		return getStories(userName, null, limit);
	}

	/**
	 * Get all the stories of a user. Optionally indicate to include the parts of the story.
	 *
	 * @param userName     username of the user.
	 * @param includeParts boolean whether to include the parts of the story.
	 * @return WattpadStories containing all the stories of the user with or without the parts.
	 * @throws WattpadApiException if any error occurs.
	 */
	public WattpadStories getStories(final String userName, final boolean includeParts) throws WattpadApiException {
		return getStories(userName, includeParts, WattpadConstants.DEFAULT_LIMIT).all();
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
	public Pager<WattpadStories> getStories(final String userName, final boolean includeParts, int limit)
	        throws WattpadApiException {
		return getStories(userName,
		        includeParts ? WattpadConstants.STORY_ALL_FIELDS : WattpadConstants.STORY_NO_PART_FIELDS, limit);
	}

	/**
	 * Get all the stories of a user. Limit to contain certain fields.
	 *
	 * @param userName    username of the user.
	 * @param storyFields the fields of the story to include in retrieval. Defaults to all fields.
	 * @return WattpadStories containing all the stories of the user limited to only include the provided fields.
	 * @throws WattpadApiException if any error occurs.
	 */
	public WattpadStories getStories(final String userName, @Nullable final String storyFields)
	        throws WattpadApiException {
		return getStories(userName, storyFields, WattpadConstants.DEFAULT_LIMIT).all();
	}

	/**
	 * Get a limited amount the stories of a user. Limit to contain certain fields.
	 *
	 * @param userName    username of the user.
	 * @param storyFields the fields of the story to include in retrieval. Defaults to all fields.
	 * @param limit       maximum amount of stories to retrieve.
	 * @return WattpadStories containing a limited amount of the stories of the user limited to only include the
	 *         provided fields.
	 * @throws WattpadApiException if any error occurs.
	 */
	public Pager<WattpadStories> getStories(final String userName, @Nullable final String storyFields, int limit)
	        throws WattpadApiException {
		final String fields = storyFields == null ? WattpadConstants.STORY_ALL_FIELDS : storyFields;
		return new Pager<>(this, WattpadStories.class, limit, WattpadConstants.FIELDS_FUNCTION.apply("stories", fields),
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
	public WattpadLists getLists(final String userName) throws WattpadApiException {
		return getLists(userName, null, WattpadConstants.DEFAULT_LIMIT).all();
	}

	/**
	 * Get a limited amount of lists of a user.
	 *
	 * @param userName username of the user.
	 * @param limit    maximum amount of lists to retrieve.
	 * @return WattpadLists containing a limited amount of the lists of the user.
	 * @throws WattpadApiException if any error occurs.
	 */
	public Pager<WattpadLists> getLists(final String userName, int limit) throws WattpadApiException {
		return getLists(userName, null, limit);
	}

	/**
	 * Get all the lists of a user. Limit to contain certain fields.
	 *
	 * @param userName   username of the user.
	 * @param listFields the fields of the list to include in retrieval. Defaults to all fields.
	 * @return WattpadLists containing all the lists of the user limited to only include the provided fields.
	 * @throws WattpadApiException if any error occurs.
	 */
	public WattpadLists getLists(final String userName, @Nullable final String listFields) throws WattpadApiException {
		return getLists(userName, listFields, WattpadConstants.DEFAULT_LIMIT).all();
	}

	/**
	 * Get a limited amount of lists of a user. Limit to contain certain fields.
	 *
	 * @param userName   username of the user.
	 * @param listFields the fields of the list to include in retrieval. Defaults to all fields
	 * @param limit      maximum amount of lists to retrieve.
	 * @return WattpadLists containing a limited amount of the lists of the user limited to only include the provided
	 *         fields.
	 * @throws WattpadApiException if any error occurs.
	 */
	public Pager<WattpadLists> getLists(final String userName, @Nullable final String listFields, int limit)
	        throws WattpadApiException {
		final String fields = listFields == null ? WattpadConstants.LIST_ALL_FIELDS : listFields;
		return new Pager<>(this, WattpadLists.class, limit, WattpadConstants.FIELDS_FUNCTION.apply("lists", fields),
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
	public WattpadUser getUser(final String userName) throws WattpadApiException {
		Response response = get(Map.of(), "api", "v3", "users", userName);
		return readValue(response, WattpadUser.class);
	}

	@SuppressWarnings("SameParameterValue")
	private <T> T readValue(final Response response, final Class<T> clazz) {
		try {
			return JacksonJson.mapper.readValue((InputStream) response.getEntity(), clazz);
		} catch (IOException e) {
			throw new WattpadApiException(e);
		}
	}

	/**
	 * Wraps an exception in a WattpadApiException if needed.
	 *
	 * @param thrown the exception that should be wrapped.
	 * @return either the untouched WattpadApiException or a new WattpadApiException wrapping a non-WattpadApiException.
	 * @throws WattpadApiException if any error occurs.
	 */
	private WattpadApiException handle(final Exception thrown) throws WattpadApiException {
		if (thrown instanceof WattpadApiException wattpadApiException) {
			return wattpadApiException;
		}

		return new WattpadApiException(thrown);
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
			return get(queryParams, url);
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
}
