package se.david.labs.api;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import se.david.labs.dto.SuperDto;
import se.david.labs.repository.SuperRepository;
import se.david.labs.repository.entity.SuperEntity;

import java.util.stream.IntStream;
import java.util.stream.Stream;

@RestController
public class FluxController {

    @Autowired
    private SuperRepository superRepository;

    @GetMapping(path = "/reactive/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Mono<SuperEntity> mono(@PathVariable("id") Long id){
        return superRepository.findById(id);
    }

    @GetMapping(path = "/reactive", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Flux<SuperEntity> getAll(){
        return superRepository.findAll();
    }

    @PostMapping(params = "/reactive", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Flux<SuperEntity> create() {
        Flux<SuperEntity> flux = Flux.fromStream(IntStream.range(0, 2000)
                .boxed()
                .map(a -> new SuperEntity(new Long(a))));

        return superRepository.saveAll(flux);
    }
}
