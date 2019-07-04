package se.david.labs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@GetMapping(value = "/endpoint", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	ResponseEntity<SuperDto> call() {
		return ResponseEntity.ok(new SuperDto("Hello"));
	}
}
