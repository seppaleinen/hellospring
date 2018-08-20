package se.david.consul;

import com.pszymczyk.consul.junit.ConsulResource;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.ClassRule;
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
public class ConsulIntegrationTest {
    @LocalServerPort
    private int port;

    @ClassRule
    public static final ConsulResource consul = new ConsulResource();

    @Before
    public void before() {
        RestAssured.port = port;
        consul.reset();
    }

    @Test
    public void callRest() {
        given()
                .get("/ping/hello")
                .then()
                .statusCode(HttpStatus.OK.value());
    }
}
