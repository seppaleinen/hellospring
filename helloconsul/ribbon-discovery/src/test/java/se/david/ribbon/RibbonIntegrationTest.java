package se.david.ribbon;

import com.github.tomakehurst.wiremock.client.WireMock;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = Application.class
)
@AutoConfigureWireMock(port = 9999)
@ActiveProfiles("test")
public class RibbonIntegrationTest {
    @LocalServerPort
    private int port;

    @Before
    public void before() {
        RestAssured.port = port;
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
        stubFor(WireMock.get(WireMock.urlMatching("/ping/.*")).willReturn(
                aResponse()
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .withBody(String.format("{\"message\": \"%s\"}", "message")))
        );

        given()
                .accept(ContentType.JSON)
                .get("/ping/hello")
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .body("message", CoreMatchers.equalTo("message"));

        verify(WireMock.getRequestedFor(WireMock.urlMatching("/ping/hello")));
    }
}
