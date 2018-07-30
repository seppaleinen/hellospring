package se.david.labs.producer;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(loader = SpringBootContextLoader.class)
public class ProducerIntegrationTest {
    @LocalServerPort
    private int port;

    @Before
    public void beforeAll() {
        RestAssured.port = port;
    }

    @Test
    public void testProducerEndpoint() {
        JsonPath expectedJson = new JsonPath(readFile("response.json"));

        given()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(readFile("request.json"))
                .when()
                .post("/producer")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("", equalTo(expectedJson.getMap("")));
    }

    private String readFile(String filename) {
        try (
                InputStream resource = getClass().getClassLoader().getResourceAsStream(filename);
                InputStreamReader isr = new InputStreamReader(resource, StandardCharsets.UTF_8);
                BufferedReader bf = new BufferedReader(isr)) {
            return bf.lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new IllegalStateException("Couldn't read from file: " + filename);
        }
    }
}
