package se.david.neo4j;

import io.restassured.RestAssured;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
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

import static io.restassured.RestAssured.given;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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
    }

    @Test
    public void test() {
        given().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).
                when().get("/neo4j")
                .then()
                .statusCode(HttpStatus.OK.value());
    }
}
