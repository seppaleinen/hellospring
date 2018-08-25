package se.david.labs.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import se.david.labs.repository.NewEntityRepository;
import se.david.labs.repository.entity.NewEntity;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;


@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class FluxController {
    @Autowired
    private NewEntityRepository newEntityRepository;

    @GetMapping(path = "/reactive/{id}")
    Mono<NewEntity> mono(@PathVariable("id") Long id){
        return Mono.justOrEmpty(newEntityRepository.findById(id));
    }

    @GetMapping(path = "/reactive")
    Flux<NewEntity> getAll(){
        return Flux.fromStream(newEntityRepository.findAll().stream());
    }

    @PostMapping(path = "/reactive/{amount}")
    Flux<NewEntity> create(@PathVariable("amount") int amount) {
        Stream<NewEntity> newEntityStream = LongStream.range(0, amount)
                .boxed()
                .map(NewEntity::new);

        return Flux.fromIterable(newEntityRepository.saveAll(newEntityStream.collect(Collectors.toList())));
    }
}
