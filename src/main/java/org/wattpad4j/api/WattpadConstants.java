package org.wattpad4j.api;

import java.util.function.BiFunction;

public final class WattpadConstants {

	// used for calling wattpad
	public static final String BASE_URL = "https://www.wattpad.com";
	public static final String USER_AGENT = "WattpadAPI/1.0";

	// fields used in queryParams
	public static final String OFFSET = "offset";
	public static final String LIMIT = "limit";
	public static final String FIELDS = "fields";

	// defaults
	static final int DEFAULT_LIMIT = 25;

	// story defaults
	public static final String[] STORY_ALL_FIELDS = new String[] { "id", "title", "length", "createDate", "modifyDate",
	        "voteCount", "commentCount", "language", "user", "description", "cover", "coverTimestamp", "completed",
	        "categories", "tags", "rating", "mature", "copyright", "url", "firstPartId", "numParts", "parts",
	        "readerBrowseEligibility", "firstPublishedPart", "lastPublishedPart", "deleted", "readCount", };
	public static final String[] STORY_NO_PART_FIELDS = new String[] { "id", "title", "length", "createDate",
	        "modifyDate",
	        "voteCount", "commentCount", "language", "user", "description", "cover", "coverTimestamp", "completed",
	        "categories", "tags", "rating", "mature", "copyright", "url", "firstPartId", "numParts",
	        "readerBrowseEligibility", "firstPublishedPart", "lastPublishedPart", "deleted", "readCount", };

	// list defaults
	public static final String[] LIST_ALL_FIELDS = new String[] { "id", "name", "user", "numStories", "sample_covers",
	        "cover",
	        "tags", };

	// always include total and nextUrl
	public static final BiFunction<String, String[], String> FIELDS_FUNCTION = (item, fields) -> item + "("
	        + String.join("", fields) + "),total,nextUrl";
}
