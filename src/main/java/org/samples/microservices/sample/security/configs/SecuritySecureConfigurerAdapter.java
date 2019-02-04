package org.samples.microservices.sample.security.configs;

import org.samples.microservices.sample.constants.ProfileConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Slf4j
@Configuration(value = "securitySecureConfigurerAdapter")
@Order(SecurityProperties.BASIC_AUTH_ORDER - 1)
@EnableWebSecurity
@Profile(ProfileConstants.SPRING_PROFILE_SECURE)
public class SecuritySecureConfigurerAdapter extends WebSecurityConfigurerAdapter {

	/*
	 * PLEASE NOTE: For sake of simplicity we are omitting specific security configurations
	 */
	@Override
	protected void configure(final HttpSecurity http) throws Exception {

		log.debug("Loading SECURE security config...");

		http
				.authorizeRequests()
				.anyRequest().authenticated()

				.and()
				.httpBasic()

				.and()
				.cors()

				.and()
				.csrf().disable()
		;
	}

}
