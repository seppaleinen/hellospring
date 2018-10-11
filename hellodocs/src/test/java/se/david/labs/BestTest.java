package se.david.labs;

import io.restassured.RestAssured;
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

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(loader = SpringBootContextLoader.class)
public class BestTest {
    @LocalServerPort
    private int port;

    @Before
    public void before() {
        RestAssured.port = port;
    }

    @Test
    public void testSwaggerEndpoint() {
        RequestResponse response = given().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(new RequestResponse("hiya"))
                .when().get("/swagger")
                .then().statusCode(HttpStatus.OK.value())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
                .extract().body().as(RequestResponse.class);

        assertEquals("hiya", response.getMessage());
    }

    @Test
    public void testSpringRestDocsEndpoint() {
        RequestResponse response = given().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(new RequestResponse("hiya"))
                .when().get("/spring-rest-docs")
                .then().statusCode(HttpStatus.OK.value())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
                .extract().body().as(RequestResponse.class);

        assertEquals("hiya", response.getMessage());
    }
}
