package org.samples.microservices.sample.inventory.unittests.web.controllers;

import org.junit.Test;
import org.mockito.internal.verification.VerificationModeFactory;
import org.samples.microservices.sample.testingutils.XmlUtils;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_XML;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;


/**
 * @author Matteo Baiguini
 * matteo@solidarchitectures.com
 * 16 Sep 2018
 */
public class InventoryXmlInboundControllerUnitTest extends AbstractInventoryInboundControllerUnitTest {

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
								.contentType(APPLICATION_XML)
								.accept(APPLICATION_XML)
								.content(XmlUtils.toXml(inventoryRequestDto))
				)

				// then
				.andExpect(
						status().isCreated()
				)
				.andExpect(
						content()
								.contentTypeCompatibleWith(APPLICATION_XML)
				)
				.andExpect(
						xpath("/inventories").nodeCount(1)
				)
				.andExpect(
						xpath("/inventories/inventory").nodeCount(2)
				)
				// valid
				.andExpect(
						xpath("/inventories/inventory[1]/inventoryId").string(inventoryId1)
				)
				.andExpect(
						xpath("/inventories/inventory[1]/status").string(inventoryResponseStatus1.getValue())
				)
				// not-valid
				.andExpect(
						xpath("/inventories/inventory[2]/inventoryId").string(inventoryId2)
				)
				.andExpect(
						xpath("/inventories/inventory[2]/status").string(inventoryResponseStatus2.getValue())
				)
				.andExpect(
						xpath("/inventories/inventory[2]/errorMessage").string(inventoryResponseDtoErrorMessage2)
				)
		;

		// verify
		verify(inventoryInboundFacade, VerificationModeFactory.times(1))
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
								.contentType(APPLICATION_XML)
								.accept(APPLICATION_XML)
								.content(XmlUtils.toXml(inventoryRequestDto))
				)

				// then
				.andExpect(
						status().isOk()
				)
				.andExpect(
						content()
								.contentTypeCompatibleWith(APPLICATION_XML)
				)
				.andExpect(
						xpath("/inventories").nodeCount(1)
				)
				// valid
				.andExpect(
						xpath("/inventories/inventory[1]/inventoryId").string(inventoryId1)
				)
				.andExpect(
						xpath("/inventories/inventory[1]/status").string(inventoryResponseStatus1.getValue())
				)
				// not-valid
				.andExpect(
						xpath("/inventories/inventory[2]/inventoryId").string(inventoryId2)
				)
				.andExpect(
						xpath("/inventories/inventory[2]/status").string(inventoryResponseStatus2.getValue())
				)
				.andExpect(
						xpath("/inventories/inventory[2]/errorMessage").string(inventoryResponseDtoErrorMessage2)
				)
		;

		// verify
		verify(inventoryInboundFacade, VerificationModeFactory.times(1))
				.update(inventoryRequestDto);
	}

}
