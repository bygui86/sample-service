package org.samples.microservices.sample.inventory.web.converters;

import org.samples.microservices.sample.inventory.web.dtos.request.InventoryWriteRequestDto;
import org.samples.microservices.sample.inventory.web.dtos.response.InventoryWriteResponseDto;

import java.util.List;


public interface InventoryInboundErrorConverter {

	InventoryWriteResponseDto errorRequestToErrorResponse(final InventoryWriteRequestDto inventoryWriteRequestDto);

	List<InventoryWriteResponseDto> errorRequestsToErrorResponses(final List<InventoryWriteRequestDto> inventoryWriteRequestDtos);

}
