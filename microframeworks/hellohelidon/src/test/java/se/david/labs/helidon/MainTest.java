package se.david.labs.helidon;

import io.helidon.webserver.WebServer;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.awaitility.Awaitility;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;

public class MainTest {

    private static WebServer webServer;

    @BeforeAll
    public static void startTheServer() throws Exception {
        webServer = Main.startServer();

        Awaitility.waitAtMost(2, TimeUnit.SECONDS)
                .until(() -> webServer.isRunning());

        RestAssured.port = webServer.port();
    }

    @AfterAll
    public static void stopServer() throws Exception {
        if (webServer != null) {
            webServer.shutdown()
                    .toCompletableFuture()
                    .get(10, TimeUnit.SECONDS);
        }
    }

    @Test
    public void defaultMessage() {
        given().when().get("/greet")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("message", Matchers.equalTo("Hello World!"));
    }

    @Test
    public void parameterizedMessage() {
        given().when().get("/greet/Joe")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("message", Matchers.equalTo("Hello Joe!"));
    }

    @Test
    public void health() {
        given().contentType(ContentType.JSON)
                .when()
                .get("/health")
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void metrics() {
        given().contentType(ContentType.JSON)
                .when()
                .get("/metrics")
                .then()
                .statusCode(HttpStatus.SC_OK);
    }
}
