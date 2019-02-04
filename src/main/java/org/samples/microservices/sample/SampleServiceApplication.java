package org.samples.microservices.sample;

import org.samples.microservices.sample.annotations.EnableSelectiveAutoConfiguration;
import org.samples.microservices.sample.annotations.SelectiveComponentScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;


@SelectiveComponentScan
@EnableSelectiveAutoConfiguration
@SpringBootConfiguration
public class SampleServiceApplication {

	public static void main(final String[] args) {

		SpringApplication.run(SampleServiceApplication.class, args);
	}
}
