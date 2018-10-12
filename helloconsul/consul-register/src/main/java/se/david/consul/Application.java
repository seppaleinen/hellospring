package se.david.consul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@SpringBootApplication
@RestController
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @GetMapping(path = "/ping/{message}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<String> ping(@PathVariable("message") @Valid @NotEmpty String message) {
        return ResponseEntity.ok(String.format("{\"message\": \"%s\"}", message));
    }
}
