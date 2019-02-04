package org.samples.microservices.sample.inventory.web.services;

import org.samples.microservices.sample.inventory.web.dtos.request.InventoryWriteRequestDto;
import org.samples.microservices.sample.inventory.web.dtos.response.InventoryResponseDto;

import java.util.List;


public interface InventoryOutboundService {

	InventoryResponseDto insert(final List<InventoryWriteRequestDto> inventoryWriteRequestDtos);

	InventoryResponseDto update(final List<InventoryWriteRequestDto> inventoryWriteRequestDtos);

	void delete(final String productId, final String posId);

}
