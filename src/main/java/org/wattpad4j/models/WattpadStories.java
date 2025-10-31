package org.wattpad4j.models;

import java.io.Serial;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.wattpad4j.util.JacksonJson;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.annotation.Nullable;
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
@JsonPropertyOrder({ "stories", "total", "nextUrl" })
public class WattpadStories implements HasNext<WattpadStories>, Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	private List<WattpadStory> stories;
	private Integer total;
	private URL nextUrl;

	@Override
	public Integer getCurrentTotalElements() {
		return stories == null ? 0 : stories.size();
	}

	@Override
	public WattpadStories merge(@Nullable WattpadStories next) {
		if (next == null || next == this) {
			return this;
		}
		List<WattpadStory> stories = new ArrayList<>(this.stories);
		stories.addAll(next.stories);
		return new WattpadStories(stories, next.total, next.nextUrl);
	}

	@Override
	public String toString() {
		return JacksonJson.toJsonString(this);
	}
}
