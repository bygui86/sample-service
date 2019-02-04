package org.samples.microservices.sample.inventory.web.dtos.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;


@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Getter
@ToString
public enum InventoryQuantityTypeEnumRequestDto {

	AVAILABLE("AVAILABLE"),
	RESERVED("RESERVED"),
	QUALITY_TEST("QUALITY_TEST"),
	POS("POS"); // TODO to be defined

	String value;

	InventoryQuantityTypeEnumRequestDto(final String value) {

		this.value = value;
	}

}
