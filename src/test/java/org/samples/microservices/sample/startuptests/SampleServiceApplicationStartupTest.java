package org.samples.microservices.sample.startuptests;

import org.samples.microservices.sample.constants.ProfileConstants;
import org.samples.microservices.sample.inventory.web.controllers.InventoryInboundController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;


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
public class SampleServiceApplicationStartupTest {

	@Autowired
	private ApplicationContext applicationContext;

	/*
		PLEASE NOTE:
		Here we are verifying just the controller spring bean, because if it is properly instantiated means that all its dependencies are satisfied.
	 */
	@Test
	public void testApplicationStartup() {

		// given
		final String inventoryInboundControllerBeanName = "inventoryInboundController";

		// when
		final Object inventoryInboundControllerObject = applicationContext.getBean(inventoryInboundControllerBeanName);

		// then
		assertThat(inventoryInboundControllerObject)
				.isInstanceOf(InventoryInboundController.class);
	}

}
