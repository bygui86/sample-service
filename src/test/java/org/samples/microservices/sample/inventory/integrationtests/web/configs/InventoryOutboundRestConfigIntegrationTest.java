package org.samples.microservices.sample.inventory.integrationtests.web.configs;

import org.samples.microservices.sample.constants.ProfileConstants;
import org.samples.microservices.sample.inventory.commons.AbstractInventoryOutboundRestConfigTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * @author Matteo Baiguini
 * matteo@solidarchitectures.com
 * 19 Sep 2018
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles({
		ProfileConstants.SPRING_PROFILE_INSECURE, ProfileConstants.SPRING_PROFILE_TEST,
		ProfileConstants.SPRING_PROFILE_INBOUND_REST, ProfileConstants.SPRING_PROFILE_OUTBOUND_REST
})
public class InventoryOutboundRestConfigIntegrationTest extends AbstractInventoryOutboundRestConfigTest {

	@Test
	public void testInventoryOutboundRestConfig() {

		testOutboundConfig();
	}

}
