package com.example.domstolstyrrelsen.domstolstyrelsen;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.stereotype.Component;

@Component
public class PodIdentity {

	static final Path NAMESPACE_FILE = Path.of("/var/run/secrets/kubernetes.io/serviceaccount/namespace");

	public String text() {
		return text(namespace(), replica());
	}

	public String namespace() {
		return namespace(System.getenv("POD_NAMESPACE"), readNamespaceFile());
	}

	public String replica() {
		return replica(System.getenv("POD_NAME"), System.getenv("HOSTNAME"), localHostname());
	}

	static String text(String namespace, String replica) {
		return "namespace: " + namespace + "\nreplica: " + replica + "\n";
	}

	static String namespace(String fromEnv, String fromFile) {
		if (notBlank(fromEnv)) {
			return fromEnv.trim();
		}
		if (notBlank(fromFile)) {
			return fromFile.trim();
		}
		return "local";
	}

	static String replica(String podName, String hostname, String localHostname) {
		if (notBlank(podName)) {
			return podName.trim();
		}
		if (notBlank(hostname)) {
			return hostname.trim();
		}
		if (notBlank(localHostname)) {
			return localHostname.trim();
		}
		return "unknown";
	}

	private static String readNamespaceFile() {
		if (!Files.isReadable(NAMESPACE_FILE)) {
			return null;
		}
		try {
			return Files.readString(NAMESPACE_FILE);
		}
		catch (IOException ignored) {
			return null;
		}
	}

	private static String localHostname() {
		try {
			return InetAddress.getLocalHost().getHostName();
		}
		catch (UnknownHostException ignored) {
			return null;
		}
	}

	private static boolean notBlank(String value) {
		return value != null && !value.isBlank();
	}

}
