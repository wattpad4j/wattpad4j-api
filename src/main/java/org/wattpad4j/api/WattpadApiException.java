package org.wattpad4j.api;

import java.io.Serial;

import org.wattpad4j.models.WattpadError;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = true)
public class WattpadApiException extends Exception {

	@Serial
	private static final long serialVersionUID = 1L;

	private final String message;

	/**
	 * Create a WattpadApiException instance based on a message.
	 *
	 * @param message the message.
	 */
	public WattpadApiException(String message) {
		super();
		this.message = message;
	}

	/**
	 * Create a WattpadApiException instance based on the exception.
	 *
	 * @param e the Exception to wrap.
	 */
	public WattpadApiException(final Exception e) {
		super(e);
		message = e.getMessage();
	}

	/**
	 * Create a WattpadApiException instance based on the WattpadError.
	 *
	 * @param wattpadError the WattpadError wo wrap.
	 */
	public WattpadApiException(final WattpadError wattpadError) {
		super();
		if (wattpadError.getMessage() != null) {
			this.message = wattpadError.getMessage();
		} else {
			this.message = "Unknown error occurred";
		}
	}
}
