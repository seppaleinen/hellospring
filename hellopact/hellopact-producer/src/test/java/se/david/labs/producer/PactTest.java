package se.david.labs.producer;

import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactBroker;
import au.com.dius.pact.provider.junit.target.HttpTarget;
import au.com.dius.pact.provider.junit.target.Target;
import au.com.dius.pact.provider.junit.target.TestTarget;
import au.com.dius.pact.provider.spring.SpringRestPactRunner;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(SpringRestPactRunner.class)
@SpringBootTest(classes=Application.class,webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Provider("hellopact-producer")
@PactBroker(host = "${pactbroker.hostname:localhost}", port = "${pactbroker.port:8099}")
public class PactTest {
    @LocalServerPort
    private int port;

    @TestTarget
    public final Target target = new HttpTarget(8082);

    @Before
    public void before() {
        RestAssured.port = 8082;
    }

    @State(value="a collection of 2 addresses")
    public void createInventoryState() {
        JsonPath expectedJson = new JsonPath(readFile("response.json"));

        given().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(readFile("request.json"))
                .when()
                .post("/producer")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("", equalTo(expectedJson.getMap("")));
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
