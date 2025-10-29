package org.wattpad4j.models;

import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;

import org.wattpad4j.util.JacksonJson;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;

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
@JsonPropertyOrder({ "id", "title", "url", "rating", "draft", "modifyDate", "createDate", "length", "videoId",
        "photoUrl", "commentCount", "voteCount", "readCount" })
public class WattpadPart implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	private Long id;
	private String title;
	private String url;
	private Integer rating;
	private Boolean draft;
	private ZonedDateTime modifyDate;
	private ZonedDateTime createDate;
	private Long length;
	private String videoId;

	@JsonSetter(nulls = Nulls.AS_EMPTY)
	private String photoUrl;
	private Integer commentCount;
	private Integer voteCount;
	private Integer readCount;

	@Override
	public String toString() {
		return JacksonJson.toJsonString(this);
	}

}
