package com.abforthewin.chucknoris_api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableScheduling
public class ChucknorisApiApplication {

	private static final Logger log = LoggerFactory.getLogger(ChucknorisApiApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ChucknorisApiApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Component
	public class JokeReporter {

		private final RestTemplate restTemplate;

		public JokeReporter(RestTemplate restTemplate) {
			this.restTemplate = restTemplate;
		}

		@Scheduled(fixedRate = 5000)
		public void reportJoke() {
			Joke joke = restTemplate.getForObject("https://api.chucknorris.io/jokes/random", Joke.class);
			log.info(joke.toString());
		}
	}
}



