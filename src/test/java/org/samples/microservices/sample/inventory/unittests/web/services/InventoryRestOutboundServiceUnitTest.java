package org.samples.microservices.sample.inventory.unittests.web.services;

import org.samples.microservices.sample.inventory.web.dtos.request.InventoryQuantityTypeEnumRequestDto;
import org.samples.microservices.sample.inventory.web.dtos.request.InventoryRequestDto;
import org.samples.microservices.sample.inventory.web.dtos.request.InventoryWriteRequestDto;
import org.samples.microservices.sample.inventory.web.dtos.response.InventoryResponseDto;
import org.samples.microservices.sample.inventory.web.dtos.response.InventoryStatusEnumResponseDto;
import org.samples.microservices.sample.inventory.web.dtos.response.InventoryWriteResponseDto;
import org.samples.microservices.sample.inventory.web.services.impl.InventoryRestOutboundServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.samples.microservices.sample.inventory.web.utils.InventoryUrlUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.http.HttpStatus.OK;


/**
 * @author Matteo Baiguini
 * matteo@solidarchitectures.com
 * 18 Sep 2018
 */
@RunWith(MockitoJUnitRunner.class)
public class InventoryRestOutboundServiceUnitTest {

	@InjectMocks
	protected InventoryRestOutboundServiceImpl inventoryOutboundService;

	@Mock
	protected RestTemplate outboundRestTemplate;

	private final String protocol = "http";

	private final String host = "localhost";

	private final int port = 8182;

	private final String endpoint = "inventories";

	protected final String inventoryId = "001";

	protected final String posId = "POS-01";

	protected final String productId = "PROD-01";

	@Before
	public void setUp() {

		/*
			PLEASE NOTE:
			Here we have two solutions:
				. use @RunWith(SpringRunner.class) and inject everything more or less like a integration test
				. use reflections to set private fields
		 */
		ReflectionTestUtils.setField(inventoryOutboundService, "inventoryUrl", getInventoryUrl());
	}

	@Test
	public void testInsert() {

		// given
		final List<InventoryWriteRequestDto> inventoryWriteRequestDtoList = createRequestList();
		final InventoryRequestDto inventoryRequestDto = InventoryRequestDto.builder().requestDtos(inventoryWriteRequestDtoList).build();
		final InventoryResponseDto inventoryResponseDto = createResponse();

		given(outboundRestTemplate.postForObject(
				getInventoryUrl(),
				InventoryUrlUtils.buildInventoryRequestHttpEntity(inventoryRequestDto),
				InventoryResponseDto.class)
		).willReturn(inventoryResponseDto);

		// when
		final InventoryResponseDto response = inventoryOutboundService.insert(inventoryWriteRequestDtoList);

		// then
		assertThat(response)
				.isEqualTo(inventoryResponseDto);

		// verify
		verify(outboundRestTemplate, times(1))
				.postForObject(getInventoryUrl(), InventoryUrlUtils.buildInventoryRequestHttpEntity(inventoryRequestDto), InventoryResponseDto.class);
	}

	@Test
	public void testUpdate() {

		// given
		final List<InventoryWriteRequestDto> inventoryWriteRequestDtoList = createRequestList();
		final InventoryRequestDto inventoryRequestDto = InventoryRequestDto.builder().requestDtos(inventoryWriteRequestDtoList).build();
		final InventoryResponseDto inventoryResponseDto = createResponse();
		final ResponseEntity<InventoryResponseDto> inventoryResponseEntity = new ResponseEntity<>(inventoryResponseDto, OK);

		given(outboundRestTemplate.exchange(
				getInventoryUrl(),
				PUT,
				InventoryUrlUtils.buildInventoryRequestHttpEntity(inventoryRequestDto),
				InventoryResponseDto.class)
		).willReturn(inventoryResponseEntity);

		// when
		final InventoryResponseDto response = inventoryOutboundService.update(inventoryWriteRequestDtoList);

		// then
		assertThat(response)
				.isEqualTo(inventoryResponseDto);

		// verify
		verify(outboundRestTemplate, times(1))
				.exchange(getInventoryUrl(), PUT, InventoryUrlUtils.buildInventoryRequestHttpEntity(inventoryRequestDto), InventoryResponseDto.class);
	}

	@Test
	public void testDeleteAll() {

		// given
		// -

		// when
		inventoryOutboundService.delete(null, null);

		// verify
		verify(outboundRestTemplate, times(1))
				.delete(InventoryUrlUtils.getInventoryDeleteUrl(getInventoryUrl(), null, null));
	}

	@Test
	public void testDeleteAllByPosId() {

		// given
		// posId

		// when
		inventoryOutboundService.delete(null, posId);

		// verify
		verify(outboundRestTemplate, times(1))
				.delete(InventoryUrlUtils.getInventoryDeleteUrl(getInventoryUrl(), null, posId));
	}

	@Test
	public void testDeleteAllByProductId() {

		// given
		// productId

		// when
		inventoryOutboundService.delete(productId, null);

		// verify
		verify(outboundRestTemplate, times(1))
				.delete(InventoryUrlUtils.getInventoryDeleteUrl(getInventoryUrl(), productId, null));
	}

	@Test
	public void testDeleteAllByProductIdAndPosId() {

		// given
		// posId
		// productId

		// when
		inventoryOutboundService.delete(productId, posId);

		// verify
		verify(outboundRestTemplate, times(1))
				.delete(InventoryUrlUtils.getInventoryDeleteUrl(getInventoryUrl(), productId, posId));
	}

	private String getInventoryUrl() {

		return protocol + "://" + host + ":" + port + "/" + endpoint;
	}

	protected List<InventoryWriteRequestDto> createRequestList() {

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

	protected InventoryResponseDto createResponse() {

		final InventoryWriteResponseDto inventoryWriteResponseDto =
				InventoryWriteResponseDto.builder()
						.inventoryId(inventoryId)
						.status(InventoryStatusEnumResponseDto.OK)
						.build();
		final List<InventoryWriteResponseDto> inventoryWriteResponseDtoList = new ArrayList<>(1);
		inventoryWriteResponseDtoList.add(inventoryWriteResponseDto);
		return InventoryResponseDto.builder().responses(inventoryWriteResponseDtoList).build();
	}

}