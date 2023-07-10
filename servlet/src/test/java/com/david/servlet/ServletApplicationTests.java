package com.david.servlet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ServletApplicationTests {

	@Autowired
	TestRestTemplate restTemplate;

	@Test
	void test() {
		User user = new User(null, "david", "123456", "david@email.com");
		User postResponse = restTemplate.postForObject("/users", user, User.class);

		assertNotNull(postResponse.id());
		assertEquals(user.username(), postResponse.username());
		assertEquals(user.password(), postResponse.password());
		assertEquals(user.email(), postResponse.email());

		User[] getResponses = restTemplate.getForObject("/users", User[].class);
		User getResponse = getResponses[0];

		assertNotNull(getResponse.id());
		assertEquals(user.username(), getResponse.username());
		assertEquals(user.password(), getResponse.password());
		assertEquals(user.email(), getResponse.email());
	}

}
