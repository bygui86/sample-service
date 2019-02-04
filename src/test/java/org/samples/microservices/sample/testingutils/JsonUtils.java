package org.samples.microservices.sample.testingutils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;


public final class JsonUtils {

	private JsonUtils() {
		// no-op
	}

	public static String toJsonString(final Object object) throws JsonProcessingException {

		final ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		return mapper.writeValueAsString(object);
	}

	public static byte[] toJsonByteArray(final Object object) throws IOException {

		final ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		return mapper.writeValueAsBytes(object);
	}

	@SuppressWarnings("unchecked")
	public static Object toObject(final String jsonString, final Class type) throws IOException {

		return new ObjectMapper()
				.readValue(jsonString, type);
	}

	@SuppressWarnings("unchecked")
	public static Object toObject(final byte[] jsonByteArray, final Class type) throws IOException {

		return new ObjectMapper()
				.readValue(jsonByteArray, type);
	}

}
