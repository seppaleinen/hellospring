package se.david.labs.springrestdocs;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.david.labs.RequestResponse;

@RestController
@RequestMapping(path = "/spring-rest-docs")
public class SpringDocsController {
    @GetMapping(path = "/spring",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<RequestResponse> springRestDocs(@RequestBody RequestResponse request) {
        return ResponseEntity.ok(new RequestResponse(request.getMessage()));
    }
}
