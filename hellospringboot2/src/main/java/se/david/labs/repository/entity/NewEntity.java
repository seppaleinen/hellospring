package se.david.labs.repository.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class NewEntity {
    @Id
    private Long id;

    public NewEntity() {}

    public NewEntity(Long id) {
            this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewEntity newEntity = (NewEntity) o;
        return Objects.equals(id, newEntity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
