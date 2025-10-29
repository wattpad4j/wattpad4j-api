package org.wattpad4j.api;

import java.io.Serial;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = true)
public class WattpadApiException extends RuntimeException {

	@Serial
	private static final long serialVersionUID = 1L;

	private final String message;

	/**
	 * Create a WattpadApiException instance based on the exception.
	 *
	 * @param e the Exception to wrap.
	 */
	public WattpadApiException(Exception e) {
		super(e);
		message = e.getMessage();
	}
}
