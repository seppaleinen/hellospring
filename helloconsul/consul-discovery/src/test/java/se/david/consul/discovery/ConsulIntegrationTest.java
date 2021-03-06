package se.david.consul.discovery;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.pszymczyk.consul.junit.ConsulResource;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.ClassRule;
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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static io.restassured.RestAssured.given;
import static org.awaitility.Awaitility.await;
import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = Application.class
)
@AutoConfigureWireMock(port = 9998)
@ActiveProfiles("test")
public class ConsulIntegrationTest {
    @LocalServerPort
    private int port;

    @ClassRule
    public static final ConsulResource CONSUL = new ConsulResource(8502);

    private boolean initialized = false;

    @Before
    public void before() {
        RestAssured.port = port;
        registerService();
    }

    private void registerService() {
        if(initialized) {
            return;
        }
        try {
            String path = this.getClass().getClassLoader().getResource("consul-register.json").getPath();
            String content = new String(Files.readAllBytes(Paths.get(path)));

            // Register CONSUL-register to CONSUL
            given().contentType(ContentType.JSON)
                    .body(content)
                    .put(String.format("http://localhost:%s/v1/agent/service/register", CONSUL.getHttpPort()))
                    .then()
                    .statusCode(HttpStatus.OK.value());

            // Mock CONSUL-register health endpoint
            stubFor(WireMock.get(WireMock.urlEqualTo("/health")).willReturn(
                    aResponse()
                            .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
                            .withBody("{\"status\": \"UP\"}"))
            );

            String expected = "HTTP GET http://localhost:9998/health: 200 OK";
            await().pollInterval(50L, TimeUnit.MILLISECONDS)
                    .atMost(5L, TimeUnit.SECONDS).until(() -> given()
                    .get(String.format("http://localhost:%s/v1/health/state/passing", CONSUL.getHttpPort()))
                    .thenReturn()
                    .body().asString().contains(expected));

            initialized = true;
        } catch (IOException e) {
            e.printStackTrace();
            fail("No can do: " + e.getMessage());
        }
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
