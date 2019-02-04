package org.samples.microservices.sample.inventory.web.validators;

import org.samples.microservices.sample.inventory.web.dtos.request.InventoryRequestDto;
import org.samples.microservices.sample.inventory.web.dtos.request.InventoryWriteRequestDto;
import org.samples.microservices.sample.inventory.web.dtos.request.InventoryWriteTypeEnumRequestDto;

import java.util.List;
import java.util.Map;


public interface InventoryInboundValidator {

	/**
	 * Verify that quantities are ONLY POSITIVE
	 *
	 * @param inventoryRequestDto
	 */
	Map<InventoryWriteTypeEnumRequestDto, List<InventoryWriteRequestDto>> validateInsert(final InventoryRequestDto inventoryRequestDto);

	/**
	 * Verify that quantities are NOT EQUALS TO ZERO
	 *
	 * @param inventoryRequestDto
	 */
	Map<InventoryWriteTypeEnumRequestDto, List<InventoryWriteRequestDto>> validateUpdate(final InventoryRequestDto inventoryRequestDto);

}
