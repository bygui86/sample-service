package org.samples.microservices.sample.inventory.unittests.web.controllers;

import org.junit.Test;

import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * @author Matteo Baiguini
 * matteo@solidarchitectures.com
 * 16 Sep 2018
 */
public class InventoryInboundControllerUnitTest extends AbstractInventoryInboundControllerUnitTest {

	@Test
	public void testDeleteAll() throws Exception {

		// given
		// -

		mockMvc
				// when
				.perform(
						delete(sampleServiceEndpoint)
				)

				// then
				.andExpect(
						status().isOk()
				)
		;

		// verify
		verify(inventoryInboundFacade, times(1))
				.delete(null, null);
	}

	@Test
	public void testDeleteAllByPosId() throws Exception {

		// given
		// posId1

		mockMvc
				// when
				.perform(
						delete(getSampleServiceDeletePosEndpoint())
				)

				// then
				.andExpect(
						status().isOk()
				)
		;

		// verify
		verify(inventoryInboundFacade, times(1))
				.delete(null, posId1);
	}

	@Test
	public void testDeleteAllByProductId() throws Exception {

		// given
		// productId1

		mockMvc
				// when
				.perform(
						delete(getSampleServiceDeleteProductEndpoint())
				)

				// then
				.andExpect(
						status().isOk()
				)
		;

		// verify
		verify(inventoryInboundFacade, times(1))
				.delete(productId1, null);
	}

	@Test
	public void testDeleteAllByProductIdAndPosId() throws Exception {

		// given
		// posId1
		// productId1

		mockMvc
				// when
				.perform(
						delete(getSampleServiceDeleteProductPosEndpoint())
				)

				// then
				.andExpect(
						status().isOk()
				)
		;

		// verify
		verify(inventoryInboundFacade, times(1))
				.delete(productId1, posId1);
	}

}
