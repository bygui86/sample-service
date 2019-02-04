package org.samples.microservices.sample.inventory.commons;

import org.samples.microservices.sample.inventory.web.dtos.request.InventoryRequestDto;
import org.samples.microservices.sample.inventory.web.dtos.response.InventoryStatusEnumResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;


/**
 * @author Matteo Baiguini
 * matteo@solidarchitectures.com
 * 01 Nov 2018
 */
public class AbstractInventoryInboundControllerTest {

	@Autowired
	protected MockMvc mockMvc;

	/* REQUESTS */

	protected final String inventoryId1 = "001";

	protected final String inventoryId2 = "002";

	protected final String posId1 = "POS-01";

	protected final String posId2 = "POS-02";

	protected final String productId1 = "PROD-01";

	protected final String productId2 = null;

	protected InventoryRequestDto inventoryRequestDto;

	/* RESPONSES */

	protected final InventoryStatusEnumResponseDto inventoryResponseStatus1 = InventoryStatusEnumResponseDto.OK;

	protected final InventoryStatusEnumResponseDto inventoryResponseStatus2 = InventoryStatusEnumResponseDto.ERR;

	protected String inventoryResponseDtoErrorMessage1 = null;

}
