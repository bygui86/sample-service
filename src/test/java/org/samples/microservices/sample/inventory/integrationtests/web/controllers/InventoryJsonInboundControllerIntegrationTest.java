package org.samples.microservices.sample.inventory.integrationtests.web.controllers;

import org.junit.Test;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.samples.microservices.sample.testingutils.JsonUtils.toJsonByteArray;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.http.MediaType.APPLICATION_JSON;


/**
 * @author Matteo Baiguini
 * matteo@solidarchitectures.com
 * 16 Sep 2018
 */
public class InventoryJsonInboundControllerIntegrationTest extends AbstractInventoryInboundControllerIntegrationTest {

	@Test
	public void testInsert() throws Exception {

		// given
		mockRestServiceServer
				.expect(
						ExpectedCount.once(),
						MockRestRequestMatchers.requestTo(otherServiceUrl)
				)
				.andExpect(
						MockRestRequestMatchers.method(POST)
				)
				.andExpect(
						MockRestRequestMatchers.content()
								.contentType(APPLICATION_JSON)
				)
				.andExpect(
						MockRestRequestMatchers.content()
								.bytes(toJsonByteArray(mocked_inventoryRequestDto))
				)
				.andRespond(
						MockRestResponseCreators.withSuccess(
								toJsonByteArray(mocked_inventoryResponseDto),
								APPLICATION_JSON
						)
				)
		;

		mockMvc
				// when
				.perform(
						MockMvcRequestBuilders
								.post(sampleServiceUrl)
								.contentType(APPLICATION_JSON)
								.accept(APPLICATION_JSON)
								.content(toJsonByteArray(inventoryRequestDto))
				)

				// then
				.andExpect(
						MockMvcResultMatchers
								.status().isCreated()
				)
				.andExpect(
						MockMvcResultMatchers.content()
								.contentTypeCompatibleWith(APPLICATION_JSON)
				)
				.andExpect(
						MockMvcResultMatchers.jsonPath(
								"$.responses",
								hasSize(2))
				)
				// valid
				.andExpect(
						MockMvcResultMatchers.jsonPath(
								"$.responses[0].inventoryId",
								is(inventoryId1))
				)
				.andExpect(
						MockMvcResultMatchers.jsonPath(
								"$.responses[0].status",
								is(inventoryResponseStatus1.getValue()))
				)
				// not-valid
				.andExpect(
						MockMvcResultMatchers.jsonPath(
								"$.responses[1].inventoryId",
								is(inventoryId2))
				)
				.andExpect(
						MockMvcResultMatchers.jsonPath(
								"$.responses[1].status",
								is(inventoryResponseStatus2.getValue()))
				)
				.andExpect(
						MockMvcResultMatchers.jsonPath(
								"$.responses[1].errorMessage",
								is(inventoryResponseDtoErrorMessage2))
				);

		// verify
		mockRestServiceServer.verify();
	}

	@Test
	public void testUpdate() throws Exception {

		// given
		mockRestServiceServer
				.expect(
						ExpectedCount.once(),
						MockRestRequestMatchers.requestTo(otherServiceUrl)
				)
				.andExpect(
						MockRestRequestMatchers.method(PUT)
				)
				.andExpect(
						MockRestRequestMatchers.content()
								.contentType(APPLICATION_JSON)
				)
				.andExpect(
						MockRestRequestMatchers.content()
								.bytes(toJsonByteArray(mocked_inventoryRequestDto))
				)
				.andRespond(
						MockRestResponseCreators.withSuccess(
								toJsonByteArray(mocked_inventoryResponseDto),
								APPLICATION_JSON
						)
				)
		;

		mockMvc
				// when
				.perform(
						MockMvcRequestBuilders
								.put(sampleServiceUrl)
								.contentType(APPLICATION_JSON)
								.accept(APPLICATION_JSON)
								.content(toJsonByteArray(inventoryRequestDto))
				)

				// then
				.andExpect(
						MockMvcResultMatchers
								.status().isOk()
				)
				.andExpect(
						MockMvcResultMatchers.content()
								.contentTypeCompatibleWith(APPLICATION_JSON)
				)
				.andExpect(
						MockMvcResultMatchers.jsonPath(
								"$.responses",
								hasSize(2))
				)
				// valid
				.andExpect(
						MockMvcResultMatchers.jsonPath(
								"$.responses[0].inventoryId",
								is(inventoryId1))
				)
				.andExpect(
						MockMvcResultMatchers.jsonPath(
								"$.responses[0].status",
								is(inventoryResponseStatus1.getValue()))
				)
				// not-valid
				.andExpect(
						MockMvcResultMatchers.jsonPath(
								"$.responses[1].inventoryId",
								is(inventoryId2))
				)
				.andExpect(
						MockMvcResultMatchers.jsonPath(
								"$.responses[1].status",
								is(inventoryResponseStatus2.getValue()))
				)
				.andExpect(
						MockMvcResultMatchers.jsonPath(
								"$.responses[1].errorMessage",
								is(inventoryResponseDtoErrorMessage2))
				)
		;

		// verify
		mockRestServiceServer.verify();
	}

}
