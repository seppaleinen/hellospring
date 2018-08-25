package se.david.labs.producer;

import io.restassured.path.json.JsonPath;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class IntegrationTest extends AbstractSpringRunner {
    private static final String IO_DATA = "{\"data\":\"hello\"}";

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


