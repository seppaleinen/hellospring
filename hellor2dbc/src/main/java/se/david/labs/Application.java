package se.david.labs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import se.david.labs.repository.NewEntityRepository;
import se.david.labs.repository.entity.NewEntity;

@SpringBootApplication
@RestController
public class Application {
	@Autowired
	private NewEntityRepository newEntityRepository;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@PostMapping(path = "/reactive", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	Mono<NewEntity> createOne(NewEntity newEntity) {
		return newEntityRepository.save(newEntity);
	}

	@GetMapping(path = "/reactive/{id}")
	Mono<NewEntity> mono(@PathVariable("id") Long id){
		return newEntityRepository.findById(id);
	}

	@GetMapping(path = "/reactive")
	Flux<NewEntity> getAll(){
		return newEntityRepository.findAll();
	}
}
