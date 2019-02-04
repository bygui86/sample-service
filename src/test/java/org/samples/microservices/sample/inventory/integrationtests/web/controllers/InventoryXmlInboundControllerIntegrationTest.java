package org.samples.microservices.sample.inventory.integrationtests.web.controllers;

import org.junit.Test;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.samples.microservices.sample.testingutils.JsonUtils.toJsonByteArray;
import static org.samples.microservices.sample.testingutils.XmlUtils.toXml;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_XML;


/**
 * @author Matteo Baiguini
 * matteo@solidarchitectures.com
 * 16 Sep 2018
 */
public class InventoryXmlInboundControllerIntegrationTest extends AbstractInventoryInboundControllerIntegrationTest {

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
								.contentType(APPLICATION_XML)
								.accept(APPLICATION_XML)
								.content(toXml(inventoryRequestDto))
				)

				// then
				.andExpect(
						MockMvcResultMatchers
								.status().isCreated()
				)
				.andExpect(
						MockMvcResultMatchers.content()
								.contentTypeCompatibleWith(APPLICATION_XML)
				)
				.andExpect(
						MockMvcResultMatchers
								.xpath("/inventories")
								.nodeCount(1)
				)
				.andExpect(
						MockMvcResultMatchers
								.xpath("/inventories/inventory")
								.nodeCount(2)
				)
				// valid
				.andExpect(
						MockMvcResultMatchers
								.xpath("/inventories/inventory[1]/inventoryId")
								.string(inventoryId1)
				)
				.andExpect(
						MockMvcResultMatchers
								.xpath("/inventories/inventory[1]/status")
								.string(inventoryResponseStatus1.getValue())
				)
				// not-valid
				.andExpect(
						MockMvcResultMatchers
								.xpath("/inventories/inventory[2]/inventoryId")
								.string(inventoryId2)
				)
				.andExpect(
						MockMvcResultMatchers
								.xpath("/inventories/inventory[2]/status")
								.string(inventoryResponseStatus2.getValue())
				)
				.andExpect(
						MockMvcResultMatchers
								.xpath("/inventories/inventory[2]/errorMessage")
								.string(inventoryResponseDtoErrorMessage2)
				)
		;

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
								.contentType(APPLICATION_XML)
								.accept(APPLICATION_XML)
								.content(toXml(inventoryRequestDto))
				)

				// then
				.andExpect(
						MockMvcResultMatchers
								.status().isOk()
				)
				.andExpect(
						MockMvcResultMatchers.content()
								.contentTypeCompatibleWith(APPLICATION_XML)
				)
				.andExpect(
						MockMvcResultMatchers
								.xpath("/inventories")
								.nodeCount(1)
				)
				// valid
				.andExpect(
						MockMvcResultMatchers
								.xpath("/inventories/inventory[1]/inventoryId")
								.string(inventoryId1)
				)
				.andExpect(
						MockMvcResultMatchers
								.xpath("/inventories/inventory[1]/status")
								.string(inventoryResponseStatus1.getValue())
				)
				// not-valid
				.andExpect(
						MockMvcResultMatchers
								.xpath("/inventories/inventory[2]/inventoryId")
								.string(inventoryId2)
				)
				.andExpect(
						MockMvcResultMatchers
								.xpath("/inventories/inventory[2]/status")
								.string(inventoryResponseStatus2.getValue())
				)
				.andExpect(
						MockMvcResultMatchers
								.xpath("/inventories/inventory[2]/errorMessage")
								.string(inventoryResponseDtoErrorMessage2)
				)
		;

		// verify
		mockRestServiceServer.verify();
	}

}
