package org.samples.microservices.sample.inventory.unittests.web.facades;

import org.samples.microservices.sample.inventory.web.converters.InventoryInboundErrorConverter;
import org.samples.microservices.sample.inventory.web.dtos.request.InventoryQuantityTypeEnumRequestDto;
import org.samples.microservices.sample.inventory.web.dtos.request.InventoryRequestDto;
import org.samples.microservices.sample.inventory.web.dtos.request.InventoryWriteRequestDto;
import org.samples.microservices.sample.inventory.web.dtos.request.InventoryWriteTypeEnumRequestDto;
import org.samples.microservices.sample.inventory.web.dtos.response.InventoryResponseDto;
import org.samples.microservices.sample.inventory.web.dtos.response.InventoryStatusEnumResponseDto;
import org.samples.microservices.sample.inventory.web.dtos.response.InventoryWriteResponseDto;
import org.samples.microservices.sample.inventory.web.facade.impl.InventoryInboundFacadeImpl;
import org.samples.microservices.sample.inventory.web.services.InventoryOutboundService;
import org.samples.microservices.sample.inventory.web.validators.InventoryInboundValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.samples.microservices.sample.testingutils.DtoUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;


/**
 * @author Matteo Baiguini
 * matteo@solidarchitectures.com
 * 17 Sep 2018
 */
@RunWith(MockitoJUnitRunner.class)
public class InventoryInboundFacadeUnitTest {

	@InjectMocks
	protected InventoryInboundFacadeImpl inventoryInboundFacade;

	@Mock
	protected InventoryInboundValidator inventoryInboundValidator;

	@Mock
	protected InventoryOutboundService inventoryOutboundService;

	@Mock
	protected InventoryInboundErrorConverter inventoryInboundErrorConverter;

	/* REQUESTS */

	protected final String inventoryId1 = "001";

	protected final String inventoryId2 = "002";

	protected final String posId1 = "POS-01";

	protected final String posId2 = "POS-02";

	protected final String productId1 = "PROD-01";

	protected final String productId2 = null;

	protected List<InventoryWriteRequestDto> valid_inventoryWriteRequestDtoList;

	protected List<InventoryWriteRequestDto> notvalid_inventoryWriteRequestDtoList;

	protected InventoryRequestDto inventoryRequestDto;

	protected Map<InventoryWriteTypeEnumRequestDto, List<InventoryWriteRequestDto>> inventoryRequestValidationMap;

	/* RESPONSES */

	protected String inventoryResponseDtoErrorMessage1 = null;

	/*
	    PLEASE NOTE:
	    It doesn't matter what's inside here, as the focus of this test is not to validate requests.
	    It's enough to set this string not empty or null
	  */
	protected String inventoryResponseDtoErrorMessage2 = "some errors";

	protected InventoryResponseDto service_inventoryResponseDto;

	protected InventoryResponseDto expected_inventoryResponseDto;

	protected List<InventoryWriteResponseDto> error_inventoryWriteResponseDtoList;

	@Before
	public void setUp() {

		prepareRequests();

		prepareResponses();
	}

	@Test
	public void testInsert() {

		// given
		given(inventoryInboundValidator.validateInsert(inventoryRequestDto))
				.willReturn(inventoryRequestValidationMap);
		given(inventoryOutboundService.insert(valid_inventoryWriteRequestDtoList))
				.willReturn(service_inventoryResponseDto);
		given(inventoryInboundErrorConverter.errorRequestsToErrorResponses(notvalid_inventoryWriteRequestDtoList))
				.willReturn(error_inventoryWriteResponseDtoList);

		// when
		final InventoryResponseDto response = inventoryInboundFacade.insert(inventoryRequestDto);

		// then
		assertThat(response)
				.isEqualTo(expected_inventoryResponseDto);

		// verify
		verify(inventoryInboundValidator, times(1))
				.validateInsert(inventoryRequestDto);
		verify(inventoryOutboundService, times(1))
				.insert(valid_inventoryWriteRequestDtoList);
		verify(inventoryInboundErrorConverter, times(1))
				.errorRequestsToErrorResponses(notvalid_inventoryWriteRequestDtoList);
	}

