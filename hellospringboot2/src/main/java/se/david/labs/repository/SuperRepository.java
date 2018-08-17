package se.david.labs.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import se.david.labs.repository.entity.SuperEntity;

public interface SuperRepository extends ReactiveCrudRepository<SuperEntity, Long> {
}
