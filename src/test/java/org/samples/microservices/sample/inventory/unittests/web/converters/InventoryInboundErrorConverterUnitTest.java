package org.samples.microservices.sample.inventory.unittests.web.converters;

import org.samples.microservices.sample.inventory.web.converters.impl.InventoryInboundErrorConverterImpl;
import org.samples.microservices.sample.inventory.web.dtos.request.InventoryWriteRequestDto;
import org.samples.microservices.sample.inventory.web.dtos.response.InventoryStatusEnumResponseDto;
import org.samples.microservices.sample.inventory.web.dtos.response.InventoryWriteResponseDto;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * @author Matteo Baiguini
 * matteo@solidarchitectures.com
 * 18 Sep 2018
 */
@RunWith(MockitoJUnitRunner.class)
public class InventoryInboundErrorConverterUnitTest {

	@InjectMocks
	InventoryInboundErrorConverterImpl inventoryInboundErrorConverter;

	protected final String inventoryIdPrefix = "00";

	protected final String validationErrorMessage = "Validation error message";

	@Test
	public void testErrorRequestToErrorResponse() {

		// given
		final InventoryWriteRequestDto inventoryWriteRequestDto =
				generateInventoryWriteRequestDto(1).get(0);

		// when
		final InventoryWriteResponseDto inventoryWriteResponseDto =
				inventoryInboundErrorConverter.errorRequestToErrorResponse(inventoryWriteRequestDto);

		// then
		Assertions.assertThat(inventoryWriteResponseDto.getInventoryId())
				.contains(inventoryIdPrefix);
		assertThat(inventoryWriteResponseDto.getStatus())
				.isEqualTo(InventoryStatusEnumResponseDto.ERR);
		assertThat(inventoryWriteResponseDto.getErrorMessage())
				.isEqualTo(validationErrorMessage);
	}

	@Test
	public void testErrorRequestsToErrorResponses() {

		// given
		final List<InventoryWriteRequestDto> inventoryWriteRequestDtoList =
				generateInventoryWriteRequestDto(3);

		// when
		final List<InventoryWriteResponseDto> inventoryWriteResponseDtoList =
				inventoryInboundErrorConverter.errorRequestsToErrorResponses(inventoryWriteRequestDtoList);

		// then
		for (final InventoryWriteResponseDto inventoryWriteResponseDto : inventoryWriteResponseDtoList) {
			Assertions.assertThat(inventoryWriteResponseDto.getInventoryId())
					.contains(inventoryIdPrefix);
			assertThat(inventoryWriteResponseDto.getStatus())
					.isEqualTo(InventoryStatusEnumResponseDto.ERR);
			assertThat(inventoryWriteResponseDto.getErrorMessage())
					.isEqualTo(validationErrorMessage);
		}
	}

	protected List<InventoryWriteRequestDto> generateInventoryWriteRequestDto(final int howMany) {

		final List<InventoryWriteRequestDto> dtos = new ArrayList<>();
		for (int i = 0; i < howMany; i++) {
			dtos.add(
					InventoryWriteRequestDto.builder()
							.inventoryId(inventoryIdPrefix + i)
							.validationErrorMessage(validationErrorMessage)
							.build()
			);
		}
		return dtos;
	}

}