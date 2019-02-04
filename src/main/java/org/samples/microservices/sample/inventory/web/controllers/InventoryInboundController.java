package org.samples.microservices.sample.inventory.web.controllers;

import org.samples.microservices.sample.constants.ProfileConstants;
import org.samples.microservices.sample.inventory.web.dtos.request.InventoryRequestDto;
import org.samples.microservices.sample.inventory.web.dtos.response.InventoryResponseDto;
import org.samples.microservices.sample.inventory.web.facade.InventoryInboundFacade;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;


@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter(value = AccessLevel.PROTECTED)
@Profile(ProfileConstants.SPRING_PROFILE_INBOUND_REST)
@RestController("inventoryInboundController")
@RequestMapping("/inventories")
public class InventoryInboundController {

	@Autowired
	@Qualifier("inventoryInboundFacade")
	InventoryInboundFacade inventoryInboundFacade;

	@PostMapping(
			consumes = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE},
			produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE}
	)
	@ResponseStatus(code = HttpStatus.CREATED)
	public InventoryResponseDto insert(
			@RequestBody @Valid final InventoryRequestDto inventoryRequestDto) {

		log.info("INSERT request: {}", inventoryRequestDto);

		final InventoryResponseDto inventoryResponseDto = getInventoryInboundFacade().insert(inventoryRequestDto);

		log.debug("INSERT response: {}", inventoryResponseDto);

		return inventoryResponseDto;
	}

	@PutMapping(
			consumes = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE},
			produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE}
	)
	public InventoryResponseDto update(
			@RequestBody @Valid final InventoryRequestDto inventoryRequestDto) {

		log.info("UPDATE request: {}", inventoryRequestDto);

		final InventoryResponseDto inventoryResponseDto = getInventoryInboundFacade().update(inventoryRequestDto);

		log.debug("UPDATE response: {}", inventoryResponseDto);

		return inventoryResponseDto;
	}

	@DeleteMapping
	public void deleteAll(
			@RequestParam(required = false) final String productId,
			@RequestParam(required = false) final String posId) {

		log.warn("DELETE ALL INVENTORIES for productId {} and posId {}", productId, posId);

		getInventoryInboundFacade().delete(productId, posId);
	}

}
