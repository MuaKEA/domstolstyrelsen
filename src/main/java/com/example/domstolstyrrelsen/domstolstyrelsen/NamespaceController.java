package com.example.domstolstyrrelsen.domstolstyrelsen;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NamespaceController {

	private final PodIdentity identity;

	public NamespaceController(PodIdentity identity) {
		this.identity = identity;
	}

	@GetMapping(value = "/", produces = MediaType.TEXT_PLAIN_VALUE)
	public String home() {
		return identity.text();
	}

}
