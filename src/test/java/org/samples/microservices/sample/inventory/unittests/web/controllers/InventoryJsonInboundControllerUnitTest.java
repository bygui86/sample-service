package org.samples.microservices.sample.inventory.unittests.web.controllers;

import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.samples.microservices.sample.testingutils.JsonUtils;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * @author Matteo Baiguini
 * matteo@solidarchitectures.com
 * 16 Sep 2018
 */
public class InventoryJsonInboundControllerUnitTest extends AbstractInventoryInboundControllerUnitTest {

	@Test
	public void testInsert() throws Exception {

		// given
		/*
			PLEASE NOTE:
			If the object does not implement the equals and hashCode methods, this could lead to problems during mocking.
		 */
		given(inventoryInboundFacade.insert(inventoryRequestDto))
				.willReturn(inventoryResponseDto);

		mockMvc
				// when
				.perform(
						post(sampleServiceEndpoint)
								.contentType(APPLICATION_JSON)
								.accept(APPLICATION_JSON)
								.content(JsonUtils.toJsonByteArray(inventoryRequestDto))
				)

				// then
				.andExpect(
						status().isCreated()
				)
				.andExpect(
						content()
								.contentTypeCompatibleWith(APPLICATION_JSON)
				)
				.andExpect(
						jsonPath("$.responses", hasSize(2))
				)
				// valid
				.andExpect(
						MockMvcResultMatchers.jsonPath("$.responses[0].inventoryId", CoreMatchers.is(inventoryId1))
				)
				.andExpect(
						MockMvcResultMatchers.jsonPath("$.responses[0].status", CoreMatchers.is(inventoryResponseStatus1.getValue()))
				)
				// not-valid
				.andExpect(
						MockMvcResultMatchers.jsonPath("$.responses[1].inventoryId", CoreMatchers.is(inventoryId2))
				)
				.andExpect(
						MockMvcResultMatchers.jsonPath("$.responses[1].status", CoreMatchers.is(inventoryResponseStatus2.getValue()))
				)
				.andExpect(
						jsonPath("$.responses[1].errorMessage", is(inventoryResponseDtoErrorMessage2))
				)
		;

		// verify
		verify(inventoryInboundFacade, times(1))
				.insert(inventoryRequestDto);
	}

	@Test
	public void testUpdate() throws Exception {

		// given
		/*
			PLEASE NOTE:
			If the object does not implement the equals and hashCode methods, this could lead to problems during mocking.
		 */
		given(inventoryInboundFacade.update(inventoryRequestDto))
				.willReturn(inventoryResponseDto);

		mockMvc
				// when
				.perform(
						put(sampleServiceEndpoint)
								.contentType(APPLICATION_JSON)
								.accept(APPLICATION_JSON)
								.content(JsonUtils.toJsonByteArray(inventoryRequestDto))
				)

				// then
				.andExpect(
						status().isOk()
				)
				.andExpect(
						content()
								.contentTypeCompatibleWith(APPLICATION_JSON)
				)
				.andExpect(
						jsonPath("$.responses", hasSize(2))
				)
				// valid
				.andExpect(
						MockMvcResultMatchers.jsonPath("$.responses[0].inventoryId", CoreMatchers.is(inventoryId1))
				)
				.andExpect(
						MockMvcResultMatchers.jsonPath("$.responses[0].status", CoreMatchers.is(inventoryResponseStatus1.getValue()))
				)
				// not-valid
				.andExpect(
						MockMvcResultMatchers.jsonPath("$.responses[1].inventoryId", CoreMatchers.is(inventoryId2))
				)
				.andExpect(
						MockMvcResultMatchers.jsonPath("$.responses[1].status", CoreMatchers.is(inventoryResponseStatus2.getValue()))
				)
				.andExpect(
						jsonPath("$.responses[1].errorMessage", is(inventoryResponseDtoErrorMessage2))
				)
		;

		// verify
		verify(inventoryInboundFacade, times(1))
				.update(inventoryRequestDto);
	}

}
