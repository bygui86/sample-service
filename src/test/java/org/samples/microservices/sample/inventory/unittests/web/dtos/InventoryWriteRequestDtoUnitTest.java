package org.samples.microservices.sample.inventory.unittests.web.dtos;

import org.samples.microservices.sample.inventory.web.dtos.request.InventoryQuantityTypeEnumRequestDto;
import org.samples.microservices.sample.inventory.web.dtos.request.InventoryWriteRequestDto;
import org.junit.Test;
import org.samples.microservices.sample.testingutils.DtoUtils;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * @author Matteo Baiguini
 * matteo@solidarchitectures.com
 * 03 Oct 2018
 */
public class InventoryWriteRequestDtoUnitTest {

	private final String inventoryId = "001";

	private final String posId = "POS-01";

	private final String productId = "PROD-001";

	private final int qty = 3;

	private final InventoryQuantityTypeEnumRequestDto qtyType = InventoryQuantityTypeEnumRequestDto.AVAILABLE;

	@Test
	public void testEquals_equals() {

		// given
		final InventoryWriteRequestDto inventoryWriteRequestDto1 =
				DtoUtils.createInventoryWriteRequestDto(inventoryId, posId, productId, qty, qtyType);
		final InventoryWriteRequestDto inventoryWriteRequestDto2 =
				DtoUtils.createInventoryWriteRequestDto(inventoryId, posId, productId, qty, qtyType);

		// when
		final boolean result = inventoryWriteRequestDto1.equals(inventoryWriteRequestDto2);

		// then
		assertThat(result).isTrue();
	}

	@Test
	public void testEquals_different() {

		// given
		final InventoryWriteRequestDto inventoryWriteRequestDto1 =
				DtoUtils.createInventoryWriteRequestDto(inventoryId, posId, productId, qty, qtyType);
		final InventoryWriteRequestDto inventoryWriteRequestDto2 =
				DtoUtils.createInventoryWriteRequestDto(inventoryId, "POS-02", productId, qty, qtyType);
		final InventoryWriteRequestDto inventoryWriteRequestDto3 =
				DtoUtils.createInventoryWriteRequestDto(inventoryId, posId, "PROD-002", qty, qtyType);

		// when
		final boolean result1equals2 = inventoryWriteRequestDto1.equals(inventoryWriteRequestDto2);
		final boolean result1equals3 = inventoryWriteRequestDto1.equals(inventoryWriteRequestDto3);
		final boolean result2equals3 = inventoryWriteRequestDto2.equals(inventoryWriteRequestDto3);

		// then
		assertThat(result1equals2).isFalse();
		assertThat(result1equals3).isFalse();
		assertThat(result2equals3).isFalse();
	}

	@Test
	public void testHashCode_equals() {

		// given
		final InventoryWriteRequestDto inventoryWriteRequestDto1 =
				DtoUtils.createInventoryWriteRequestDto("001", "POS-01", "PROD-001", qty, qtyType);
		final InventoryWriteRequestDto inventoryWriteRequestDto2 =
				DtoUtils.createInventoryWriteRequestDto("001", "POS-01", "PROD-001", qty, qtyType);

		// when
		final int hashCode1 = inventoryWriteRequestDto1.hashCode();
		final int hashCode2 = inventoryWriteRequestDto2.hashCode();

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

		// when
		final int hashCode1 = inventoryWriteRequestDto1.hashCode();
		final int hashCode2 = inventoryWriteRequestDto2.hashCode();

		// then
		assertThat(hashCode1)
				.isNotEqualTo(hashCode2);
	}

}
