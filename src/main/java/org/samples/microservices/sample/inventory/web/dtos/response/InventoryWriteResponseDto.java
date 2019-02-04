package org.samples.microservices.sample.inventory.web.dtos.response;

import org.samples.microservices.sample.inventory.web.dtos.AbstractInventoryDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;


@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InventoryWriteResponseDto extends AbstractInventoryDto {

	@NotNull
	InventoryStatusEnumResponseDto status;

	String errorMessage;

	@Builder
	private InventoryWriteResponseDto(final String inventoryId,
	                                  final InventoryStatusEnumResponseDto status,
	                                  final String errorMessage) {

		super(inventoryId);
		this.status = status;
		this.errorMessage = errorMessage;
	}

}
