package com.david.reactive;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ReactiveApplicationTests {

	@Autowired
	private WebTestClient webTestClient;

	@Test
	void test() {
		User user = new User(null, "david", "123456", "david@email.com");
		webTestClient.post().uri("/users").bodyValue(user)
				.exchange()
				.expectStatus().is2xxSuccessful()
				.expectBody(User.class)
				.value(postResponse -> {
					assertNotNull(postResponse.id());
					assertEquals(user.username(), postResponse.username());
					assertEquals(user.password(), postResponse.password());
					assertEquals(user.email(), postResponse.email());
				});

		webTestClient.get().uri("/users")
				.exchange()
				.expectStatus().is2xxSuccessful()
				.expectBodyList(User.class)
				.value(getResponses -> {
					User getResponse = getResponses.get(0);
					assertNotNull(getResponse.id());
					assertEquals(user.username(), getResponse.username());
					assertEquals(user.password(), getResponse.password());
					assertEquals(user.email(), getResponse.email());
				});
	}

}
