package org.samples.microservices.sample.inventory.unittests.web.configs;

import org.samples.microservices.sample.constants.ProfileConstants;
import org.samples.microservices.sample.inventory.commons.AbstractInventoryOutboundRestConfigTest;
import org.samples.microservices.sample.inventory.web.configs.InventoryOutboundRestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * @author Matteo Baiguini
 * matteo@solidarchitectures.com
 * 19 Sep 2018
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = InventoryOutboundRestConfig.class)
@ActiveProfiles({ProfileConstants.SPRING_PROFILE_TEST, ProfileConstants.SPRING_PROFILE_OUTBOUND_REST})
public class InventoryOutboundRestConfigUnitTest extends AbstractInventoryOutboundRestConfigTest {

	@Test
	public void testInventoryOutboundRestConfig() {

		testOutboundConfig();
	}

}
