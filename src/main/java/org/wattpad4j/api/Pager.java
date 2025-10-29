package org.wattpad4j.api;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
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

	private int position;
	private int totalItems;
	private URL nextUrl;
	private T currentItem;
	private final Class<T> type;

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
	 * @throws WattpadApiException if any error occurs.
	 */
	public Pager(final WattpadApi api, final Class<T> type,
	        final int limit, final String fields, final Object... pathArgs) throws WattpadApiException {

		this.queryParams = new HashMap<>();
		this.queryParams.put(WattpadConstants.OFFSET, Integer.toString(0));
		this.queryParams.put(WattpadConstants.LIMIT, Integer.toString(limit));
		this.queryParams.put(WattpadConstants.FIELDS, fields);

		this.position = 0;
		this.totalItems = Integer.MAX_VALUE;
		this.nextUrl = null;
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
		return this.position < this.totalItems || this.nextUrl != null;
	}

	/**
	 * Returns the next item in the iteration containing the object.
	 *
	 * @return the next item in the iteration.
	 * @throws NoSuchElementException if the iteration has no more elements.
	 * @throws WattpadApiException    if any error occurs.
	 */
	@Override
	public T next() throws WattpadApiException {
		if (!hasNext()) {
			throw new NoSuchElementException();
		}
		try {
			Response response = this.api.get(this.queryParams, this.pathArgs);
			this.currentItem = JacksonJson.mapper.readValue((InputStream) response.getEntity(), this.type);
			this.nextUrl = this.currentItem.getNextUrl();
			this.updateQueryParams();

			// actually set the total items, in the constructor Integer.MAX_VALUE was used since at that
			// point it was unknown
			if (this.totalItems == Integer.MAX_VALUE) {
				if (this.currentItem.getNextUrl() == null) {
					// no next url, so this was actually the last page
					this.totalItems = 1;
				} else {
					// there are more pages, since there is a next url
					this.totalItems = this.currentItem.getTotal();
				}
			}
			this.position++;
			return this.currentItem;
		} catch (IOException e) {
			throw new WattpadApiException(e);
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
	 * This method is not implemented and will throw an UnsupportedOperationException if called.
	 *
	 * @throws UnsupportedOperationException when invoked.
	 */
	public List<T> first() {
		throw new UnsupportedOperationException();
	}

	/**
	 * This method is not implemented and will throw an UnsupportedOperationException if called.
	 *
	 * @throws UnsupportedOperationException when invoked.
	 */
	public List<T> last() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Gets all the items from each page as a single object instance.
	 *
	 * @return all the items from each page as a single object instance
	 */
	public T all() {
		// Iterate through the pages and merge the objects.
		T prev = this.currentItem;
		T result = this.currentItem;
		while (hasNext()) {
			T current = next();
			result = current.merge(prev);
			prev = result;
		}
		return result;
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
