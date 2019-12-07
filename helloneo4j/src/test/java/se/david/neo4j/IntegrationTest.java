package se.david.neo4j;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.harness.ServerControls;
import org.neo4j.harness.TestServerBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import se.david.neo4j.entity.Movie;
import se.david.neo4j.entity.Person;
import se.david.neo4j.entity.Role;

import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = NeoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(loader = SpringBootContextLoader.class)
@ActiveProfiles("test")
public class IntegrationTest {
    @LocalServerPort
    private int port;
    private static ServerControls embeddedDatabaseServer;
    @Autowired
    private MovieRepository movieRepository;


    @BeforeClass
    public static void beforeClass() {
        embeddedDatabaseServer = TestServerBuilders
                .newInProcessBuilder()
                .newServer();
    }

    @AfterClass
    public static void afterClass() {
        embeddedDatabaseServer.close();
    }

    @Before
    public void setup() {
        RestAssured.port = port;
        movieRepository.deleteAll();
    }

    @Ignore
    @Test
    public void findByTitle() {
        Movie matrix = new Movie("The Matrix", 1999, "Welcome to the Real World");
        List<Role> roles = Arrays.asList(
                new Role(matrix, "Neo", new Person("Keanu Reeves", 1964, matrix)),
                new Role(matrix, "Trinity", new Person("Carrie-Anne Moss", 1967, matrix)),
                new Role(matrix, "Morpheus", new Person("Laurence Fishburne", 1967, matrix)),
                new Role(matrix, "Agent Smith", new Person("Hugo Weaving", 1967, matrix)),
                new Role(matrix, "Emil", new Person("Emil Eifrem", 1978, matrix))
        );
        matrix.getRoles().addAll(roles);
        movieRepository.save(matrix);

        Response response = given().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).
                when().get("/movies/search/findByTitle?title=" + "The%20Matrix")
                .thenReturn();

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        Movie movie = response.getBody().as(Movie.class);
        assertEquals("The Matrix", movie.getTitle());
        assertEquals(4, movie.getRoles().size());

    }

    @Test
    public void findByTitleLike() {
        Movie matrix = new Movie("The Matrix", 1999, "Welcome to the Real World");
        List<Role> roles = Arrays.asList(
                new Role(matrix, "Neo", new Person("Keanu Reeves", 1964, matrix)),
                new Role(matrix, "Trinity", new Person("Carrie-Anne Moss", 1967, matrix)),
                new Role(matrix, "Morpheus", new Person("Laurence Fishburne", 1967, matrix)),
                new Role(matrix, "Agent Smith", new Person("Hugo Weaving", 1967, matrix))
        );
        matrix.getRoles().addAll(roles);
        movieRepository.save(matrix);


        Response response = given().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).
                when().get("/movies/search/findByTitleLike?title=*Matrix*")
                .thenReturn();

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        List<Movie> movies = Arrays.asList(response.getBody().as(Movie[].class));
        assertEquals(1, movies.size());
        assertEquals("The Matrix", movies.get(0).getTitle());
        assertEquals(4, movies.get(0).getRoles().size());
    }

    @Test
    public void graph() {
        Movie matrix = new Movie("The Matrix", 1999, "Welcome to the Real World");
        List<Role> roles = Arrays.asList(
                new Role(matrix, "Neo", new Person("Keanu Reeves", 1964, matrix)),
                new Role(matrix, "Trinity", new Person("Carrie-Anne Moss", 1967, matrix)),
                new Role(matrix, "Morpheus", new Person("Laurence Fishburne", 1967, matrix)),
                new Role(matrix, "Agent Smith", new Person("Hugo Weaving", 1967, matrix))
        );
        matrix.getRoles().addAll(roles);
        movieRepository.save(matrix);


        Response response = given().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).
                when().get("/graph")
                .thenReturn();

        assertEquals(HttpStatus.OK.value(), response.statusCode());
        Map<String, Object> map = response.getBody().as(Map.class);
        assertEquals(2, map.size());
    }
}
