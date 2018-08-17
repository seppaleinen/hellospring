package se.david.labs.producer;

import au.com.dius.pact.provider.junit.IgnoreNoPactsToVerify;
import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactBroker;
import au.com.dius.pact.provider.junit.target.Target;
import au.com.dius.pact.provider.junit.target.TestTarget;
import au.com.dius.pact.provider.spring.SpringRestPactRunner;
import au.com.dius.pact.provider.spring.target.SpringBootHttpTarget;
import io.restassured.path.json.JsonPath;
import org.junit.runner.RunWith;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(SpringRestPactRunner.class)
@Provider("hellopact-producer")
@IgnoreNoPactsToVerify
@PactBroker(host = "${pactbroker.hostname:localhost}", port = "${pactbroker.port:8099}")
public class PactTest extends AbstractSpringRunner {
    private static final String IO_DATA = "{\"data\":\"hello\"}";
    private static final String FOO_RESPONSE = "{\"foo\":\"foo\",\"bar\":\"bar\",\"baz\":\"baz\"}";

    @TestTarget
    public final Target target = new SpringBootHttpTarget();

    @State(value = "simple call to producer")
    public void verifyConsumerPact() {
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
    @State(value = "simple call to producer to get foo document")
    public void verifyConsumerFooPact() {
        JsonPath expectedJson = new JsonPath(FOO_RESPONSE);

        given()
                .when()
                .get("/producer/foo")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("", equalTo(expectedJson.getMap(""))); // This is to easier validate json response
    }
}