	@Test
	public void testUpdate() {

		// given
		given(inventoryInboundValidator.validateUpdate(inventoryRequestDto))
				.willReturn(inventoryRequestValidationMap);
		given(inventoryOutboundService.update(valid_inventoryWriteRequestDtoList))
				.willReturn(service_inventoryResponseDto);
		given(inventoryInboundErrorConverter.errorRequestsToErrorResponses(notvalid_inventoryWriteRequestDtoList))
				.willReturn(error_inventoryWriteResponseDtoList);

		// when
		final InventoryResponseDto response = inventoryInboundFacade.update(inventoryRequestDto);

		// then
		assertThat(response)
				.isEqualTo(expected_inventoryResponseDto);

		// verify
		verify(inventoryInboundValidator, times(1))
				.validateUpdate(inventoryRequestDto);
		verify(inventoryOutboundService, times(1))
				.update(valid_inventoryWriteRequestDtoList);
		verify(inventoryInboundErrorConverter, times(1))
				.errorRequestsToErrorResponses(notvalid_inventoryWriteRequestDtoList);
	}

	@Test
	public void testDeleteAll() {

		// given
		// -

		// when
		inventoryInboundFacade.delete(null, null);

		// then
		// -

		// verify
		verify(inventoryOutboundService, times(1))
				.delete(null, null);
	}

	@Test
	public void testDeleteAllByPosId() {

		// given
		// posId1

		// when
		inventoryInboundFacade.delete(null, posId1);

		// then
		// -

		// verify
		verify(inventoryOutboundService, times(1))
				.delete(null, posId1);
	}

	@Test
	public void testDeleteAllByProductId() {

		// given
		// productId1

		// when
		inventoryInboundFacade.delete(productId1, null);

		// then
		// -

		// verify
		verify(inventoryOutboundService, times(1))
				.delete(productId1, null);
	}

	@Test
	public void testDeleteAllByProductIdAndPosId() {

		// given
		// posId1
		// productId1

		// when
		inventoryInboundFacade.delete(productId1, posId1);

		// then
		// -

		// verify
		verify(inventoryOutboundService, times(1))
				.delete(productId1, posId1);
	}

	private void prepareRequests() {

		// write request dtos
		final InventoryWriteRequestDto valid_inventoryWriteRequestDto =
				DtoUtils.createInventoryWriteRequestDto(inventoryId1, posId1, productId1, 3, InventoryQuantityTypeEnumRequestDto.AVAILABLE);
		final InventoryWriteRequestDto notvalid_inventoryWriteRequestDto =
				DtoUtils.createInventoryWriteRequestDto(inventoryId2, posId2, productId2, 3, InventoryQuantityTypeEnumRequestDto.AVAILABLE);

		// request dto
		inventoryRequestDto = DtoUtils.createInventoryRequestDto(valid_inventoryWriteRequestDto, notvalid_inventoryWriteRequestDto);

		// validation lists
		valid_inventoryWriteRequestDtoList = Stream.of(valid_inventoryWriteRequestDto).collect(Collectors.toList());
		notvalid_inventoryWriteRequestDtoList = Stream.of(notvalid_inventoryWriteRequestDto).collect(Collectors.toList());

		inventoryRequestValidationMap = new HashMap<>();
		inventoryRequestValidationMap.put(InventoryWriteTypeEnumRequestDto.VALID, valid_inventoryWriteRequestDtoList);
		inventoryRequestValidationMap.put(InventoryWriteTypeEnumRequestDto.NOT_VALID, notvalid_inventoryWriteRequestDtoList);
	}

	private void prepareResponses() {

		// write response dtos
		final InventoryWriteResponseDto ok_inventoryWriteResponseDto =
				DtoUtils.createInventoryWriteResponseDto(inventoryId1, InventoryStatusEnumResponseDto.OK, inventoryResponseDtoErrorMessage1);
		final InventoryWriteResponseDto error_inventoryWriteResponseDto =
				DtoUtils.createInventoryWriteResponseDto(inventoryId2, InventoryStatusEnumResponseDto.ERR, inventoryResponseDtoErrorMessage2);

		// service response dto
		service_inventoryResponseDto = DtoUtils.createInventoryResponseDto(ok_inventoryWriteResponseDto);

		// expected facade response dto
		expected_inventoryResponseDto = DtoUtils.createInventoryResponseDto(ok_inventoryWriteResponseDto, error_inventoryWriteResponseDto);

		// converter error response dto list
		error_inventoryWriteResponseDtoList = Stream.of(error_inventoryWriteResponseDto).collect(Collectors.toList());
	}

}
