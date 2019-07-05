package se.david.labs.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import se.david.labs.repository.entity.NewEntity;

@Repository
public interface NewEntityRepository extends ReactiveCrudRepository<NewEntity, Long> {
}
