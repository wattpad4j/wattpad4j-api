package org.wattpad4j.models;

import java.net.URL;

import jakarta.annotation.Nullable;

/**
 * @author Yaris van Thiel
 */
public interface HasNext<T> {

	URL getNextUrl();

	Integer getTotal();

	Integer getCurrentTotalElements();

	T merge(@Nullable T next);

}
