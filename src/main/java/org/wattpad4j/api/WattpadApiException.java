package org.wattpad4j.api;

import java.io.Serial;

import org.wattpad4j.models.WattpadError;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * This is the exception that will be thrown if any exception occurs while communicating with a Wattpad endpoint.
 * <p>
 * Copied from <a href=
 * "https://github.com/gitlab4j/gitlab4j-api/blob/main/gitlab4j-api/src/main/java/org/gitlab4j/api/GitLabApiException.java">GitLab4J&trade;</a>
 * and adapted to use in this project.
 *
 */
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
