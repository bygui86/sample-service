package org.samples.microservices.sample.inventory.web.validators.impl;

import org.samples.microservices.sample.inventory.web.dtos.request.InventoryRequestDto;
import org.samples.microservices.sample.inventory.web.dtos.request.InventoryWriteRequestDto;
import org.samples.microservices.sample.inventory.web.dtos.request.InventoryWriteTypeEnumRequestDto;
import org.samples.microservices.sample.inventory.web.validators.InventoryInboundValidator;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@Service("inventoryInboundValidator")
public class InventoryInboundValidatorImpl implements InventoryInboundValidator {

	private static final String VALIDATION_ERROR_SEPARATOR = ", ";

	@Override
	public Map<InventoryWriteTypeEnumRequestDto, List<InventoryWriteRequestDto>> validateInsert(final InventoryRequestDto inventoryRequestDto) {

		return validate(ValidationTypeEnum.INSERT, inventoryRequestDto);
	}

	@Override
	public Map<InventoryWriteTypeEnumRequestDto, List<InventoryWriteRequestDto>> validateUpdate(final InventoryRequestDto inventoryRequestDto) {

		return validate(ValidationTypeEnum.UPDATE, inventoryRequestDto);
	}

	protected Map<InventoryWriteTypeEnumRequestDto, List<InventoryWriteRequestDto>> validate(
			final ValidationTypeEnum validationType, final InventoryRequestDto inventoryRequestDto) {

		final List<InventoryWriteRequestDto> validDtos = new ArrayList<>();
		final List<InventoryWriteRequestDto> notValidDtos = new ArrayList<>();

		inventoryRequestDto.getRequestDtos()
				.forEach(writeDto -> {

					String validationResult = "Validation type not valid";
					switch (validationType) {
						case INSERT:
							validationResult = validateInsert(writeDto);
							break;
						case UPDATE:
							validationResult = validateUpdate(writeDto);
							break;
					}

					if (StringUtils.isEmpty(validationResult)) {
						validDtos.add(writeDto);
					} else {
						writeDto.setValidationErrorMessage(validationResult);
						notValidDtos.add(writeDto);
					}
				});

		return createReturnMap(validDtos, notValidDtos);
	}

	protected String validateInsert(final InventoryWriteRequestDto inventoryWriteRequestDto) {

		log.debug("Validating INSERT of InventoryWriteRequestDto {}", inventoryWriteRequestDto);

		final StringBuilder stringBuilder = validateCommons(inventoryWriteRequestDto);

		return stringBuilder.toString();
	}

	protected String validateUpdate(final InventoryWriteRequestDto inventoryWriteRequestDto) {

		log.debug("Validating UPDATE of InventoryWriteRequestDto {}", inventoryWriteRequestDto);

		final StringBuilder stringBuilder = validateCommons(inventoryWriteRequestDto);

		if (inventoryWriteRequestDto.getQuantity() == 0) {
			stringBuilder.append(stringBuilder.length() > 0 ? VALIDATION_ERROR_SEPARATOR : Strings.EMPTY);
			stringBuilder.append("Quantity must be NOT EQUAL TO ZERO for an UPDATE");
		}

		return stringBuilder.toString();
	}

	protected StringBuilder validateCommons(final InventoryWriteRequestDto inventoryWriteRequestDto) {

		final StringBuilder stringBuilder = new StringBuilder();

		if (StringUtils.isEmpty(inventoryWriteRequestDto.getInventoryId())) {
			stringBuilder.append("InventoryID must NOT BE NULL (item with podId[");
			stringBuilder.append(inventoryWriteRequestDto.getPosId());
			stringBuilder.append("] and productId[");
			stringBuilder.append(inventoryWriteRequestDto.getProductId());
			stringBuilder.append("])");
		}

		if (StringUtils.isEmpty(inventoryWriteRequestDto.getPosId())) {
			stringBuilder.append(stringBuilder.length() > 0 ? VALIDATION_ERROR_SEPARATOR : Strings.EMPTY);
			stringBuilder.append("PointOfServiceID must NOT BE NULL");
		}

		if (StringUtils.isEmpty(inventoryWriteRequestDto.getProductId())) {
			stringBuilder.append(stringBuilder.length() > 0 ? VALIDATION_ERROR_SEPARATOR : Strings.EMPTY);
			stringBuilder.append("ProductID must NOT BE NULL");
		}

		if (inventoryWriteRequestDto.getQuantityType() == null) {
			stringBuilder.append(stringBuilder.length() > 0 ? VALIDATION_ERROR_SEPARATOR : Strings.EMPTY);
			stringBuilder.append("QuantityType must NOT BE NULL");
		}

		return stringBuilder;
	}

	protected Map<InventoryWriteTypeEnumRequestDto, List<InventoryWriteRequestDto>> createReturnMap(
			final List<InventoryWriteRequestDto> validDtos, final List<InventoryWriteRequestDto> notValidDtos) {

		final Map<InventoryWriteTypeEnumRequestDto, List<InventoryWriteRequestDto>> map = new HashMap<>();
		map.put(InventoryWriteTypeEnumRequestDto.VALID, validDtos);
		map.put(InventoryWriteTypeEnumRequestDto.NOT_VALID, notValidDtos);
		return map;
	}

	enum ValidationTypeEnum {
		INSERT,
		UPDATE
	}

}
