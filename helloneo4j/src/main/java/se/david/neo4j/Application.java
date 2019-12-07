package se.david.neo4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

@SpringBootApplication
@EnableNeo4jRepositories("se.david.neo4j")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
