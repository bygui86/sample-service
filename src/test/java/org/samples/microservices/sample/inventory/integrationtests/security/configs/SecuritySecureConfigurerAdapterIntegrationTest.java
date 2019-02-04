package org.samples.microservices.sample.inventory.integrationtests.security.configs;

import org.samples.microservices.sample.constants.ProfileConstants;
import org.samples.microservices.sample.security.configs.SecurityInsecureConfigurerAdapter;
import org.samples.microservices.sample.security.configs.SecuritySecureConfigurerAdapter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * @author Matteo Baiguini
 * matteo@solidarchitectures.com
 * 19 Sep 2018
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles({ProfileConstants.SPRING_PROFILE_SECURE, ProfileConstants.SPRING_PROFILE_TEST})
public class SecuritySecureConfigurerAdapterIntegrationTest {

	@Autowired
	private ApplicationContext applicationContext;

	@Test
	public void testSecureConfig() {

		// given
		// -

		// when
		applicationContext.getBean(SecuritySecureConfigurerAdapter.class);

		// then
		// -
	}

	@Test(expected = NoSuchBeanDefinitionException.class)
	public void testInsecureConfig() {

		// given
		// -

		// when
		applicationContext.getBean(SecurityInsecureConfigurerAdapter.class);

		// then
		// throw exception
	}

}
