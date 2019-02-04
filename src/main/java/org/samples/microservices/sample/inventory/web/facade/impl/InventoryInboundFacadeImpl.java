package org.samples.microservices.sample.inventory.web.facade.impl;

import org.samples.microservices.sample.constants.ProfileConstants;
import org.samples.microservices.sample.inventory.web.converters.InventoryInboundErrorConverter;
import org.samples.microservices.sample.inventory.web.dtos.request.InventoryRequestDto;
import org.samples.microservices.sample.inventory.web.dtos.request.InventoryWriteRequestDto;
import org.samples.microservices.sample.inventory.web.dtos.request.InventoryWriteTypeEnumRequestDto;
import org.samples.microservices.sample.inventory.web.dtos.response.InventoryResponseDto;
import org.samples.microservices.sample.inventory.web.dtos.response.InventoryWriteResponseDto;
import org.samples.microservices.sample.inventory.web.facade.InventoryInboundFacade;
import org.samples.microservices.sample.inventory.web.services.InventoryOutboundService;
import org.samples.microservices.sample.inventory.web.validators.InventoryInboundValidator;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;


@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter(value = AccessLevel.PROTECTED)
@Profile(ProfileConstants.SPRING_PROFILE_INBOUND_REST)
@Component("inventoryInboundFacade")
public class InventoryInboundFacadeImpl implements InventoryInboundFacade {

	@Autowired
	@Qualifier("inventoryInboundValidator")
	InventoryInboundValidator inventoryInboundValidator;

	@Autowired
	@Qualifier("inventoryOutboundService")
	InventoryOutboundService inventoryOutboundService;

	@Autowired
	@Qualifier("inventoryInboundErrorConverter")
	InventoryInboundErrorConverter inventoryInboundErrorConverter;

	@Override
	public InventoryResponseDto insert(final InventoryRequestDto inventoryRequestDto) {

		log.debug("{} Inventories to INSERT...", inventoryRequestDto.getRequestDtos().size());

		final Map<InventoryWriteTypeEnumRequestDto, List<InventoryWriteRequestDto>> validationMap =
				getInventoryInboundValidator().validateInsert(inventoryRequestDto);

		InventoryResponseDto insertResponse = InventoryResponseDto.builder().build();
		final List<InventoryWriteRequestDto> validRequestDtos = validationMap.get(InventoryWriteTypeEnumRequestDto.VALID);
		if (!validRequestDtos.isEmpty()) {
			insertResponse = getInventoryOutboundService().insert(validRequestDtos);
		}

		mergeResponseDtos(insertResponse, convertNotValidRequestDtos(validationMap));
		return insertResponse;
	}

	@Override
	public InventoryResponseDto update(final InventoryRequestDto inventoryRequestDto) {

		log.debug("{} Inventories to UPDATE...", inventoryRequestDto.getRequestDtos().size());

		final Map<InventoryWriteTypeEnumRequestDto, List<InventoryWriteRequestDto>> validationMap =
				getInventoryInboundValidator().validateUpdate(inventoryRequestDto);

		InventoryResponseDto updateResponse = InventoryResponseDto.builder().build();
		final List<InventoryWriteRequestDto> validRequestDtos = validationMap.get(InventoryWriteTypeEnumRequestDto.VALID);
		if (!validRequestDtos.isEmpty()) {
			updateResponse = getInventoryOutboundService().update(validRequestDtos);
		}

		mergeResponseDtos(updateResponse, convertNotValidRequestDtos(validationMap));
		return updateResponse;
	}

	@Override
	public void delete(final String productId, final String posId) {

		log.debug("Delete all inventories for productId {} and posId {}", productId, posId);

		getInventoryOutboundService().delete(productId, posId);
	}

	protected List<InventoryWriteResponseDto> convertNotValidRequestDtos(
			final Map<InventoryWriteTypeEnumRequestDto, List<InventoryWriteRequestDto>> requestDtoValidationMap) {

		final List<InventoryWriteRequestDto> notValidRequestDtos = requestDtoValidationMap.get(InventoryWriteTypeEnumRequestDto.NOT_VALID);
		if (!notValidRequestDtos.isEmpty()) {
			return getInventoryInboundErrorConverter().errorRequestsToErrorResponses(notValidRequestDtos);
		}
		return Collections.emptyList();
	}

	protected void mergeResponseDtos(final InventoryResponseDto inventoryResponseDto, final List<InventoryWriteResponseDto> notValidResponseDtos) {

		if (inventoryResponseDto.getResponses() == null
				|| inventoryResponseDto.getResponses().isEmpty()) {
			inventoryResponseDto.setResponses(notValidResponseDtos);
		} else {
			inventoryResponseDto.getResponses().addAll(notValidResponseDtos);
		}
	}

}
