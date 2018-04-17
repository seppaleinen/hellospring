package se.david.labs.api;

import org.reactivestreams.Subscriber;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import se.david.labs.dto.SuperDto;

@RestController
public class FluxController {

    @GetMapping(path = "/mono", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Mono<SuperDto> mono(){
        return null;
    }

}
