package org.samples.microservices.sample.inventory.integrationtests.web.controllers;

import org.junit.Test;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.http.HttpMethod.DELETE;


/**
 * @author Matteo Baiguini
 * matteo@solidarchitectures.com
 * 16 Sep 2018
 */
public class InventoryInboundControllerIntegrationTest extends AbstractInventoryInboundControllerIntegrationTest {

	@Test
	public void testDeleteAll() throws Exception {

		// given
		mockRestServiceServer
				.expect(
						ExpectedCount.once(),
						MockRestRequestMatchers.requestTo(otherServiceUrl)
				)
				.andExpect(
						MockRestRequestMatchers.method(DELETE)
				)
				.andRespond(
						MockRestResponseCreators.withSuccess()
				)
		;

		mockMvc
				// when
				.perform(
						MockMvcRequestBuilders.delete(sampleServiceUrl)
				)

				// then
				.andExpect(
						MockMvcResultMatchers.status().isOk()
				)
		;

		// verify
		mockRestServiceServer.verify();
	}

	@Test
	public void testDeleteAllByPosId() throws Exception {

		// given
		// posId1
		mockRestServiceServer
				.expect(
						ExpectedCount.once(),
						MockRestRequestMatchers.requestTo(getOtherServiceDeletePosUrl())
				)
				.andExpect(
						MockRestRequestMatchers.method(DELETE)
				)
				.andRespond(
						MockRestResponseCreators.withSuccess()
				)
		;

		mockMvc
				// when
				.perform(
						MockMvcRequestBuilders.delete(getSampleServiceDeletePosUrl())
				)

				// then
				.andExpect(
						MockMvcResultMatchers.status().isOk()
				)
		;

		// verify
		mockRestServiceServer.verify();
	}

	@Test
	public void testDeleteAllByProductId() throws Exception {

		// given
		// productId1
		mockRestServiceServer
				.expect(
						ExpectedCount.once(),
						MockRestRequestMatchers.requestTo(getOtherServiceDeleteProductUrl())
				)
				.andExpect(
						MockRestRequestMatchers.method(DELETE)
				)
				.andRespond(
						MockRestResponseCreators.withSuccess()
				)
		;

		mockMvc
				// when
				.perform(
						MockMvcRequestBuilders.delete(getSampleServiceDeleteProductUrl())
				)

				// then
				.andExpect(
						MockMvcResultMatchers.status().isOk()
				)
		;

		// verify
		mockRestServiceServer.verify();
	}

	@Test
	public void testDeleteAllByProductIdAndPosId() throws Exception {

		// given
		// posId1
		// productId1
		mockRestServiceServer
				.expect(
						ExpectedCount.once(),
						MockRestRequestMatchers.requestTo(getOtherServiceDeleteProductPosUrl())
				)
				.andExpect(
						MockRestRequestMatchers.method(DELETE)
				)
				.andRespond(
						MockRestResponseCreators.withSuccess()
				)
		;

		mockMvc
				// when
				.perform(
						MockMvcRequestBuilders.delete(getSampleServiceDeleteProductPosUrl())
				)

				// then
				.andExpect(
						MockMvcResultMatchers.status().isOk()
				)
		;

		// verify
		mockRestServiceServer.verify();
	}

}
