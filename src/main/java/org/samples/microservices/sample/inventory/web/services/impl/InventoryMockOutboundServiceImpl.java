package org.samples.microservices.sample.inventory.web.services.impl;

import org.samples.microservices.sample.constants.ProfileConstants;
import org.samples.microservices.sample.inventory.web.dtos.request.InventoryRequestDto;
import org.samples.microservices.sample.inventory.web.dtos.request.InventoryWriteRequestDto;
import org.samples.microservices.sample.inventory.web.dtos.response.InventoryResponseDto;
import org.samples.microservices.sample.inventory.web.dtos.response.InventoryStatusEnumResponseDto;
import org.samples.microservices.sample.inventory.web.dtos.response.InventoryWriteResponseDto;
import org.samples.microservices.sample.inventory.web.services.InventoryOutboundService;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter(value = AccessLevel.PROTECTED)
@Profile(ProfileConstants.SPRING_PROFILE_OUTBOUND_MOCK)
@Service("inventoryOutboundService")
public class InventoryMockOutboundServiceImpl implements InventoryOutboundService {

	protected InventoryResponseDto insert(final InventoryRequestDto inventoryRequestDto) {

		log.info("[MOKED] {} Inventories to INSERT...", inventoryRequestDto.getRequestDtos().size());

		return createFakeInventoryResponseDto(inventoryRequestDto);
	}

	@Override
	public InventoryResponseDto insert(final List<InventoryWriteRequestDto> inventoryWriteRequestDtos) {

		return insert(
				InventoryRequestDto.builder().requestDtos(inventoryWriteRequestDtos).build()
		);
	}

	protected InventoryResponseDto update(final InventoryRequestDto inventoryRequestDto) {

		log.info("[MOKED] {} Inventories to UPDATE...", inventoryRequestDto.getRequestDtos().size());

		return createFakeInventoryResponseDto(inventoryRequestDto);
	}

	@Override
	public InventoryResponseDto update(final List<InventoryWriteRequestDto> inventoryWriteRequestDtos) {

		return update(
				InventoryRequestDto.builder().requestDtos(inventoryWriteRequestDtos).build()
		);
	}

	@Override
	public void delete(final String productId, final String posId) {

		log.info("[MOKED] Delete all inventories for productId {} and posId{}", productId, posId);
	}

	protected InventoryResponseDto createFakeInventoryResponseDto(final InventoryRequestDto inventoryRequestDto) {

		int threshold = 0;
		final List<InventoryWriteResponseDto> writeResponseDtos = new ArrayList<>();
		for (final InventoryWriteRequestDto writeRequestDto : inventoryRequestDto.getRequestDtos()) {
			final InventoryWriteResponseDto writeResponseDto;
			if (threshold % 3 == 0) {
				writeResponseDto = InventoryWriteResponseDto.builder()
						.inventoryId(writeRequestDto.getInventoryId())
						.status(InventoryStatusEnumResponseDto.ERR)
						.errorMessage("Error during import of Inventory")
						.build();
			} else {
				writeResponseDto = InventoryWriteResponseDto.builder()
						.inventoryId(writeRequestDto.getInventoryId())
						.status(InventoryStatusEnumResponseDto.OK)
						.build();
			}
			writeResponseDtos.add(writeResponseDto);
			threshold++;
		}

		return InventoryResponseDto.builder()
				.responses(writeResponseDtos)
				.build();
	}

}
