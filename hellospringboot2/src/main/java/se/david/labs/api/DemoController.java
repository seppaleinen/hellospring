package se.david.labs.api;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import se.david.labs.dto.SuperDto;


@RestController
class DemoController {

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<SuperDto> call() {
        return ResponseEntity.ok(new SuperDto("Hello"));
    }

}
