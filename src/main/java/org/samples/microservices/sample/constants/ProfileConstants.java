package org.samples.microservices.sample.constants;

public final class ProfileConstants {

	private ProfileConstants() {
		// no-op
	}

	public static final String SPRING_PROFILE_LOCAL = "local";

	public static final String SPRING_PROFILE_DOCKER = "docker";

	public static final String SPRING_PROFILE_KUBERNETES = "kube";

	public static final String SPRING_PROFILE_TEST = "test";

	public static final String SPRING_PROFILE_TEST_LOCAL = "test-local";

	public static final String SPRING_PROFILE_TEST_KUBERNETES = "test-kube";

	public static final String SPRING_PROFILE_INSECURE = "insecure";

	public static final String SPRING_PROFILE_SECURE = "secure";

	public static final String SPRING_PROFILE_INBOUND_REST = "in-rest";

	public static final String SPRING_PROFILE_OUTBOUND_REST = "out-rest";

	public static final String SPRING_PROFILE_OUTBOUND_MOCK = "out-mock";

}
