package org.wattpad4j.api;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.wattpad4j.models.HasNext;
import org.wattpad4j.util.JacksonJson;

import jakarta.ws.rs.core.Response;

/**
 * This class defines an Iterator implementation that is used as a paging iterator for all API methods that hav a
 * nextUrl property. It hides the details of interacting with the Wattpad API when paging is involved simplifying
 * accessing the objects
 *
 * @param <T> the wattpad4j type
 */
public class Pager<T extends HasNext<T>> implements Iterator<T> {

	private int currentPage;
	private URL nextUrl;
	private T currentItem;
	private final Class<T> type;

	private final int totalPages;
	private final WattpadApi api;
	private final Map<String, String> queryParams;
	private final Object[] pathArgs;

	/**
	 * Creates a Pager instance to access the API through the specified path and query parameters.
	 *
	 * @param api      the WattpadApi implementation to communicate through.
	 * @param type     the Wattpad4J type.
	 * @param limit    items per page.
	 * @param pathArgs HTTP path arguments.
	 */
	public Pager(final WattpadApi api, final Class<T> type,
	        final int limit, final String fields, final Object... pathArgs) throws WattpadApiException {

		this.queryParams = new HashMap<>();
		this.queryParams.put(WattpadConstants.OFFSET, Integer.toString(0));
		this.queryParams.put(WattpadConstants.LIMIT, Integer.toString(limit));
		this.queryParams.put(WattpadConstants.FIELDS, fields);

		try {
			final Response response = api.get(this.queryParams, pathArgs);
			this.currentItem = JacksonJson.mapper.readValue((InputStream) response.getEntity(), type);
		} catch (IOException e) {
			throw new WattpadApiException(e);
		}

		this.currentPage = 0;
		this.nextUrl = this.currentItem.getNextUrl();
		this.totalPages = Math.ceilDiv(this.currentItem.getTotal(), this.currentItem.getCurrentTotalElements());
		this.type = type;
		this.api = api;
		this.pathArgs = pathArgs;
	}

	/**
	 * Returns the true if there are additional pages to iterate over, otherwise returns false.
	 *
	 * @return true if there are additional pages to iterate over, otherwise returns false
	 */
	@Override
	public boolean hasNext() {
		return this.currentPage < this.totalPages;
	}

	/**
	 * Returns the next item in the iteration containing the object.
	 *
	 * @return the next item in the iteration.
	 * @throws NoSuchElementException if the iteration has no more elements.
	 * @throws RuntimeException       if any error occurs.
	 */
	@Override
	public T next() throws RuntimeException {
		try {
			return page(this.currentPage + 1);
		} catch (WattpadApiException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * This method is not implemented and will throw an UnsupportedOperationException if called.
	 *
	 * @throws UnsupportedOperationException when invoked.
	 */
	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Returns the next item in the iteration containing the object.
	 *
	 * @return the next item in the iteration.
	 * @throws NoSuchElementException if the iteration has no more elements.
	 * @throws WattpadApiException    if any error occurs.
	 */
	private T page(final int pageNumber) throws WattpadApiException {
		// already got first item in the constructor
		if (this.currentPage == 0 && pageNumber == 1) {
			this.currentPage = 1;
			return this.currentItem;
		}
		if (this.currentPage == pageNumber) {
			return this.currentItem;
		}

		if (pageNumber > this.totalPages) {
			throw new NoSuchElementException();
		}
		try {
			this.nextUrl = this.currentItem.getNextUrl();
			this.updateQueryParams();
			this.currentPage = pageNumber;
			Response response = this.api.get(this.queryParams, this.pathArgs);
			this.currentItem = JacksonJson.mapper.readValue((InputStream) response.getEntity(), this.type);
			return this.currentItem;
		} catch (IOException e) {
			throw new WattpadApiException(e);
		}
	}

	/**
	 * Gets all the items from each page as a single object instance.
	 *
	 * @return all the items from each page as a single object instance.
	 * @throws WattpadApiException if any error occurs.
	 */
	public T all() throws WattpadApiException {
		// iterate through the pages and merge the objects.
		this.currentPage = 0;
		List<T> allItems = new ArrayList<>(Math.max(this.totalPages, 0));
		while (hasNext()) {
			allItems.add(page(this.currentPage + 1));
		}
		return allItems.stream().reduce(allItems.getFirst(), HasNext::merge);
	}

	/**
	 * Updates the queryParams with the params from the nextUrl.
	 */
	private void updateQueryParams() {
		this.queryParams.clear();
		if (this.nextUrl == null) {
			return;
		}
		String[] queryParams = this.nextUrl.getQuery().split("&");
		for (String queryParam : queryParams) {
			String[] params = queryParam.split("=");
			this.queryParams.put(params[0], params[1]);
		}
	}
}
