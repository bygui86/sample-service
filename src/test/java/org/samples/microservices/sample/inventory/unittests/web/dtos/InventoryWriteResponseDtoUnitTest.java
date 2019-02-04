package org.samples.microservices.sample.inventory.unittests.web.dtos;

import org.samples.microservices.sample.inventory.web.dtos.response.InventoryStatusEnumResponseDto;
import org.samples.microservices.sample.inventory.web.dtos.response.InventoryWriteResponseDto;
import org.junit.Test;
import org.samples.microservices.sample.testingutils.DtoUtils;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * @author Matteo Baiguini
 * matteo@solidarchitectures.com
 * 03 Oct 2018
 */
public class InventoryWriteResponseDtoUnitTest {

	private final String inventoryId = "001";

	private final InventoryStatusEnumResponseDto status = InventoryStatusEnumResponseDto.OK;

	private final String errorMsg = "error message";

	@Test
	public void testEquals_equals() {

		// given
		final InventoryWriteResponseDto inventoryWriteResponseDto1 =
				DtoUtils.createInventoryWriteResponseDto(inventoryId, status, errorMsg);
		final InventoryWriteResponseDto inventoryWriteResponseDto2 =
				DtoUtils.createInventoryWriteResponseDto(inventoryId, status, errorMsg);

		// when
		final boolean result = inventoryWriteResponseDto1.equals(inventoryWriteResponseDto2);

		// then
		assertThat(result).isTrue();
	}

	@Test
	public void testEquals_different() {

		// given
		final InventoryWriteResponseDto inventoryWriteResponseDto1 =
				DtoUtils.createInventoryWriteResponseDto(inventoryId, status, errorMsg);
		final InventoryWriteResponseDto inventoryWriteResponseDto2 =
				DtoUtils.createInventoryWriteResponseDto(inventoryId, InventoryStatusEnumResponseDto.ERR, errorMsg);
		final InventoryWriteResponseDto inventoryWriteResponseDto3 =
				DtoUtils.createInventoryWriteResponseDto(inventoryId, status, "another error message");

		// when
		final boolean result1equals2 = inventoryWriteResponseDto1.equals(inventoryWriteResponseDto2);
		final boolean result1equals3 = inventoryWriteResponseDto1.equals(inventoryWriteResponseDto3);
		final boolean result2equals3 = inventoryWriteResponseDto2.equals(inventoryWriteResponseDto3);

		// then
		assertThat(result1equals2).isFalse();
		assertThat(result1equals3).isFalse();
		assertThat(result2equals3).isFalse();
	}

	@Test
	public void testHashCode_equals() {

		// given
		final InventoryWriteResponseDto inventoryWriteResponseDto1 =
				DtoUtils.createInventoryWriteResponseDto(inventoryId, status, errorMsg);
		final InventoryWriteResponseDto inventoryWriteResponseDto2 =
				DtoUtils.createInventoryWriteResponseDto(inventoryId, status, errorMsg);

		// when
		final int hashCode1 = inventoryWriteResponseDto1.hashCode();
		final int hashCode2 = inventoryWriteResponseDto2.hashCode();

		// then
		assertThat(hashCode1)
				.isEqualTo(hashCode2);
	}

	@Test
	public void testHashCode_different() {

		// given
		final InventoryWriteResponseDto inventoryWriteResponseDto1 =
				DtoUtils.createInventoryWriteResponseDto(inventoryId, status, errorMsg);
		final InventoryWriteResponseDto inventoryWriteResponseDto2 =
				DtoUtils.createInventoryWriteResponseDto(inventoryId, InventoryStatusEnumResponseDto.ERR, "another error message");

		// when
		final int hashCode1 = inventoryWriteResponseDto1.hashCode();
		final int hashCode2 = inventoryWriteResponseDto2.hashCode();

		// then
		assertThat(hashCode1)
				.isNotEqualTo(hashCode2);
	}

}
