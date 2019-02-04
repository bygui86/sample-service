package org.samples.microservices.sample.inventory.unittests.web.controllers;

import org.samples.microservices.sample.constants.ProfileConstants;
import org.samples.microservices.sample.inventory.commons.AbstractInventoryInboundControllerTest;
import org.samples.microservices.sample.inventory.web.controllers.InventoryInboundController;
import org.samples.microservices.sample.inventory.web.dtos.request.InventoryQuantityTypeEnumRequestDto;
import org.samples.microservices.sample.inventory.web.dtos.response.InventoryResponseDto;
import org.samples.microservices.sample.inventory.web.facade.InventoryInboundFacade;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.samples.microservices.sample.testingutils.DtoUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * @author Matteo Baiguini
 * matteo@solidarchitectures.com
 * 16 Sep 2018
 */
@RunWith(SpringRunner.class)
@WebMvcTest(InventoryInboundController.class)
@ActiveProfiles({
		ProfileConstants.SPRING_PROFILE_TEST, ProfileConstants.SPRING_PROFILE_INSECURE,
		// ProfileConstants.SPRING_PROFILE_INBOUND_REST, ProfileConstants.SPRING_PROFILE_OUTBOUND_MOCK
		ProfileConstants.SPRING_PROFILE_INBOUND_REST, ProfileConstants.SPRING_PROFILE_OUTBOUND_REST
})
public abstract class AbstractInventoryInboundControllerUnitTest extends AbstractInventoryInboundControllerTest {

	@Value("${inbound.rest.sample.service.inventories.endpoint}")
	protected String sampleServiceEndpoint;

	@MockBean(name = "inventoryInboundFacade")
	protected InventoryInboundFacade inventoryInboundFacade;

	/* RESPONSES */

	/*
	    PLEASE NOTE:
	    It doesn't matter what's inside here, as the focus of this test is not to validate requests.
	    It's enough to set this string not empty or null
	  */
	protected String inventoryResponseDtoErrorMessage2 = "some errors";

	protected InventoryResponseDto inventoryResponseDto;

	@Before
	public void setUp() {

		prepareRequests();

		prepareResponses();
	}

	private void prepareRequests() {

		// request dto
		inventoryRequestDto = DtoUtils.createInventoryRequestDto(
				DtoUtils.createInventoryWriteRequestDto(inventoryId1, posId1, productId1, 3, InventoryQuantityTypeEnumRequestDto.AVAILABLE),
				DtoUtils.createInventoryWriteRequestDto(inventoryId2, posId2, productId2, 3, InventoryQuantityTypeEnumRequestDto.AVAILABLE)
		);
	}

	private void prepareResponses() {

		// response dto
		inventoryResponseDto = DtoUtils.createInventoryResponseDto(
				DtoUtils.createInventoryWriteResponseDto(inventoryId1, inventoryResponseStatus1, inventoryResponseDtoErrorMessage1),
				DtoUtils.createInventoryWriteResponseDto(inventoryId2, inventoryResponseStatus2, inventoryResponseDtoErrorMessage2)
		);
	}

	protected String getSampleServiceDeletePosEndpoint() {

		return sampleServiceEndpoint + "?posId=" + posId1;
	}

	protected String getSampleServiceDeleteProductEndpoint() {

		return sampleServiceEndpoint + "?productId=" + productId1;
	}

	protected String getSampleServiceDeleteProductPosEndpoint() {

		return sampleServiceEndpoint + "?productId=" + productId1 + "&posId=" + posId1;
	}

}
