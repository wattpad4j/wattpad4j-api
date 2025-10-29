package org.wattpad4j.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.wattpad4j.models.WattpadLists;
import org.wattpad4j.models.WattpadStories;
import org.wattpad4j.models.WattpadUser;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Response;

public class WattpadApiTest {

	private static final String TEST_HOST_URL = "https://non-existant.com";

	private static WattpadApi wattpadApi;
	private static final Client apiClient = Mockito.mock(Client.class);
	private static final WebTarget target = Mockito.mock(WebTarget.class);
	private static final Invocation.Builder builder = Mockito.mock(Invocation.Builder.class);
	private static final Response response = Mockito.mock(Response.class);

	@BeforeAll
	public static void setup() {
		wattpadApi = new WattpadApi(TEST_HOST_URL);
		wattpadApi.setApiClient(apiClient);
		Mockito.when(apiClient.target(Mockito.anyString())).thenReturn(target);
		Mockito.when(target.queryParam(Mockito.anyString(), Mockito.any())).thenReturn(target);
		Mockito.when(target.request()).thenReturn(builder);
		Mockito.when(builder.accept(Mockito.anyString())).thenReturn(builder);
		Mockito.when(builder.header(Mockito.anyString(), Mockito.anyString())).thenReturn(builder);
		Mockito.when(builder.get()).thenReturn(response);
	}

	@Test
	public void testStoriesAll() throws IOException {
		File storiesAllFile = Path.of("src/test/resources/stories/stories_all.json").toFile();
		FileInputStream storiesAll = new FileInputStream(storiesAllFile);
		Mockito.when(response.getEntity()).thenReturn(storiesAll);

		WattpadStories wattpadStories = wattpadApi.getStories("User");
		Assertions.assertEquals(2, wattpadStories.getTotal());
		Assertions.assertEquals(2, wattpadStories.getStories().size());

		Assertions.assertEquals(2, wattpadStories.getStories().getFirst().getNumParts());
		Assertions.assertEquals(2, wattpadStories.getStories().getFirst().getParts().size());

		Assertions.assertFalse(wattpadStories.toString().isEmpty());

		// new FileInputStream since previous is closed
		storiesAll = new FileInputStream(storiesAllFile);
		Mockito.when(response.getEntity()).thenReturn(storiesAll);
		WattpadStories wattpadStoriesWithFlag = wattpadApi.getStories("User", true);
		Assertions.assertEquals(wattpadStories, wattpadStoriesWithFlag);
	}

	@Test
	public void testStoriesAllWithoutPart() throws IOException {
		FileInputStream storiesWithoutPart = new FileInputStream(
		        Path.of("src/test/resources/stories/stories_all_without_part.json").toFile());

		Mockito.when(response.getEntity()).thenReturn(storiesWithoutPart);

		WattpadStories wattpadStories = wattpadApi.getStories("User", false);
		Assertions.assertEquals(2, wattpadStories.getTotal());
		Assertions.assertEquals(2, wattpadStories.getStories().size());

		Assertions.assertEquals(2, wattpadStories.getStories().getFirst().getNumParts());
		Assertions.assertNull(wattpadStories.getStories().getFirst().getParts());

		Assertions.assertFalse(wattpadStories.toString().isEmpty());
	}

	@Test
	public void testStoriesIdTitleOnly() throws IOException {
		FileInputStream storiesIdTitle = new FileInputStream(
		        Path.of("src/test/resources/stories/stories_id_title.json").toFile());
		Mockito.when(response.getEntity()).thenReturn(storiesIdTitle);

		WattpadStories wattpadStories = wattpadApi.getStories("User", "id,title");
		Assertions.assertEquals(2, wattpadStories.getTotal());
		Assertions.assertEquals(2, wattpadStories.getStories().size());

		Assertions.assertNull(wattpadStories.getStories().getFirst().getNumParts());
		Assertions.assertNull(wattpadStories.getStories().getFirst().getParts());

		Assertions.assertFalse(wattpadStories.toString().isEmpty());
	}

