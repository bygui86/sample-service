package org.samples.microservices.sample.inventory.unittests.web.xml;

import org.junit.BeforeClass;

import java.util.Date;


/**
 * @author Matteo Baiguini
 * matteo@solidarchitectures.com
 * 16 Sep 2018
 */
public class AbstractInventoryInboundXmlUnitTest {

	protected static final String XML_FOLDER = "testing/xml/";

	protected static final String XML_REQUEST_FILE = XML_FOLDER + "InventoryRequestDto.xml";

	protected static final String XML_RESPONSE_FILE = XML_FOLDER + "InventoryResponseDto.xml";

	protected static final String REQUEST_PRODUCT_ID_1 = "PROD-0001";

	protected static final String REQUEST_POS_ID_1 = "ONLINE";

	protected static final int REQUEST_QTY_1 = 3;

	protected static final String REQUEST_PRODUCT_ID_2 = "PROD-0002";

	protected static final String REQUEST_POS_ID_2 = "SHOP-001";

	protected static final int REQUEST_QTY_2 = -4;

	protected static final String RESPONSE_ERROR_MSG_1 = null;

	protected static final String RESPONSE_ERROR_MSG_2 = "ERROR MESSAGE";

	private static String inventoryId1;

	private static String inventoryId2;

	public static String getInventoryId1() {

		return inventoryId1;
	}

	public static String getInventoryId2() {

		return inventoryId2;
	}

	@BeforeClass
	public static void setUp() throws InterruptedException {

		inventoryId1 = Long.toString(new Date().getTime());
		Thread.sleep(50);
		inventoryId2 = Long.toString(new Date().getTime());
	}

}
