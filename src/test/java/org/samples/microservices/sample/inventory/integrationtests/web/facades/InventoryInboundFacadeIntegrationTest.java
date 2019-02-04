package org.samples.microservices.sample.inventory.integrationtests.web.facades;

import org.samples.microservices.sample.constants.ProfileConstants;
import org.samples.microservices.sample.inventory.web.configs.InventoryOutboundRestConfig;
import org.samples.microservices.sample.inventory.web.converters.impl.InventoryInboundErrorConverterImpl;
import org.samples.microservices.sample.inventory.web.dtos.request.InventoryQuantityTypeEnumRequestDto;
import org.samples.microservices.sample.inventory.web.dtos.request.InventoryRequestDto;
import org.samples.microservices.sample.inventory.web.dtos.request.InventoryWriteRequestDto;
import org.samples.microservices.sample.inventory.web.dtos.response.InventoryResponseDto;
import org.samples.microservices.sample.inventory.web.dtos.response.InventoryStatusEnumResponseDto;
import org.samples.microservices.sample.inventory.web.dtos.response.InventoryWriteResponseDto;
import org.samples.microservices.sample.inventory.web.facade.impl.InventoryInboundFacadeImpl;
import org.samples.microservices.sample.inventory.web.services.impl.InventoryRestOutboundServiceImpl;
import org.samples.microservices.sample.inventory.web.validators.impl.InventoryInboundValidatorImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.samples.microservices.sample.testingutils.DtoUtils;
import org.samples.microservices.sample.testingutils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.client.ExpectedCount.once;
import static org.springframework.test.web.client.MockRestServiceServer.createServer;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;


/**
 * @author Matteo Baiguini
 * matteo@solidarchitectures.com
 * 3 Oct 2018
 */
@RunWith(SpringRunner.class)
@Import({
		InventoryOutboundRestConfig.class,
		InventoryInboundFacadeImpl.class,
		InventoryInboundValidatorImpl.class,
		InventoryRestOutboundServiceImpl.class,
		InventoryInboundErrorConverterImpl.class
})
// WARNING: we must specify this profile in order to load the right classes
@ActiveProfiles({ProfileConstants.SPRING_PROFILE_INBOUND_REST, ProfileConstants.SPRING_PROFILE_OUTBOUND_REST})
// WARNING: we must load the properties file because there is no ApplicationContext
@TestPropertySource(locations = "classpath:application-test.properties")
public class InventoryInboundFacadeIntegrationTest {

	@Autowired
	protected InventoryInboundFacadeImpl inventoryInboundFacade;

	@Autowired
	protected RestTemplate outboundRestTemplate;

	protected MockRestServiceServer mockRestServiceServer;

	@Value("${outbound.rest.inventory.service.inventories.url}")
	protected String inventoryServiceUrl;

	/* REQUESTS */

	protected final String inventoryId1 = "001";

	protected final String inventoryId2 = "002";

	protected final String posId1 = "POS-01";

	protected final String posId2 = "POS-02";

	protected final String productId1 = "PROD-01";

	protected final String productId2 = null;

	protected InventoryRequestDto mocked_inventoryRequestDto;

	protected InventoryRequestDto inventoryRequestDto;

	/* RESPONSES */

	protected String inventoryResponseDtoErrorMessage1 = null;

	protected String inventoryResponseDtoErrorMessage2 = "ProductID must NOT BE NULL";

	protected InventoryResponseDto mocked_inventoryResponseDto;

	protected InventoryResponseDto expected_inventoryResponseDto;

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

	@Test
	public void testInsert() throws IOException {

		// given
		mockRestServiceServer
				.expect(
						once(),
						requestTo(inventoryServiceUrl)
				)
				.andExpect(
						method(POST)
				)
				.andExpect(
						content().contentType(APPLICATION_JSON)
				)
				.andExpect(
						content().bytes(JsonUtils.toJsonByteArray(mocked_inventoryRequestDto))
				)
				.andRespond(
						MockRestResponseCreators.withSuccess(
								JsonUtils.toJsonByteArray(mocked_inventoryResponseDto),
								APPLICATION_JSON
						)
				)
		;

		// when
		final InventoryResponseDto response = inventoryInboundFacade.insert(inventoryRequestDto);

		// then
		assertThat(response)
				.isEqualTo(expected_inventoryResponseDto);

		// verify
		mockRestServiceServer.verify();
	}

