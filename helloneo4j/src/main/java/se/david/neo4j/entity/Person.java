package se.david.neo4j.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.ArrayList;
import java.util.List;

@NodeEntity
public class Person {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private int born;

    @Relationship(type = "ACTED_IN")
    @JsonIgnore
    private List<Movie> movies = new ArrayList<>();

    public Person() {
    }

    public Person(String name, int born, Movie movie) {
        this.name = name;
        this.born = born;
        this.movies.add(movie);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getBorn() {
        return born;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", born=" + born +
                ", movies=" + movies +
                '}';
    }
}