package se.david.neo4j.entity;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.ArrayList;
import java.util.List;

@NodeEntity
public class Movie {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private int released;
    private String tagline;

    @Relationship(type = "ACTED_IN", direction = Relationship.INCOMING)
    private List<Role> roles = new ArrayList<>();

    public Movie() {
    }

    public Movie(String title, int released, String tagline) {
        this.title = title;
        this.released = released;
        this.tagline = tagline;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getReleased() {
        return released;
    }

    public String getTagline() {
        return tagline;
    }

    public List<Role> getRoles() {
        return roles;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", released=" + released +
                ", tagline='" + tagline + '\'' +
                ", roles=" + roles +
                '}';
    }
}
