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
@JsonPropertyOrder({ "lists", "total", "nextUrl" })
public class WattpadLists implements HasNext<WattpadLists>, Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	private List<WattpadList> lists;
	private Integer total;
	private URL nextUrl;

	@Override
	public Integer getCurrentTotalElements() {
		return lists == null ? 0 : lists.size();
	}

	@Override
	public WattpadLists merge(@Nullable WattpadLists next) {
		if (next == null || next == this) {
			return this;
		}
		List<WattpadList> lists = new ArrayList<>(next.lists);
		lists.addAll(this.lists);
		return new WattpadLists(lists, this.total, this.nextUrl);
	}

	@Override
	public String toString() {
		return JacksonJson.toJsonString(this);
	}
}
