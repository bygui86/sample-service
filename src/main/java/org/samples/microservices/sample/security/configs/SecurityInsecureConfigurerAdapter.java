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
@Configuration(value = "securityInsecureConfigurerAdapter")
@Order(SecurityProperties.BASIC_AUTH_ORDER - 1)
@EnableWebSecurity
@Profile(ProfileConstants.SPRING_PROFILE_INSECURE)
public class SecurityInsecureConfigurerAdapter extends WebSecurityConfigurerAdapter {

	/*
	 * PLEASE NOTE: For sake of simplicity we are omitting specific security configurations
	 */
	@Override
	protected void configure(final HttpSecurity http) throws Exception {

		log.debug("Loading INSECURE security config...");

		http
				.authorizeRequests()
				.anyRequest().permitAll()

				.and()
				.httpBasic()

				.and()
				// .cors().disable()
				.cors()

				.and()
				.csrf().disable()
		;
	}

}
