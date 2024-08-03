package com.TranslationApplication;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;


import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class TranslationApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void dbTest(){
	}

	@Test
	void APITest(){
		RestTemplate restTemplate = new RestTemplate();
			String url = "https://translate.fedilab.app/translate";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		Map<String, String> requestBody = new HashMap<>();
		requestBody.put("q", "4.1. DataSource Initialization\n" +
				"\n" +
				"We can use basic SQL scripts to initialize the database. In order to demonstrate this, let’s add a data.sql file under src/main/resources directory:\n" +
				"\n" +
				"INSERT INTO countries (id, name) VALUES (1, 'USA');\n" +
				"INSERT INTO countries (id, name) VALUES (2, 'France');\n" +
				"INSERT INTO countries (id, name) VALUES (3, 'Brazil');\n" +
				"INSERT INTO countries (id, name) VALUES (4, 'Italy');\n" +
				"INSERT INTO countries (id, name) VALUES (5, 'Canada');\n" +
				"\n" +
				"Here, the script populates the countries table in our schema with some sample data.\n" +
				"\n" +
				"Spring Boot will automatically pick up this file and run it against an embedded in-memory database, such as our configured H2 instance. This is a good way to seed the database for testing or initialization purposes.\n" +
				"\n" +
				"We can disable this default behavior by setting the spring.sql.init.mode property to never. Additionally, multiple SQL files can also be configured to load the initial data.\n" +
				"\n" +
				"Our article about loading initial data covers this topic in more detail.");
		requestBody.put("source", "en");
		requestBody.put("target", "ru");
		requestBody.put("format", "text");

		HttpEntity<Map<String, String>> request = new HttpEntity<>(requestBody, headers);

		Map<String, String> response = restTemplate.postForObject(url, request, Map.class);

		System.out.println(response.get("translatedText"));
	}

}
