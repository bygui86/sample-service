package org.samples.microservices.sample.inventory.integrationtests.web.controllers;

import org.samples.microservices.sample.constants.ProfileConstants;
import org.samples.microservices.sample.inventory.commons.AbstractInventoryInboundControllerTest;
import org.samples.microservices.sample.inventory.web.dtos.request.InventoryQuantityTypeEnumRequestDto;
import org.samples.microservices.sample.inventory.web.dtos.request.InventoryRequestDto;
import org.samples.microservices.sample.inventory.web.dtos.request.InventoryWriteRequestDto;
import org.samples.microservices.sample.inventory.web.dtos.response.InventoryResponseDto;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.samples.microservices.sample.testingutils.DtoUtils.createInventoryRequestDto;
import static org.samples.microservices.sample.testingutils.DtoUtils.createInventoryResponseDto;
import static org.samples.microservices.sample.testingutils.DtoUtils.createInventoryWriteRequestDto;
import static org.samples.microservices.sample.testingutils.DtoUtils.createInventoryWriteResponseDto;
import static org.springframework.test.web.client.MockRestServiceServer.createServer;


/**
 * @author Matteo Baiguini
 * matteo@solidarchitectures.com
 * 16 Sep 2018
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles({
		ProfileConstants.SPRING_PROFILE_INSECURE, ProfileConstants.SPRING_PROFILE_TEST,
		ProfileConstants.SPRING_PROFILE_INBOUND_REST, ProfileConstants.SPRING_PROFILE_OUTBOUND_REST
})
@AutoConfigureMockMvc
public abstract class AbstractInventoryInboundControllerIntegrationTest extends AbstractInventoryInboundControllerTest {

	@Value("${inbound.rest.sample.service.inventories.url}")
	protected String sampleServiceUrl;

	@Value("${outbound.rest.other.service.inventories.url}")
	protected String otherServiceUrl;

	@Autowired
	protected RestTemplate outboundRestTemplate;

	protected MockRestServiceServer mockRestServiceServer;

	/* REQUESTS */

	protected InventoryRequestDto mocked_inventoryRequestDto;

	/* RESPONSES */

	protected String inventoryResponseDtoErrorMessage2 = "ProductID must NOT BE NULL";

	protected InventoryResponseDto mocked_inventoryResponseDto;

	@Before
	public void setUp() {

		mockRestServiceServer = createServer(outboundRestTemplate);

		prepareRequests();

		prepareResponses();
	}

	@After
	public void tearDown() {

		mockRestServiceServer.reset();
	}

	private void prepareRequests() {

		// write request dtos
		final InventoryWriteRequestDto valid_inventoryWriteRequestDto =
				createInventoryWriteRequestDto(inventoryId1, posId1, productId1, 3, InventoryQuantityTypeEnumRequestDto.AVAILABLE);
		final InventoryWriteRequestDto notvalid_inventoryWriteRequestDto =
				createInventoryWriteRequestDto(inventoryId2, posId2, productId2, 3, InventoryQuantityTypeEnumRequestDto.AVAILABLE);

		// request dto
		inventoryRequestDto = createInventoryRequestDto(valid_inventoryWriteRequestDto, notvalid_inventoryWriteRequestDto);

		// request dto expected by mocked rest template
		mocked_inventoryRequestDto = createInventoryRequestDto(valid_inventoryWriteRequestDto);
	}

	private void prepareResponses() {

		// expected response dto
		mocked_inventoryResponseDto =
				createInventoryResponseDto(
						createInventoryWriteResponseDto(inventoryId1, inventoryResponseStatus1, inventoryResponseDtoErrorMessage1)
				);
	}

	protected String getSampleServiceDeletePosUrl() {

		return sampleServiceUrl + "?posId=" + posId1;
	}

	protected String getSampleServiceDeleteProductUrl() {

		return sampleServiceUrl + "?productId=" + productId1;
	}

	protected String getSampleServiceDeleteProductPosUrl() {

		return sampleServiceUrl + "?productId=" + productId1 + "&posId=" + posId1;
	}

	protected String getOtherServiceDeletePosUrl() {

		return otherServiceUrl + "?posId=" + posId1;
	}

	protected String getOtherServiceDeleteProductUrl() {

		return otherServiceUrl + "?productId=" + productId1;
	}

	protected String getOtherServiceDeleteProductPosUrl() {

		return otherServiceUrl + "?productId=" + productId1 + "&posId=" + posId1;
	}

}
