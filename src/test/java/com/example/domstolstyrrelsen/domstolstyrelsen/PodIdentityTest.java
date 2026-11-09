package com.example.domstolstyrrelsen.domstolstyrelsen;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class PodIdentityTest {

	@Test
	void showsNamespaceAndReplica() {
		assertEquals("namespace: stage\nreplica: domstolstyrelsen-1\n", PodIdentity.text("stage", "domstolstyrelsen-1"));
	}

	@Test
	void namespacePrefersEnvironment() {
		assertEquals("prod", PodIdentity.namespace("prod", "stage"));
	}

	@Test
	void namespaceUsesFileWhenEnvironmentIsMissing() {
		assertEquals("test", PodIdentity.namespace("  ", "test"));
	}

	@Test
	void namespaceIsLocalOutsideKubernetes() {
		assertEquals("local", PodIdentity.namespace(null, null));
	}

	@Test
	void replicaPrefersPodName() {
		assertEquals("domstolstyrelsen-2", PodIdentity.replica("domstolstyrelsen-2", "other", "laptop"));
	}

	@Test
	void replicaFallsBackToHostname() {
		assertEquals("domstolstyrelsen-0", PodIdentity.replica(null, "domstolstyrelsen-0", "laptop"));
	}

}
