package org.wattpad4j.models;

import java.util.List;

import org.wattpad4j.util.JacksonJson;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Yaris van Thiel
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@JsonPropertyOrder({ "errorCode", "errorType", "message", "fields" })
public class WattpadError {

	@JsonProperty("error_code")
	private Integer errorCode;

	@JsonProperty("error_type")
	private String errorType;

	private String message;

	private List<String> fields;

	@Override
	public String toString() {
		return JacksonJson.toJsonString(this);
	}
}
