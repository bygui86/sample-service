package org.samples.microservices.sample.inventory.web.converters.impl;

import org.samples.microservices.sample.inventory.web.converters.InventoryInboundErrorConverter;
import org.samples.microservices.sample.inventory.web.dtos.request.InventoryWriteRequestDto;
import org.samples.microservices.sample.inventory.web.dtos.response.InventoryStatusEnumResponseDto;
import org.samples.microservices.sample.inventory.web.dtos.response.InventoryWriteResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Component("inventoryInboundErrorConverter")
public class InventoryInboundErrorConverterImpl implements InventoryInboundErrorConverter {

	@Override
	public InventoryWriteResponseDto errorRequestToErrorResponse(final InventoryWriteRequestDto inventoryWriteRequestDto) {

		return InventoryWriteResponseDto.builder()
				.inventoryId(inventoryWriteRequestDto.getInventoryId())
				.status(InventoryStatusEnumResponseDto.ERR)
				.errorMessage(inventoryWriteRequestDto.getValidationErrorMessage())
				.build();
	}

	@Override
	public List<InventoryWriteResponseDto> errorRequestsToErrorResponses(final List<InventoryWriteRequestDto> inventoryWriteRequestDtos) {

		return inventoryWriteRequestDtos.stream()
				.map(this::errorRequestToErrorResponse)
				.collect(Collectors.toList());
	}

}
