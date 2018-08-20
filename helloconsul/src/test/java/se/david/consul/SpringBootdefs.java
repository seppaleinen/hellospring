package se.david.consul;

import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = Application.class
)
public class SpringBootdefs {
    @LocalServerPort
    private int port;

    @Before
    public void before() {
        RestAssured.port = port;
    }

    @Test
    public void callRest() {
        given()
                .get("/ping/hello")
                .then()
                .statusCode(HttpStatus.OK.value());
    }
}
