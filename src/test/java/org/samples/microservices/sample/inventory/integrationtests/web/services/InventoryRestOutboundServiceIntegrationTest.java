package org.samples.microservices.sample.inventory.integrationtests.web.services;

import org.samples.microservices.sample.constants.ProfileConstants;
import org.samples.microservices.sample.inventory.web.configs.InventoryOutboundRestConfig;
import org.samples.microservices.sample.inventory.web.dtos.request.InventoryQuantityTypeEnumRequestDto;
import org.samples.microservices.sample.inventory.web.dtos.request.InventoryRequestDto;
import org.samples.microservices.sample.inventory.web.dtos.request.InventoryWriteRequestDto;
import org.samples.microservices.sample.inventory.web.dtos.response.InventoryResponseDto;
import org.samples.microservices.sample.inventory.web.dtos.response.InventoryStatusEnumResponseDto;
import org.samples.microservices.sample.inventory.web.dtos.response.InventoryWriteResponseDto;
import org.samples.microservices.sample.inventory.web.services.impl.InventoryRestOutboundServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
import java.util.ArrayList;
import java.util.List;

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
 * 18 Sep 2018
 */
@RunWith(SpringRunner.class)
@Import({
		InventoryOutboundRestConfig.class,
		InventoryRestOutboundServiceImpl.class
})
// WARNING: we must specify this profile in order to load the right classes
@ActiveProfiles({ProfileConstants.SPRING_PROFILE_OUTBOUND_REST})
// WARNING: we must load the properties file because there is no ApplicationContext
@TestPropertySource(locations = "classpath:application-test.properties")
public class InventoryRestOutboundServiceIntegrationTest {

	@Autowired
	protected InventoryRestOutboundServiceImpl inventoryOutboundService;

	@Autowired
	protected RestTemplate outboundRestTemplate;

	protected MockRestServiceServer mockRestServiceServer;

	@Value("${outbound.rest.inventory.service.inventories.url}")
	protected String inventoryServiceUrl;

	protected final String inventoryId = "001";

	protected final String posId = "POS-01";

	protected final String productId = "PROD-01";

	@Before
	public void setUp() {

		mockRestServiceServer = createServer(outboundRestTemplate);
	}

	@After
	public void tearDown() {

		mockRestServiceServer.reset();
	}

	@Test
	public void testInsert() throws IOException {

		// given
		final List<InventoryWriteRequestDto> inventoryWriteRequestDtoList = createRequestList();
		final InventoryRequestDto inventoryRequestDto = InventoryRequestDto.builder().requestDtos(inventoryWriteRequestDtoList).build();
		final InventoryResponseDto inventoryResponseDto = createResponse();

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
						content().bytes(JsonUtils.toJsonByteArray(inventoryRequestDto))
				)
				.andRespond(
						MockRestResponseCreators.withSuccess(
								JsonUtils.toJsonByteArray(inventoryResponseDto),
								APPLICATION_JSON
						)
				)
		;

		// when
		final InventoryResponseDto response = inventoryOutboundService.insert(inventoryWriteRequestDtoList);

		// then
		assertThat(response)
				.isEqualTo(inventoryResponseDto);

		// verify
		mockRestServiceServer.verify();
	}

	@Test
	public void testUpdate() throws IOException {

		// given
		final List<InventoryWriteRequestDto> inventoryWriteRequestDtoList = createRequestList();
		final InventoryRequestDto inventoryRequestDto = InventoryRequestDto.builder().requestDtos(inventoryWriteRequestDtoList).build();
		final InventoryResponseDto inventoryResponseDto = createResponse();

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
						content().bytes(JsonUtils.toJsonByteArray(inventoryRequestDto))
				)
				.andRespond(
						MockRestResponseCreators.withSuccess(
								JsonUtils.toJsonByteArray(inventoryResponseDto),
								APPLICATION_JSON
						)
				)
		;

		// when
		final InventoryResponseDto response = inventoryOutboundService.update(inventoryWriteRequestDtoList);

		// then
		assertThat(response)
				.isEqualTo(inventoryResponseDto);

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
		inventoryOutboundService.delete(null, null);

		// verify
		mockRestServiceServer.verify();
	}

	@Test
	public void testDeleteAllByPosId() {

		// given
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
		inventoryOutboundService.delete(null, posId);

		// verify
		mockRestServiceServer.verify();
	}

	@Test
	public void testDeleteAllByProductId() {

		// given
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
		inventoryOutboundService.delete(productId, null);

		// verify
		mockRestServiceServer.verify();
	}

	@Test
	public void testDeleteAllByProductIdAndPosId() {

		// given
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
		inventoryOutboundService.delete(productId, posId);

		// verify
		mockRestServiceServer.verify();
	}

	private List<InventoryWriteRequestDto> createRequestList() {

		final InventoryWriteRequestDto inventoryWriteRequestDto =
				InventoryWriteRequestDto.builder()
						.inventoryId(inventoryId)
						.posId(posId)
						.productId(productId)
						.quantity(3)
						.quantityType(InventoryQuantityTypeEnumRequestDto.AVAILABLE)
						.build();
		final List<InventoryWriteRequestDto> inventoryWriteRequestDtoList = new ArrayList<>(2);
		inventoryWriteRequestDtoList.add(inventoryWriteRequestDto);
		return inventoryWriteRequestDtoList;
	}

	private InventoryResponseDto createResponse() {

		final InventoryWriteResponseDto inventoryWriteResponseDto =
				InventoryWriteResponseDto.builder()
						.inventoryId(inventoryId)
						.status(InventoryStatusEnumResponseDto.OK)
						.build();
		final List<InventoryWriteResponseDto> inventoryWriteResponseDtoList = new ArrayList<>(1);
		inventoryWriteResponseDtoList.add(inventoryWriteResponseDto);
		return InventoryResponseDto.builder().responses(inventoryWriteResponseDtoList).build();
	}

	protected String getInventoryServiceDeletePosEndpoint() {

		return inventoryServiceUrl + "?posId=" + posId;
	}

	protected String getInventoryServiceDeleteProductEndpoint() {

		return inventoryServiceUrl + "?productId=" + productId;
	}

	protected String getInventoryServiceDeleteProductPosEndpoint() {

		return inventoryServiceUrl + "?productId=" + productId + "&posId=" + posId;
	}

}