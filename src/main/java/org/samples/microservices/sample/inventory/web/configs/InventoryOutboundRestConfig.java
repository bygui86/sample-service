package org.samples.microservices.sample.inventory.web.configs;

import org.samples.microservices.sample.constants.ProfileConstants;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;


@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter(value = AccessLevel.PROTECTED)
@Profile(ProfileConstants.SPRING_PROFILE_OUTBOUND_REST)
@Configuration("inventoryOutboundRestConfig")
public class InventoryOutboundRestConfig {

	@Value("${outbound.rest.client.connect.timeout:2000}")
	int connectTimeout;

	@Value("${outbound.rest.client.connection.request.timeout:5000}")
	int connectionRequestTimeout;

	@Value("${outbound.rest.client.read.timeout:10000}")
	int readTimeout;

	@Bean("outboundRestTemplate")
	@Autowired
	@Qualifier("outboundClientHttpRequestFactory")
	public RestTemplate restTemplate(final ClientHttpRequestFactory clientHttpRequestFactory) {

		log.debug("Creating outbound RestTemplate...");

		return new RestTemplate(clientHttpRequestFactory);
	}

	/**
	 * PLEASE NOTE:
	 * To support HTTP operations other than HEAD, GET and POST, Apache HttpComponents HttpClient 4.2 or higher is required.
	 * This configuration is specific to avoid that Spring takes the standard Java HttpURLConnection, using a HttpClient lower than 4.2.
	 * see
	 * https://stackoverflow.com/questions/29447382/resttemplate-patch-request
	 * https://jira.spring.io/browse/SPR-7985?focusedCommentId=80924&page=com.atlassian.jira.plugin.system.issuetabpanels%3Acomment-tabpanel#comment-80924
	 */
	@Bean("outboundClientHttpRequestFactory")
	public ClientHttpRequestFactory getClientHttpRequestFactory() {

		log.debug("Creating outbound ClientHttpRequestFactory...");

		final HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		requestFactory.setConnectTimeout(getConnectTimeout());
		requestFactory.setConnectionRequestTimeout(getConnectionRequestTimeout());
		requestFactory.setReadTimeout(getReadTimeout());
		return requestFactory;
	}

}
