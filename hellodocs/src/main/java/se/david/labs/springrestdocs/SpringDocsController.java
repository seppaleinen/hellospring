package se.david.labs.springrestdocs;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import se.david.labs.RequestResponse;

@RestController
public class SpringDocsController {
    @GetMapping(path = "/spring-rest-docs",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<RequestResponse> springRestDocs(@RequestBody RequestResponse request) {
        return ResponseEntity.ok(new RequestResponse(request.getMessage()));
    }
}
