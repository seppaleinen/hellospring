package se.david.labs.producer;

import au.com.dius.pact.provider.junit.IgnoreNoPactsToVerify;
import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactBroker;
import au.com.dius.pact.provider.junit.target.Target;
import au.com.dius.pact.provider.junit.target.TestTarget;
import au.com.dius.pact.provider.spring.SpringRestPactRunner;
import au.com.dius.pact.provider.spring.target.SpringBootHttpTarget;
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

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(SpringRestPactRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Provider("hellopact-producer")
@PactBroker(host = "${pactbroker.hostname:localhost}", port = "${pactbroker.port:8099}")
@IgnoreNoPactsToVerify
public class PactTest {
    private static final String IO_DATA = "{\"data\":\"hello\"}";

    @LocalServerPort
    private int port;

    @TestTarget
    public final Target target = new SpringBootHttpTarget();

    @Before
    public void before() {
        RestAssured.port = port;
    }

    @State(value = "simple call to producer")
    public void simpleCallToProducer() {
        JsonPath expectedJson = new JsonPath(IO_DATA);

        given().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(IO_DATA)
                .when()
                .post("/producer")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("", equalTo(expectedJson.getMap(""))); // This is to easier validate json response
    }

    // Is ignored somehow
    @Test
    public void testProducerEndpoint() {
        JsonPath expectedJson = new JsonPath(IO_DATA);

        given()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(IO_DATA)
                .when()
                .post("/producer")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("", equalTo(expectedJson.getMap(""))); // This is to easier validate json response
    }
}


