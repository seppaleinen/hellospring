package se.david.consul;

import com.pszymczyk.consul.ConsulProcess;
import com.pszymczyk.consul.ConsulStarterBuilder;
import com.pszymczyk.consul.junit.ConsulResource;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.CoreMatchers;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = Application.class
)
@ActiveProfiles("test")
public class ConsulIntegrationTest {
    @LocalServerPort
    private int port;

    @ClassRule
    public static final ConsulResource consul = new ConsulResource(8502);

    @Before
    public void before() {
        RestAssured.port = port;
        consul.reset();
    }

    @Test
    public void healthShouldBeUp() {
        given()
                .get("/health")
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .body("status", CoreMatchers.equalTo("UP"));
    }

    @Test
    public void callRest() {
        given()
                .accept(ContentType.JSON)
                .get("/ping/hello")
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .body("message", CoreMatchers.equalTo("hello"));
    }
}
