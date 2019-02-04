package org.samples.microservices.sample.inventory.unittests.web.validators;

import org.samples.microservices.sample.inventory.web.dtos.request.InventoryQuantityTypeEnumRequestDto;
import org.samples.microservices.sample.inventory.web.dtos.request.InventoryRequestDto;
import org.samples.microservices.sample.inventory.web.dtos.request.InventoryWriteRequestDto;
import org.samples.microservices.sample.inventory.web.dtos.request.InventoryWriteTypeEnumRequestDto;
import org.samples.microservices.sample.inventory.web.validators.impl.InventoryInboundValidatorImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * @author Matteo Baiguini
 * matteo@solidarchitectures.com
 * 18 Sep 2018
 */
@RunWith(MockitoJUnitRunner.class)
public class InventoryInboundValidatorUnitTest {

	@InjectMocks
	protected InventoryInboundValidatorImpl inventoryInboundValidator;

	protected final String inventoryId = "001";

	protected final String posId = "POS-01";

	protected final String productId = "PROD-01";

	@Test
	public void testValidateInsert_valid() {

		// given
		final InventoryWriteRequestDto inventoryWriteRequestDto =
				getInventoryWriteRequestDto(inventoryId, posId, productId, 3, InventoryQuantityTypeEnumRequestDto.AVAILABLE);
		final List<InventoryWriteRequestDto> inventoryWriteRequestDtoList = new ArrayList<>(1);
		inventoryWriteRequestDtoList.add(inventoryWriteRequestDto);
		final InventoryRequestDto inventoryRequestDto =
				InventoryRequestDto.builder().requestDtos(inventoryWriteRequestDtoList).build();

		// when
		final Map<InventoryWriteTypeEnumRequestDto, List<InventoryWriteRequestDto>> validationMap =
				inventoryInboundValidator.validateInsert(inventoryRequestDto);

		// then
		assertThat(validationMap.size())
				.isEqualTo(2);
		assertThat(validationMap.get(InventoryWriteTypeEnumRequestDto.VALID).size())
				.isEqualTo(1);
		assertThat(validationMap.get(InventoryWriteTypeEnumRequestDto.VALID).get(0).getValidationErrorMessage())
				.isNullOrEmpty();
		assertThat(validationMap.get(InventoryWriteTypeEnumRequestDto.NOT_VALID).size())
				.isEqualTo(0);
	}

	@Test
	public void testValidateInsert_validNegativeQantity() {

		// given
		final InventoryWriteRequestDto inventoryWriteRequestDto =
				getInventoryWriteRequestDto(inventoryId, posId, productId, -5, InventoryQuantityTypeEnumRequestDto.AVAILABLE);
		final List<InventoryWriteRequestDto> inventoryWriteRequestDtoList = new ArrayList<>(1);
		inventoryWriteRequestDtoList.add(inventoryWriteRequestDto);
		final InventoryRequestDto inventoryRequestDto =
				InventoryRequestDto.builder().requestDtos(inventoryWriteRequestDtoList).build();

		// when
		final Map<InventoryWriteTypeEnumRequestDto, List<InventoryWriteRequestDto>> validationMap =
				inventoryInboundValidator.validateInsert(inventoryRequestDto);

		// then
		assertThat(validationMap.size())
				.isEqualTo(2);
		assertThat(validationMap.get(InventoryWriteTypeEnumRequestDto.VALID).size())
				.isEqualTo(1);
		assertThat(validationMap.get(InventoryWriteTypeEnumRequestDto.VALID).get(0).getValidationErrorMessage())
				.isNullOrEmpty();
		assertThat(validationMap.get(InventoryWriteTypeEnumRequestDto.NOT_VALID).size())
				.isEqualTo(0);
	}

	@Test
	public void testValidateInsert_notValidInventoryId() {

		// given
		final InventoryWriteRequestDto inventoryWriteRequestDto =
				getInventoryWriteRequestDto(null, posId, productId, 1, InventoryQuantityTypeEnumRequestDto.AVAILABLE);
		final List<InventoryWriteRequestDto> inventoryWriteRequestDtoList = new ArrayList<>(1);
		inventoryWriteRequestDtoList.add(inventoryWriteRequestDto);
		final InventoryRequestDto inventoryRequestDto =
				InventoryRequestDto.builder().requestDtos(inventoryWriteRequestDtoList).build();

		// when
		final Map<InventoryWriteTypeEnumRequestDto, List<InventoryWriteRequestDto>> validationMap =
				inventoryInboundValidator.validateInsert(inventoryRequestDto);

		// then
		assertThat(validationMap.size())
				.isEqualTo(2);
		assertThat(validationMap.get(InventoryWriteTypeEnumRequestDto.VALID).size())
				.isEqualTo(0);
		assertThat(validationMap.get(InventoryWriteTypeEnumRequestDto.NOT_VALID).size())
				.isEqualTo(1);
		assertThat(validationMap.get(InventoryWriteTypeEnumRequestDto.NOT_VALID).get(0).getValidationErrorMessage())
				.contains("InventoryID must NOT BE NULL");
	}

