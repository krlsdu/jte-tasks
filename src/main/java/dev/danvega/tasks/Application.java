package dev.danvega.tasks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.config.HypermediaRestTemplateConfigurer;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	RestTemplate restClient(HypermediaRestTemplateConfigurer configurer, RestTemplateBuilder builder) {
		RestTemplate client = builder
				.basicAuthentication("user", "user")
				.rootUri("http://localhost:8081")
				.build();
		return configurer.registerHypermediaTypes(client);
	}
}
