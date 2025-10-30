package org.wattpad4j.util;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJaxbJsonProvider;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.ext.ContextResolver;
import lombok.Getter;

/**
 * Jackson JSON Configuration and utility class.
 */
@Produces(MediaType.APPLICATION_JSON)
@Getter
public class JacksonJson extends JacksonJaxbJsonProvider implements ContextResolver<ObjectMapper> {

	private static final Logger LOGGER = Logger.getLogger(JacksonJson.class.getName());

	public static final JacksonJson jacksonJson = new JacksonJson();
	public static final ObjectMapper mapper = jacksonJson.getObjectMapper();

	private final ObjectMapper objectMapper;

	public JacksonJson() {
		objectMapper = new ObjectMapper();

		objectMapper.setDefaultPropertyInclusion(Include.NON_NULL);
		objectMapper.setDefaultSetterInfo(JsonSetter.Value.forValueNulls(Nulls.AS_EMPTY));

		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

		objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, false);
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true);

		objectMapper.registerModule(new JavaTimeModule());

		setMapper(objectMapper);
	}

	@Override
	public ObjectMapper getContext(final Class<?> objectType) {
		return objectMapper;
	}

	/**
	 * Marshals the supplied object out as a formatted JSON string.
	 *
	 * @param <T>    the generics type for the provided object.
	 * @param object the object to output as a JSON string.
	 * @return a String containing the JSON for the specified object.
	 */
	public <T> String marshal(final T object) {

		if (object == null) {
			throw new IllegalArgumentException("object parameter is null");
		}

		ObjectWriter writer = objectMapper.writer().withDefaultPrettyPrinter();
		String results = null;
		try {
			results = writer.writeValueAsString(object);
		} catch (JsonGenerationException e) {
			JacksonJson.LOGGER.log(Level.SEVERE, "JsonGenerationException", e);
		} catch (JsonMappingException e) {
			JacksonJson.LOGGER.log(Level.SEVERE, "JsonMappingException", e);
		} catch (IOException e) {
			JacksonJson.LOGGER.log(Level.SEVERE, "IOException", e);
		}

		return results;
	}

	/**
	 * This class is used to create a thread-safe singleton instance of JacksonJson customized to be used by.
	 */
	private static class JacksonJsonSingletonHelper {

		private static final JacksonJson JACKSON_JSON = new JacksonJson();
	}

	/**
	 * Gets the supplied object output as a formatted JSON string. Null properties will result in the value of the
	 * property being null. This is meant to be used for toString() implementations of wattpad4j classes.
	 *
	 * @param <T>    the generics type for the provided object.
	 * @param object the object to output as a JSON string.
	 * @return a String containing the JSON for the specified object.
	 */
	public static <T> String toJsonString(final T object) {
		return JacksonJsonSingletonHelper.JACKSON_JSON.marshal(object);
	}
}