	@Test
	public void testValidateInsert_notValidMultipleFields() {

		// given
		final InventoryWriteRequestDto inventoryWriteRequestDto =
				getInventoryWriteRequestDto(inventoryId, null, null, -1, InventoryQuantityTypeEnumRequestDto.AVAILABLE);
		final List<InventoryWriteRequestDto> inventoryWriteRequestDtoList = new ArrayList<>(1);
		inventoryWriteRequestDtoList.add(inventoryWriteRequestDto);
		final InventoryRequestDto inventoryRequestDto =
				InventoryRequestDto.builder().requestDtos(inventoryWriteRequestDtoList).build();

		// when
		final Map<InventoryWriteTypeEnumRequestDto, List<InventoryWriteRequestDto>> validationMap =
				inventoryInboundValidator.validateInsert(inventoryRequestDto);

		// then
		assertThat(validationMap.size())
				.isEqualTo(2);
		assertThat(validationMap.get(InventoryWriteTypeEnumRequestDto.VALID).size())
				.isEqualTo(0);
		assertThat(validationMap.get(InventoryWriteTypeEnumRequestDto.NOT_VALID).size())
				.isEqualTo(1);
		assertThat(validationMap.get(InventoryWriteTypeEnumRequestDto.NOT_VALID).get(0).getValidationErrorMessage())
				.contains("PointOfServiceID must NOT BE NULL")
				.contains("ProductID must NOT BE NULL");
	}

	@Test
	public void testValidateInsert_notValidQuantityType() {

		// given
		final InventoryWriteRequestDto inventoryWriteRequestDto =
				getInventoryWriteRequestDto(inventoryId, posId, productId, 3, null);
		final List<InventoryWriteRequestDto> inventoryWriteRequestDtoList = new ArrayList<>(1);
		inventoryWriteRequestDtoList.add(inventoryWriteRequestDto);
		final InventoryRequestDto inventoryRequestDto =
				InventoryRequestDto.builder().requestDtos(inventoryWriteRequestDtoList).build();

		// when
		final Map<InventoryWriteTypeEnumRequestDto, List<InventoryWriteRequestDto>> validationMap =
				inventoryInboundValidator.validateInsert(inventoryRequestDto);

		// then
		assertThat(validationMap.size())
				.isEqualTo(2);
		assertThat(validationMap.get(InventoryWriteTypeEnumRequestDto.VALID).size())
				.isEqualTo(0);
		assertThat(validationMap.get(InventoryWriteTypeEnumRequestDto.NOT_VALID).size())
				.isEqualTo(1);
		assertThat(validationMap.get(InventoryWriteTypeEnumRequestDto.NOT_VALID).get(0).getValidationErrorMessage())
				.isEqualTo("QuantityType must NOT BE NULL");
	}

	@Test
	public void testValidateUpdate_valid() {

		// given
		final InventoryWriteRequestDto inventoryWriteRequestDto =
				getInventoryWriteRequestDto(inventoryId, posId, productId, 3, InventoryQuantityTypeEnumRequestDto.AVAILABLE);
		final List<InventoryWriteRequestDto> inventoryWriteRequestDtoList = new ArrayList<>(1);
		inventoryWriteRequestDtoList.add(inventoryWriteRequestDto);
		final InventoryRequestDto inventoryRequestDto =
				InventoryRequestDto.builder().requestDtos(inventoryWriteRequestDtoList).build();

		// when
		final Map<InventoryWriteTypeEnumRequestDto, List<InventoryWriteRequestDto>> validationMap =
				inventoryInboundValidator.validateUpdate(inventoryRequestDto);

		// then
		assertThat(validationMap.size())
				.isEqualTo(2);
		assertThat(validationMap.get(InventoryWriteTypeEnumRequestDto.VALID).size())
				.isEqualTo(1);
		assertThat(validationMap.get(InventoryWriteTypeEnumRequestDto.VALID).get(0).getValidationErrorMessage())
				.isNullOrEmpty();
		assertThat(validationMap.get(InventoryWriteTypeEnumRequestDto.NOT_VALID).size())
				.isEqualTo(0);
	}

	@Test
	public void testValidateUpdate_notValidQuantity() {

		// given
		final InventoryWriteRequestDto inventoryWriteRequestDto =
				getInventoryWriteRequestDto(inventoryId, posId, productId, 0, InventoryQuantityTypeEnumRequestDto.AVAILABLE);
		final List<InventoryWriteRequestDto> inventoryWriteRequestDtoList = new ArrayList<>(1);
		inventoryWriteRequestDtoList.add(inventoryWriteRequestDto);
		final InventoryRequestDto inventoryRequestDto =
				InventoryRequestDto.builder().requestDtos(inventoryWriteRequestDtoList).build();

		// when
		final Map<InventoryWriteTypeEnumRequestDto, List<InventoryWriteRequestDto>> validationMap =
				inventoryInboundValidator.validateUpdate(inventoryRequestDto);

		// then
		assertThat(validationMap.size())
				.isEqualTo(2);
		assertThat(validationMap.get(InventoryWriteTypeEnumRequestDto.VALID).size())
				.isEqualTo(0);
		assertThat(validationMap.get(InventoryWriteTypeEnumRequestDto.NOT_VALID).size())
				.isEqualTo(1);
		assertThat(validationMap.get(InventoryWriteTypeEnumRequestDto.NOT_VALID).get(0).getValidationErrorMessage())
				.isEqualTo("Quantity must be NOT EQUAL TO ZERO for an UPDATE");
	}

