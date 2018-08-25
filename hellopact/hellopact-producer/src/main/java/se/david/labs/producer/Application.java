package se.david.labs.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@PostMapping(value = "/producer",
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	ResponseEntity<RequestResponseDto> call(@RequestBody RequestResponseDto request) {
		return ResponseEntity.ok(request);
	}
	@GetMapping(value = "/producer/foo", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	ResponseEntity<Foo> get() {
		return ResponseEntity.ok(new Foo("foo", "bar", "baz"));
	}
}
