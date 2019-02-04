package org.samples.microservices.sample.inventory.web.dtos.request;

import org.samples.microservices.sample.inventory.web.dtos.AbstractInventoryDto;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;


@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractInventoryRequestDto extends AbstractInventoryDto {

	@NotBlank
	String productId;

	@NotBlank
	String posId;

	public AbstractInventoryRequestDto(final String inventoryId, final String productId, final String posId) {

		super(inventoryId);
		this.productId = productId;
		this.posId = posId;
	}

}
