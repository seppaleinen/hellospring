package se.david.consul;

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

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = Application.class
)
@AutoConfigureWireMock(port = 9999)
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
        registerService();
        stubFor(WireMock.get(WireMock.urlEqualTo("/ping/message")).willReturn(
                aResponse()
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .withBody(String.format("{\"message\": \"%s\"}", "message")))
        );
    }

    private void registerService() {
        try {
            String path = this.getClass().getClassLoader().getResource("consul-register.json").getPath();
            String content = new String(Files.readAllBytes(Paths.get(path)));

            // Register consul-register to consul
            given().contentType(ContentType.JSON)
                    .body(content)
                    .put(String.format("http://localhost:%s/v1/catalog/register", consul.getHttpPort()))
                    .then()
                    .statusCode(HttpStatus.OK.value());

            // Mock consul-register health endpoint
            stubFor(WireMock.get(WireMock.urlEqualTo("/health")).willReturn(
                    aResponse()
                            .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
                            .withBody("{\"status\": \"UP\"}"))
            );

            String response = given().get(String.format("http://localhost:%s/v1/health/state/passing", consul.getHttpPort()))
                    .thenReturn().body().asString();

            System.out.println("RESPONSE: " + response);

            // Trigger consul check
            /**
             * await until accessable
             *
             * http://localhost:8500/v1/health/node/{Node}
             * CheckID: "serfHealth"
             * CheckID: "service:consul-register-8089" ? Maybe needed
             * Status: "passing"
             */
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
        given()
                .accept(ContentType.JSON)
                .get("/ping/hello")
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .body("message", CoreMatchers.equalTo("hello"));
    }
}