	@Test
	public void testValidateUpdate_notValidPosId() {

		// given
		final InventoryWriteRequestDto inventoryWriteRequestDto =
				getInventoryWriteRequestDto(inventoryId, null, productId, 1, InventoryQuantityTypeEnumRequestDto.AVAILABLE);
		final List<InventoryWriteRequestDto> inventoryWriteRequestDtoList = new ArrayList<>(1);
		inventoryWriteRequestDtoList.add(inventoryWriteRequestDto);
		final InventoryRequestDto inventoryRequestDto =
				InventoryRequestDto.builder().requestDtos(inventoryWriteRequestDtoList).build();

		// when
		final Map<InventoryWriteTypeEnumRequestDto, List<InventoryWriteRequestDto>> validationMap =
				inventoryInboundValidator.validateUpdate(inventoryRequestDto);

		// then
		assertThat(validationMap.size())
				.isEqualTo(2);
		assertThat(validationMap.get(InventoryWriteTypeEnumRequestDto.VALID).size())
				.isEqualTo(0);
		assertThat(validationMap.get(InventoryWriteTypeEnumRequestDto.NOT_VALID).size())
				.isEqualTo(1);
		assertThat(validationMap.get(InventoryWriteTypeEnumRequestDto.NOT_VALID).get(0).getValidationErrorMessage())
				.isEqualTo("PointOfServiceID must NOT BE NULL");
	}

	@Test
	public void testValidateUpdate_notValidProductId() {

		// given
		final InventoryWriteRequestDto inventoryWriteRequestDto =
				getInventoryWriteRequestDto(inventoryId, posId, null, 1, InventoryQuantityTypeEnumRequestDto.AVAILABLE);
		final List<InventoryWriteRequestDto> inventoryWriteRequestDtoList = new ArrayList<>(1);
		inventoryWriteRequestDtoList.add(inventoryWriteRequestDto);
		final InventoryRequestDto inventoryRequestDto =
				InventoryRequestDto.builder().requestDtos(inventoryWriteRequestDtoList).build();

		// when
		final Map<InventoryWriteTypeEnumRequestDto, List<InventoryWriteRequestDto>> validationMap =
				inventoryInboundValidator.validateUpdate(inventoryRequestDto);

		// then
		assertThat(validationMap.size())
				.isEqualTo(2);
		assertThat(validationMap.get(InventoryWriteTypeEnumRequestDto.VALID).size())
				.isEqualTo(0);
		assertThat(validationMap.get(InventoryWriteTypeEnumRequestDto.NOT_VALID).size())
				.isEqualTo(1);
		assertThat(validationMap.get(InventoryWriteTypeEnumRequestDto.NOT_VALID).get(0).getValidationErrorMessage())
				.isEqualTo("ProductID must NOT BE NULL");
	}

	@Test
	public void testValidateUpdate_notValidMultipleFields() {

		// given
		final InventoryWriteRequestDto inventoryWriteRequestDto =
				getInventoryWriteRequestDto(inventoryId, null, null, 1, InventoryQuantityTypeEnumRequestDto.AVAILABLE);
		final List<InventoryWriteRequestDto> inventoryWriteRequestDtoList = new ArrayList<>(1);
		inventoryWriteRequestDtoList.add(inventoryWriteRequestDto);
		final InventoryRequestDto inventoryRequestDto =
				InventoryRequestDto.builder().requestDtos(inventoryWriteRequestDtoList).build();

		// when
		final Map<InventoryWriteTypeEnumRequestDto, List<InventoryWriteRequestDto>> validationMap =
				inventoryInboundValidator.validateUpdate(inventoryRequestDto);

		// then
		assertThat(validationMap.size())
				.isEqualTo(2);
		assertThat(validationMap.get(InventoryWriteTypeEnumRequestDto.VALID).size())
				.isEqualTo(0);
		assertThat(validationMap.get(InventoryWriteTypeEnumRequestDto.NOT_VALID).size())
				.isEqualTo(1);
		assertThat(validationMap.get(InventoryWriteTypeEnumRequestDto.NOT_VALID).get(0).getValidationErrorMessage())
				.contains("PointOfServiceID must NOT BE NULL")
				.contains("ProductID must NOT BE NULL");
	}

	protected InventoryWriteRequestDto getInventoryWriteRequestDto(
			final String inventoryId, final String posId, final String productId,
			final int quantity, final InventoryQuantityTypeEnumRequestDto quantityType) {

		return InventoryWriteRequestDto.builder()
				.inventoryId(inventoryId)
				.posId(posId)
				.productId(productId)
				.quantity(quantity)
				.quantityType(quantityType)
				.build();
	}

}
