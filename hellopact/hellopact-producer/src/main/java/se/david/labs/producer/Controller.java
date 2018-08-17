package se.david.labs.producer;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
class Controller {
    @PostMapping(
            value = "/producer",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<RequestResponseDto> call(@RequestBody RequestResponseDto request) {
        return ResponseEntity.ok(new RequestResponseDto(request.getData()));
    }
    @GetMapping(value = "/producer/foo",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Foo get() {
        return new Foo("foo", "bar", "baz");
    }
}
