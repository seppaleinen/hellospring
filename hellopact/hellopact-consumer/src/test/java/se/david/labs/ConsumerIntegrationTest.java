package se.david.labs;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRuleMk2;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.RequestResponsePact;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(loader = SpringBootContextLoader.class)
public class ConsumerIntegrationTest {
    @LocalServerPort
    private int port;

    @Rule
    public PactProviderRuleMk2 stubProvider =
            new PactProviderRuleMk2("hellopact-producer", "localhost", 8082, this);

    @Before
    public void beforeAll() {
        RestAssured.port = port;
    }

    @Pact(state = "a collection of 2 addresses",
            provider = "hellopact-producer",
            consumer = "hellopact-consumer")
    public RequestResponsePact createAddressCollectionResourcePact(PactDslWithProvider builder) {
        return builder
                .given("a collection of 2 addresses")
                .uponReceiving("a request to the address collection resource")
                .path("/producer")
                .method(HttpMethod.POST.name())
                .willRespondWith()
                .status(HttpStatus.OK.value())
                .body(readFile("response.json"), MediaType.APPLICATION_JSON_UTF8_VALUE)
                .toPact();
    }

    @Test
    @PactVerification(fragment = "createAddressCollectionResourcePact")
    public void testConsumeEndpoint() {
        JsonPath expectedJson = new JsonPath(readFile("response.json"));

        given()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(readFile("request.json"))
                .when()
                .post("/consume")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("", equalTo(expectedJson.getMap("")));
    }


    private String readFile(String filename) {
        try (
                InputStream resource = getClass().getClassLoader().getResourceAsStream(filename);
                InputStreamReader isr = new InputStreamReader(resource, StandardCharsets.UTF_8);
                BufferedReader bf = new BufferedReader(isr)) {
            return bf.lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new IllegalStateException("Couldn't read from file: " + filename);
        }
    }
}
