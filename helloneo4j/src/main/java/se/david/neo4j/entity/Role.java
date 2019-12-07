package se.david.neo4j.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

import java.util.ArrayList;
import java.util.List;

@RelationshipEntity(type = "ACTED_IN")
public class Role {

    @Id
    @GeneratedValue
    private Long id;
    private List<String> roles = new ArrayList<>();

    @StartNode
    private Person person;

    @EndNode
    @JsonIgnore
    private Movie movie;

    public Role() {
    }

    public Role(Movie movie, String role, Person actor) {
        this.movie = movie;
        this.roles.add(role);
        this.person = actor;
    }

    public Long getId() {
        return id;
    }

    public List<String> getRoles() {
        return roles;
    }

    public Person getPerson() {
        return person;
    }

    public Movie getMovie() {
        return movie;
    }
}