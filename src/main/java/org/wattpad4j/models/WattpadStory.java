package org.wattpad4j.models;

import java.io.Serial;
import java.io.Serializable;
import java.net.URL;
import java.time.ZonedDateTime;
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
@JsonPropertyOrder({ "id", "title", "length", "createDate", "modifyDate", "voteCount", "commentCount", "language",
        "user", "description", "cover", "coverTimestamp", "completed", "categories", "tags", "rating", "mature",
        "copyright", "url", "firstPartId", "numParts", "readerBrowseEligibility", "firstPublishedPart",
        "lastPublishedPart", "parts", "deleted", "readCount" })
public class WattpadStory implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	private String id;
	private String title;
	private Long length;
	private ZonedDateTime createDate;
	private ZonedDateTime modifyDate;
	private Integer voteCount;
	private Integer commentCount;
	private WattpadLanguage language;
	private WattpadShortUser user;
	private String description;
	private URL cover;

	@JsonProperty("cover_timestamp")
	private ZonedDateTime coverTimestamp;
	private Boolean completed;
	private List<Integer> categories;
	private List<String> tags;
	private Integer rating;
	private Boolean mature;
	private Integer copyright;
	private String url;
	private Long firstPartId;
	private Integer numParts;
	private WattpadBrowseEligibility readerBrowseEligibility;
	private WattpadPublishedPart firstPublishedPart;
	private WattpadPublishedPart lastPublishedPart;
	private List<WattpadPart> parts;
	private Boolean deleted;
	private Long readCount;

	@Override
	public String toString() {
		return JacksonJson.toJsonString(this);
	}
}
