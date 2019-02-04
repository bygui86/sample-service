package org.samples.microservices.sample.inventory.integrationtests.configs;

import org.samples.microservices.sample.configs.Swagger2Config;
import org.samples.microservices.sample.constants.ProfileConstants;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import springfox.documentation.spring.web.plugins.Docket;

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
public class Swagger2ConfigIntegrationTest {

	@Autowired
	ApplicationContext applicationContext;

	@Test
	public void testSwagger2Config() {

		// given
		// -

		// when
		applicationContext.getBean(Swagger2Config.class);
		final Docket swaggerDocket = applicationContext.getBean(Docket.class);

		// then
		assertThat(swaggerDocket.isEnabled())
				.isTrue();
	}

}
