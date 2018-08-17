package se.david.labs.repository.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class SuperEntity {
    @Id
    private Long id;

    public SuperEntity(Long id) {
            this.id = id;
    }

    public Long getId() {
        return id;
    }
}
