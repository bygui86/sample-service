package org.samples.microservices.sample.inventory.unittests.web.dtos;

import org.samples.microservices.sample.inventory.web.dtos.request.InventoryQuantityTypeEnumRequestDto;
import org.samples.microservices.sample.inventory.web.dtos.request.InventoryRequestDto;
import org.samples.microservices.sample.inventory.web.dtos.request.InventoryWriteRequestDto;
import org.junit.Test;
import org.samples.microservices.sample.testingutils.DtoUtils;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * @author Matteo Baiguini
 * matteo@solidarchitectures.com
 * 03 Oct 2018
 */
public class InventoryRequestDtoUnitTest {

	private final int qty = 3;

	private final InventoryQuantityTypeEnumRequestDto qtyType = InventoryQuantityTypeEnumRequestDto.AVAILABLE;

	@Test
	public void testEquals_equals() {

		// given
		final InventoryWriteRequestDto inventoryWriteRequestDto1 =
				DtoUtils.createInventoryWriteRequestDto("001", "POS-01", "PROD-001", qty, qtyType);
		final InventoryWriteRequestDto inventoryWriteRequestDto2 =
				DtoUtils.createInventoryWriteRequestDto("002", "POS-02", "PROD-002", qty, qtyType);

		final InventoryRequestDto inventoryRequestDto1 = DtoUtils.createInventoryRequestDto(inventoryWriteRequestDto1, inventoryWriteRequestDto2);
		final InventoryRequestDto inventoryRequestDto2 = DtoUtils.createInventoryRequestDto(inventoryWriteRequestDto1, inventoryWriteRequestDto2);

		// when
		final boolean result = inventoryRequestDto1.equals(inventoryRequestDto2);

		// then
		assertThat(result).isTrue();
	}

	@Test
	public void testEquals_different() {

		// given
		final InventoryWriteRequestDto inventoryWriteRequestDto1 =
				DtoUtils.createInventoryWriteRequestDto("001", "POS-01", "PROD-001", qty, qtyType);
		final InventoryWriteRequestDto inventoryWriteRequestDto2 =
				DtoUtils.createInventoryWriteRequestDto("002", "POS-02", "PROD-002", qty, qtyType);
		final InventoryWriteRequestDto inventoryWriteRequestDto3 =
				DtoUtils.createInventoryWriteRequestDto("003", "POS-03", "PROD-003", qty, qtyType);

		final InventoryRequestDto inventoryRequestDto1 = DtoUtils.createInventoryRequestDto(inventoryWriteRequestDto1, inventoryWriteRequestDto2);
		final InventoryRequestDto inventoryRequestDto2 = DtoUtils.createInventoryRequestDto(inventoryWriteRequestDto1, inventoryWriteRequestDto3);

		// when
		final boolean result = inventoryRequestDto1.equals(inventoryRequestDto2);

		// then
		assertThat(result).isFalse();
	}

	@Test
	public void testHashCode_equals() {

		// given
		final InventoryWriteRequestDto inventoryWriteRequestDto1 =
				DtoUtils.createInventoryWriteRequestDto("001", "POS-01", "PROD-001", qty, qtyType);
		final InventoryWriteRequestDto inventoryWriteRequestDto2 =
				DtoUtils.createInventoryWriteRequestDto("001", "POS-01", "PROD-001", qty, qtyType);

		final InventoryRequestDto inventoryRequestDto1 = DtoUtils.createInventoryRequestDto(inventoryWriteRequestDto1, inventoryWriteRequestDto2);
		final InventoryRequestDto inventoryRequestDto2 = DtoUtils.createInventoryRequestDto(inventoryWriteRequestDto1, inventoryWriteRequestDto2);

		// when
		final int hashCode1 = inventoryRequestDto1.hashCode();
		final int hashCode2 = inventoryRequestDto2.hashCode();

		// then
		assertThat(hashCode1)
				.isEqualTo(hashCode2);
	}

	@Test
	public void testHashCode_different() {

		// given
		final InventoryWriteRequestDto inventoryWriteRequestDto1 =
				DtoUtils.createInventoryWriteRequestDto("001", "POS-01", "PROD-001", qty, qtyType);
		final InventoryWriteRequestDto inventoryWriteRequestDto2 =
				DtoUtils.createInventoryWriteRequestDto("002", "POS-02", "PROD-002", qty, qtyType);
		final InventoryWriteRequestDto inventoryWriteRequestDto3 =
				DtoUtils.createInventoryWriteRequestDto("003", "POS-03", "PROD-003", qty, qtyType);

		final InventoryRequestDto inventoryRequestDto1 = DtoUtils.createInventoryRequestDto(inventoryWriteRequestDto1, inventoryWriteRequestDto2);
		final InventoryRequestDto inventoryRequestDto2 = DtoUtils.createInventoryRequestDto(inventoryWriteRequestDto1, inventoryWriteRequestDto3);

		// when
		final int hashCode1 = inventoryRequestDto1.hashCode();
		final int hashCode2 = inventoryRequestDto2.hashCode();

		// then
		assertThat(hashCode1)
				.isNotEqualTo(hashCode2);
	}

}
