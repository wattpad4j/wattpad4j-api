package org.wattpad4j.models;

import java.io.Serial;
import java.io.Serializable;

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
@JsonPropertyOrder({ "name", "avatar", "fullname", "verified" })
public class WattpadShortUser implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	private String name;
	private String avatar;
	private String fullname;
	private Boolean verified;

	@Override
	public String toString() {
		return JacksonJson.toJsonString(this);
	}
}
