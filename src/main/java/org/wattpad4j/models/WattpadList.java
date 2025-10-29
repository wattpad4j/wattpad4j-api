package org.wattpad4j.models;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

import org.wattpad4j.util.JacksonJson;

import com.fasterxml.jackson.annotation.JsonProperty;
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
@JsonPropertyOrder({ "id", "name", "user", "numStories", "sampleCovers", "cover", "tags" })
public class WattpadList implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	private String id;
	private String name;
	private WattpadShortUser user;
	private Integer numStories;
	@JsonProperty("sample_covers")
	private List<String> sampleCovers;
	private String cover;
	private List<String> tags;

	@Override
	public String toString() {
		return JacksonJson.toJsonString(this);
	}
}
