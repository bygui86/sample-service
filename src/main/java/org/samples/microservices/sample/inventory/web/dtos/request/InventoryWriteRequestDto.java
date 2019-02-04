package org.samples.microservices.sample.inventory.web.dtos.request;

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

import javax.xml.bind.annotation.XmlTransient;


@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InventoryWriteRequestDto extends AbstractInventoryRequestDto {

	int quantity;

	InventoryQuantityTypeEnumRequestDto quantityType;

	@XmlTransient
	String validationErrorMessage;

	@Builder
	private InventoryWriteRequestDto(final String inventoryId,
	                                 final String productId,
	                                 final String posId,
	                                 final int quantity,
	                                 final InventoryQuantityTypeEnumRequestDto quantityType,
	                                 final String validationErrorMessage) {

		super(inventoryId, productId, posId);
		this.quantity = quantity;
		this.quantityType = quantityType;
		this.validationErrorMessage = validationErrorMessage;
	}

}
