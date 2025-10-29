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
@JsonPropertyOrder({ "username", "avatar", "isPrivate", "backgroundUrl", "name", "description", "status", "gender",
        "genderCode", "language", "locale", "createDate", "modifyDate", "location", "verified", "ambassador",
        "facebook", "website", "lulu", "smashwords", "bubok", "votesReceived", "numStoriesPublished", "numFollowing",
        "numFollowers", "numMessages", "numLists", "verifiedEmail", "preferredCategories", "allowCrawler", "deeplink",
        "isMuted", })
public class WattpadUser implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	private String username;
	private URL avatar;
	private Boolean isPrivate;
	private URL backgroundUrl;
	private String name;
	private String description;
	private String status;
	private String gender;
	private String genderCode;
	private Integer language;
	private String locale;
	private ZonedDateTime createDate;
	private ZonedDateTime modifyDate;
	private String location;
	private Boolean verified;
	private Boolean ambassador;
	private URL facebook;
	private URL website;
	private URL lulu;
	private URL smashwords;
	private URL bubok;
	private Long votesReceived;
	private Integer numStoriesPublished;
	private Integer numFollowing;
	private Integer numFollowers;
	private Integer numMessages;
	private Integer numLists;

	@JsonProperty("verified_email")
	private Boolean verifiedEmail;

	@JsonProperty("preferred_categories")
	private List<String> preferredCategories;
	private Boolean allowCrawler;
	private String deeplink;
	private Boolean isMuted;

	@Override
	public String toString() {
		return JacksonJson.toJsonString(this);
	}
}
