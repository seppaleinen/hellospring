package se.david.neo4j;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.driver.springframework.boot.test.autoconfigure.Neo4jTestHarnessAutoConfiguration;
import org.neo4j.harness.ServerControls;
import org.neo4j.harness.TestServerBuilders;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import se.david.neo4j.dto.D3Format;
import se.david.neo4j.entity.Movie;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = NeoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration(exclude = { Neo4jTestHarnessAutoConfiguration.class }) // <.>
@ContextConfiguration(loader = SpringBootContextLoader.class, initializers = { IntegrationTest.Initializer.class })
@ActiveProfiles("test")
public class IntegrationTest {
	@LocalServerPort
	private int port;
	private static ServerControls embeddedDatabaseServer;

	@BeforeClass
	public static void beforeClass() throws URISyntaxException {
		URI testData = IntegrationTest.class.getClassLoader().getResource("testdata.neo4j").toURI();

		embeddedDatabaseServer = TestServerBuilders
				.newInProcessBuilder()
				.withFixture(new File(testData))
				.newServer();
	}

	@AfterClass
	public static void afterClass() {
		embeddedDatabaseServer.close();
	}

	@Before
	public void setup() {
		RestAssured.port = port;
	}

	@Test
	public void findByTitle() {
		Response response = given().contentType(MediaType.APPLICATION_JSON_VALUE)
				.when().get("/movies/search/findByTitle?title=The Matrix")
				.thenReturn();

		assertEquals(HttpStatus.OK.value(), response.getStatusCode());
		Movie movie = response.getBody().as(Movie.class);
		assertEquals("The Matrix", movie.getTitle());
		assertEquals(movie.getRoles().toString(), 5, movie.getRoles().size());
	}

	@Test
	public void findByTitleMustHaveValue() {
		Stream.of("", "?title=")
				.map(a -> given().contentType(MediaType.APPLICATION_JSON_VALUE)
						.when().get("/movies/search/findByTitle").then())
				.forEach(resp -> resp.statusCode(HttpStatus.BAD_REQUEST.value()));
	}

	@Test
	public void findByTitleLike() {
		Response response = given().contentType(MediaType.APPLICATION_JSON_VALUE)
				.when().get("/movies/search/findByTitleLike?title=*Matrix*")
				.thenReturn();

		assertEquals(HttpStatus.OK.value(), response.getStatusCode());
		List<Movie> movies = Arrays.asList(response.getBody().as(Movie[].class));
		assertEquals(3, movies.size());
		assertEquals("The Matrix", movies.get(0).getTitle());
		assertEquals(5, movies.get(0).getRoles().size());
	}

	@Test
	public void findByTitleLikeMustHaveValue() {
		Stream.of("", "?title=")
				.map(a -> given().contentType(MediaType.APPLICATION_JSON_VALUE)
						.when().get("/movies/search/findByTitleLike").then())
				.forEach(resp -> resp.statusCode(HttpStatus.BAD_REQUEST.value()));
	}

	@Test
	public void graph() {
		Response response = given().contentType(MediaType.APPLICATION_JSON_VALUE).
				when().get("/graph")
				.thenReturn();

		assertEquals(HttpStatus.OK.value(), response.statusCode());
		D3Format d3FormatResponse = response.getBody().as(D3Format.class);
		assertEquals(121, d3FormatResponse.getNodes().size());
		assertEquals(21, d3FormatResponse.getNodes().stream().filter(node -> node.getLabel().equals("movie")).count());

		assertEquals(100, d3FormatResponse.getRels().size());
		assertEquals(100, d3FormatResponse.getNodes().stream().filter(node -> node.getLabel().equals("actor")).count());
	}

	static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
		public void initialize(ConfigurableApplicationContext configurableApplicationContext) {

			TestPropertyValues.of(
					"org.neo4j.driver.uri=" + embeddedDatabaseServer.boltURI().toString(),
					"org.neo4j.driver.authentication.password="
			).applyTo(configurableApplicationContext.getEnvironment());
		}
	}

}
