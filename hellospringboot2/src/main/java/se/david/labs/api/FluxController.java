package se.david.labs.api;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import se.david.labs.repository.entity.NewEntity;


@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class FluxController {

    @GetMapping(path = "/reactive/{id}")
    Mono<NewEntity> mono(@PathVariable("id") Long id){
        return Mono.just(new NewEntity(id));
    }

    @GetMapping(path = "/reactive")
    Flux<NewEntity> getAll(){
        return Flux.range(0, 2000).map(a -> new NewEntity(new Long(a)));
    }

    @PostMapping(path = "/reactive")
    Flux<NewEntity> create() {
        return Flux.range(0, 200000).map(a -> new NewEntity(new Long(a)));
    }
}
