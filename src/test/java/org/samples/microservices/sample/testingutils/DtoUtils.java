package org.samples.microservices.sample.testingutils;

import org.samples.microservices.sample.inventory.web.dtos.request.InventoryQuantityTypeEnumRequestDto;
import org.samples.microservices.sample.inventory.web.dtos.request.InventoryRequestDto;
import org.samples.microservices.sample.inventory.web.dtos.request.InventoryWriteRequestDto;
import org.samples.microservices.sample.inventory.web.dtos.response.InventoryResponseDto;
import org.samples.microservices.sample.inventory.web.dtos.response.InventoryStatusEnumResponseDto;
import org.samples.microservices.sample.inventory.web.dtos.response.InventoryWriteResponseDto;

import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * @author Matteo Baiguini
 * matteo@solidarchitectures.com
 * 03 Oct 2018
 */
public final class DtoUtils {

	private DtoUtils() {
		// no-op
	}

	public static InventoryWriteRequestDto createInventoryWriteRequestDto(
			final String inventoryId, final String posId, final String productId,
			final int qty, final InventoryQuantityTypeEnumRequestDto qtyType) {

		return InventoryWriteRequestDto.builder()
				.inventoryId(inventoryId)
				.posId(posId)
				.productId(productId)
				.quantity(qty)
				.quantityType(qtyType)
				.build();
	}

	public static InventoryWriteResponseDto createInventoryWriteResponseDto(
			final String inventoryId, final InventoryStatusEnumResponseDto status, final String errorMsg) {

		return InventoryWriteResponseDto.builder()
				.inventoryId(inventoryId)
				.status(status)
				.errorMessage(errorMsg)
				.build();
	}

	public static InventoryRequestDto createInventoryRequestDto(final InventoryWriteRequestDto... inventoryWriteRequestDtos) {

		return InventoryRequestDto.builder()
				.requestDtos(Stream.of(inventoryWriteRequestDtos).collect(Collectors.toList()))
				.build();
	}

	public static InventoryResponseDto createInventoryResponseDto(final InventoryWriteResponseDto... inventoryWriteResponseDtos) {

		return InventoryResponseDto.builder()
				.responses(Stream.of(inventoryWriteResponseDtos).collect(Collectors.toList()))
				.build();
	}

}
