package org.samples.microservices.sample.inventory.web.facade;

import org.samples.microservices.sample.inventory.web.dtos.request.InventoryRequestDto;
import org.samples.microservices.sample.inventory.web.dtos.response.InventoryResponseDto;


public interface InventoryInboundFacade {

	InventoryResponseDto insert(final InventoryRequestDto inventoryRequestDto);

	InventoryResponseDto update(final InventoryRequestDto inventoryRequestDto);

	void delete(final String productId, final String posId);

}
