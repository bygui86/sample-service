package org.samples.microservices.sample.inventory.web.dtos.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;


@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Getter
@ToString
public enum InventoryWriteTypeEnumRequestDto {

	VALID("VALID"),
	NOT_VALID("NOT_VALID");

	String value;

	InventoryWriteTypeEnumRequestDto(final String value) {

		this.value = value;
	}

}
