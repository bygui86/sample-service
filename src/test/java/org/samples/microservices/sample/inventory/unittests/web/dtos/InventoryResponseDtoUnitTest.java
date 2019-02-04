package org.samples.microservices.sample.inventory.unittests.web.dtos;

import org.samples.microservices.sample.inventory.web.dtos.response.InventoryResponseDto;
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
public class InventoryResponseDtoUnitTest {

	private final InventoryStatusEnumResponseDto status = InventoryStatusEnumResponseDto.OK;

	private final String errorMsg = "error message";

	@Test
	public void testEquals_equals() {

		// given
		final InventoryWriteResponseDto inventoryWriteResponseDto1 =
				DtoUtils.createInventoryWriteResponseDto("001", status, errorMsg);
		final InventoryWriteResponseDto inventoryWriteResponseDto2 =
				DtoUtils.createInventoryWriteResponseDto("002", status, errorMsg);

		final InventoryResponseDto inventoryResponseDto1 = DtoUtils.createInventoryResponseDto(inventoryWriteResponseDto1, inventoryWriteResponseDto2);
		final InventoryResponseDto inventoryResponseDto2 = DtoUtils.createInventoryResponseDto(inventoryWriteResponseDto1, inventoryWriteResponseDto2);

		// when
		final boolean result = inventoryResponseDto1.equals(inventoryResponseDto2);

		// then
		assertThat(result).isTrue();
	}

	@Test
	public void testEquals_different() {

		// given
		final InventoryWriteResponseDto inventoryWriteResponseDto1 =
				DtoUtils.createInventoryWriteResponseDto("001", status, errorMsg);
		final InventoryWriteResponseDto inventoryWriteResponseDto2 =
				DtoUtils.createInventoryWriteResponseDto("002", status, errorMsg);
		final InventoryWriteResponseDto inventoryWriteResponseDto3 =
				DtoUtils.createInventoryWriteResponseDto("003", status, errorMsg);

		final InventoryResponseDto inventoryResponseDto1 = DtoUtils.createInventoryResponseDto(inventoryWriteResponseDto1, inventoryWriteResponseDto2);
		final InventoryResponseDto inventoryResponseDto2 = DtoUtils.createInventoryResponseDto(inventoryWriteResponseDto1, inventoryWriteResponseDto3);

		// when
		final boolean result = inventoryResponseDto1.equals(inventoryResponseDto2);

		// then
		assertThat(result).isFalse();
	}

	@Test
	public void testHashCode_equals() {

		// given
		final InventoryWriteResponseDto inventoryWriteResponseDto1 =
				DtoUtils.createInventoryWriteResponseDto("001", status, errorMsg);
		final InventoryWriteResponseDto inventoryWriteResponseDto2 =
				DtoUtils.createInventoryWriteResponseDto("001", status, errorMsg);

		final InventoryResponseDto inventoryResponseDto1 = DtoUtils.createInventoryResponseDto(inventoryWriteResponseDto1, inventoryWriteResponseDto2);
		final InventoryResponseDto inventoryResponseDto2 = DtoUtils.createInventoryResponseDto(inventoryWriteResponseDto1, inventoryWriteResponseDto2);

		// when
		final int hashCode1 = inventoryResponseDto1.hashCode();
		final int hashCode2 = inventoryResponseDto2.hashCode();

		// then
		assertThat(hashCode1)
				.isEqualTo(hashCode2);
	}

	@Test
	public void testHashCode_different() {

		// given
		final InventoryWriteResponseDto inventoryWriteResponseDto1 =
				DtoUtils.createInventoryWriteResponseDto("001", status, errorMsg);
		final InventoryWriteResponseDto inventoryWriteResponseDto2 =
				DtoUtils.createInventoryWriteResponseDto("002", status, errorMsg);
		final InventoryWriteResponseDto inventoryWriteResponseDto3 =
				DtoUtils.createInventoryWriteResponseDto("003", status, errorMsg);

		final InventoryResponseDto inventoryResponseDto1 = DtoUtils.createInventoryResponseDto(inventoryWriteResponseDto1, inventoryWriteResponseDto2);
		final InventoryResponseDto inventoryResponseDto2 = DtoUtils.createInventoryResponseDto(inventoryWriteResponseDto1, inventoryWriteResponseDto3);

		// when
		final int hashCode1 = inventoryResponseDto1.hashCode();
		final int hashCode2 = inventoryResponseDto2.hashCode();

		// then
		assertThat(hashCode1)
				.isNotEqualTo(hashCode2);
	}

}
