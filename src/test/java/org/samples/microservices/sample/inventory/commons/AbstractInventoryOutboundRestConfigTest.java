package org.samples.microservices.sample.inventory.commons;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * @author Matteo Baiguini
 * matteo@solidarchitectures.com
 * 6 Nov 2018
 */
public class AbstractInventoryOutboundRestConfigTest {

	@Autowired
	protected ApplicationContext applicationContext;

	protected void testOutboundConfig() {

		// given
		final String outboundClientHttpRequestFactoryBeanName = "outboundClientHttpRequestFactory";
		final String outboundRestTemplateBeanName = "outboundRestTemplate";

		// when
		final Object outboundClientHttpRequestFactoryBean = applicationContext.getBean(outboundClientHttpRequestFactoryBeanName);
		final Object outboundRestTemplateBean = applicationContext.getBean(outboundRestTemplateBeanName);

		// then
		assertThat(outboundClientHttpRequestFactoryBean)
				.isInstanceOf(ClientHttpRequestFactory.class);
		assertThat(outboundRestTemplateBean)
				.isInstanceOf(RestTemplate.class);
	}

}
