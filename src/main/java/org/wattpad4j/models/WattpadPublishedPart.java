package org.wattpad4j.models;

import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;

import org.wattpad4j.util.JacksonJson;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@JsonPropertyOrder({ "id", "createDate" })
public class WattpadPublishedPart implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	private Long id;
	private ZonedDateTime createDate;

	@Override
	public String toString() {
		return JacksonJson.toJsonString(this);
	}
}
