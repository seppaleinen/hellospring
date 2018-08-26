package se.david.labs;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import io.micronaut.configuration.hibernate.jpa.scope.CurrentSession;
import io.micronaut.spring.tx.annotation.Transactional;

import java.util.List;

@Singleton
public class DbRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    public DbRepository(@CurrentSession EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional(readOnly = true)
    public List<DbEntity> findAll() {
        return entityManager.createQuery("select entity from DbEntity entity", DbEntity.class)
                .getResultList();
    }

    @Transactional
    public DbEntity save(DbEntity dbEntity) {
        entityManager.persist(dbEntity);
        return dbEntity;
    }

    @Transactional
    public void deleteAll() {
        entityManager.createQuery("delete from DbEntity").executeUpdate();
    }
}
