package org.samples.microservices.sample.inventory.web.dtos.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;


@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Getter
@ToString
public enum InventoryStatusEnumResponseDto {

	OK("OK"),
	ERR("ERR");

	String value;

	InventoryStatusEnumResponseDto(final String value) {

		this.value = value;
	}

}
