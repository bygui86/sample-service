package org.samples.microservices.sample.startuptests;

import org.samples.microservices.sample.SampleServiceApplication;
import org.junit.Test;


/**
 * @author Matteo Baiguini
 * matteo@solidarchitectures.com
 * 19 Sep 2018
 * <p>
 * PLEASE NOTE: Here there is no need of @RunWith because neither injections nor application context are needed.
 */
public class SampleServiceMainStartupTest {

	@Test
	public void testApplicationMain() {

		// given
		final String[] applicationPropertiesArgs = {
				"--server.port = 65010",
				"--management.server.port = 65020",
				"--outbound.rest.other.service.inventories.url = http://fake:10000"
		};

		// when
		SampleServiceApplication.main(applicationPropertiesArgs);

		// then
		// -
	}

}