	@Test
	public void testUpdate() throws IOException {

		// given
		mockRestServiceServer
				.expect(
						once(),
						requestTo(inventoryServiceUrl)
				)
				.andExpect(
						method(PUT)
				)
				.andExpect(
						content().contentType(APPLICATION_JSON)
				)
				.andExpect(
						content().bytes(JsonUtils.toJsonByteArray(mocked_inventoryRequestDto))
				)
				.andRespond(
						MockRestResponseCreators.withSuccess(
								JsonUtils.toJsonByteArray(mocked_inventoryResponseDto),
								APPLICATION_JSON
						)
				)
		;

		// when
		final InventoryResponseDto response = inventoryInboundFacade.update(inventoryRequestDto);

		// then
		assertThat(response)
				.isEqualTo(expected_inventoryResponseDto);

		// verify
		mockRestServiceServer.verify();
	}

	@Test
	public void testDeleteAll() {

		// given
		mockRestServiceServer
				.expect(
						once(),
						requestTo(inventoryServiceUrl)
				)
				.andExpect(
						method(DELETE)
				)
				.andRespond(
						withSuccess()
				)
		;

		// when
		inventoryInboundFacade.delete(null, null);

		// then
		// -

		// verify
		mockRestServiceServer.verify();
	}

	@Test
	public void testDeleteAllByPosId() {

		// given
		// posId1
		mockRestServiceServer
				.expect(
						once(),
						requestTo(getInventoryServiceDeletePosEndpoint())
				)
				.andExpect(
						method(DELETE)
				)
				.andRespond(
						withSuccess()
				)
		;

		// when
		inventoryInboundFacade.delete(null, posId1);

		// then
		// -

		// verify
		mockRestServiceServer.verify();
	}

	@Test
	public void testDeleteAllByProductId() {

		// given
		// productId1
		mockRestServiceServer
				.expect(
						once(),
						requestTo(getInventoryServiceDeleteProductEndpoint())
				)
				.andExpect(
						method(DELETE)
				)
				.andRespond(
						withSuccess()
				)
		;

		// when
		inventoryInboundFacade.delete(productId1, null);

		// then
		// -

		// verify
		mockRestServiceServer.verify();
	}

	@Test
	public void testDeleteAllByProductIdAndPosId() {

		// given
		// posId1
		// productId1
		mockRestServiceServer
				.expect(
						once(),
						requestTo(getInventoryServiceDeleteProductPosEndpoint())
				)
				.andExpect(
						method(DELETE)
				)
				.andRespond(
						withSuccess()
				)
		;

		// when
		inventoryInboundFacade.delete(productId1, posId1);

		// then
		// -

		// verify
		mockRestServiceServer.verify();
	}

	private void prepareRequests() {

		// write request dtos
		final InventoryWriteRequestDto valid_inventoryWriteRequestDto =
				DtoUtils.createInventoryWriteRequestDto(inventoryId1, posId1, productId1, 3, InventoryQuantityTypeEnumRequestDto.AVAILABLE);
		final InventoryWriteRequestDto notvalid_inventoryWriteRequestDto =
				DtoUtils.createInventoryWriteRequestDto(inventoryId2, posId2, productId2, 3, InventoryQuantityTypeEnumRequestDto.AVAILABLE);

		// request dto
		inventoryRequestDto = DtoUtils.createInventoryRequestDto(valid_inventoryWriteRequestDto, notvalid_inventoryWriteRequestDto);

		// request dto expected by mocked rest template
		mocked_inventoryRequestDto = DtoUtils.createInventoryRequestDto(valid_inventoryWriteRequestDto);
	}

	private void prepareResponses() {

		// write response dtos
		final InventoryWriteResponseDto ok_inventoryWriteResponseDto =
				DtoUtils.createInventoryWriteResponseDto(inventoryId1, InventoryStatusEnumResponseDto.OK, inventoryResponseDtoErrorMessage1);
		final InventoryWriteResponseDto error_inventoryWriteResponseDto =
				DtoUtils.createInventoryWriteResponseDto(inventoryId2, InventoryStatusEnumResponseDto.ERR, inventoryResponseDtoErrorMessage2);

		// expected response dto
		mocked_inventoryResponseDto = DtoUtils.createInventoryResponseDto(ok_inventoryWriteResponseDto);
		expected_inventoryResponseDto = DtoUtils.createInventoryResponseDto(ok_inventoryWriteResponseDto, error_inventoryWriteResponseDto);
	}

	protected String getInventoryServiceDeletePosEndpoint() {

		return inventoryServiceUrl + "?posId=" + posId1;
	}

	protected String getInventoryServiceDeleteProductEndpoint() {

		return inventoryServiceUrl + "?productId=" + productId1;
	}

	protected String getInventoryServiceDeleteProductPosEndpoint() {

		return inventoryServiceUrl + "?productId=" + productId1 + "&posId=" + posId1;
	}

}
