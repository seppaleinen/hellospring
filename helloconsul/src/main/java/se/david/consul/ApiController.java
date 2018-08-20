package se.david.consul;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
public class ApiController {

    @GetMapping(path = "/ping/{message}")
    ResponseEntity<String> ping(@PathVariable("message") @Valid @NotNull String message) {
        return ResponseEntity.ok(String.format("PONG: %s", message));
    }
}