	@Test
	public void testStoriesPager() throws IOException {
		FileInputStream firstStories = new FileInputStream(
		        Path.of("src/test/resources/stories/stories_first.json").toFile());
		Mockito.when(response.getEntity()).thenReturn(firstStories);

		Pager<WattpadStories> wattpadStories = wattpadApi.getStories("User", 1);
		Assertions.assertTrue(wattpadStories.hasNext());
		WattpadStories first = wattpadStories.next();
		Assertions.assertEquals(2, first.getTotal());
		Assertions.assertEquals(1, first.getStories().size());
		Assertions.assertEquals("1", first.getStories().getFirst().getId());
		Assertions.assertFalse(first.toString().isEmpty());

		FileInputStream secondStories = new FileInputStream(
		        Path.of("src/test/resources/stories/stories_second.json").toFile());
		Mockito.when(response.getEntity()).thenReturn(secondStories);
		Assertions.assertTrue(wattpadStories.hasNext());
		WattpadStories second = wattpadStories.next();
		Assertions.assertEquals(2, second.getTotal());
		Assertions.assertEquals(1, second.getStories().size());
		Assertions.assertEquals("2", second.getStories().getFirst().getId());
		Assertions.assertFalse(second.toString().isEmpty());

		Assertions.assertFalse(wattpadStories.hasNext());
	}

	@Test
	public void testListsAll() throws IOException {
		FileInputStream listsAll = new FileInputStream(Path.of("src/test/resources/lists/lists_all.json").toFile());
		Mockito.when(response.getEntity()).thenReturn(listsAll);

		WattpadLists wattpadLists = wattpadApi.getLists("User");
		Assertions.assertEquals(2, wattpadLists.getTotal());
		Assertions.assertEquals(2, wattpadLists.getLists().size());

		Assertions.assertFalse(wattpadLists.toString().isEmpty());
	}

	@Test
	public void testListsIdNameOnly() throws IOException {
		FileInputStream listsIdName = new FileInputStream(
		        Path.of("src/test/resources/lists/lists_id_name.json").toFile());
		Mockito.when(response.getEntity()).thenReturn(listsIdName);

		WattpadLists wattpadLists = wattpadApi.getLists("User", "id,name");
		Assertions.assertEquals(2, wattpadLists.getTotal());
		Assertions.assertEquals(2, wattpadLists.getLists().size());

		Assertions.assertNull(wattpadLists.getLists().getFirst().getNumStories());

		Assertions.assertFalse(wattpadLists.toString().isEmpty());
	}

	@Test
	public void testListsPager() throws IOException {
		FileInputStream firstLists = new FileInputStream(Path.of("src/test/resources/lists/lists_first.json").toFile());
		Mockito.when(response.getEntity()).thenReturn(firstLists);

		Pager<WattpadLists> wattpadLists = wattpadApi.getLists("User", 1);
		Assertions.assertTrue(wattpadLists.hasNext());
		WattpadLists first = wattpadLists.next();
		Assertions.assertEquals(2, first.getTotal());
		Assertions.assertEquals(1, first.getLists().size());
		Assertions.assertEquals("1", first.getLists().getFirst().getId());
		Assertions.assertFalse(first.toString().isEmpty());

		FileInputStream secondLists = new FileInputStream(
		        Path.of("src/test/resources/lists/lists_second.json").toFile());
		Mockito.when(response.getEntity()).thenReturn(secondLists);
		Assertions.assertTrue(wattpadLists.hasNext());
		WattpadLists second = wattpadLists.next();
		Assertions.assertEquals(2, second.getTotal());
		Assertions.assertEquals(1, second.getLists().size());
		Assertions.assertEquals("2", second.getLists().getFirst().getId());
		Assertions.assertFalse(second.toString().isEmpty());

		Assertions.assertFalse(wattpadLists.hasNext());
	}

	@Test
	public void testUser() throws IOException {
		FileInputStream user = new FileInputStream(Path.of("src/test/resources/user/user.json").toFile());
		Mockito.when(response.getEntity()).thenReturn(user);

		WattpadUser wattpadUser = wattpadApi.getUser("User");
		Assertions.assertEquals("username", wattpadUser.getUsername());
		Assertions.assertFalse(wattpadUser.toString().isEmpty());
	}
}
