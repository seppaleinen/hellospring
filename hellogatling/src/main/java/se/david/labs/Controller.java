package se.david.labs;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
class Controller {

    @GetMapping(value = "/endpoint", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<SuperDto> call() {
        return ResponseEntity.ok(new SuperDto("Hello"));
    }

}
