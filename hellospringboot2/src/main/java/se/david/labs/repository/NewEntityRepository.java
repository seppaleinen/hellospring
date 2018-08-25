package se.david.labs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.david.labs.repository.entity.NewEntity;

@Repository
public interface NewEntityRepository extends JpaRepository<NewEntity, Long> {
}
