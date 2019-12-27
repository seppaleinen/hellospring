package se.david.neo4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

@SpringBootApplication
@EnableNeo4jRepositories("se.david.neo4j")
public class NeoApplication {
    public static void main(String[] args) {
        SpringApplication.run(NeoApplication.class, args);
    }
}
