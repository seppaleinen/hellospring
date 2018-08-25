package se.david.labs.consumer;

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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@ContextConfiguration(loader = SpringBootContextLoader.class)
public class ConsumerIntegrationTest {
    private static final String IO_DATA = "{\"data\":\"hello\"}";
    public static final String FOO_RESPONSE = "{\n" +
            "  \"foo\": \"foo\",\n" +
            "  \"bar\": \"bar\"\n" +
            "}";
    @LocalServerPort
    private int port;

    @Rule
    public PactProviderRuleMk2 stubProvider =
            new PactProviderRuleMk2("hellopact-producer", "localhost", 9997, this);

    @Before
    public void beforeAll() {
        RestAssured.port = port;
    }

    @Pact(provider = "hellopact-producer", consumer = "hellopact-consumer2")
    public RequestResponsePact simpleCallToProducer(PactDslWithProvider builder) {
        return builder
                .given("simple call to producer")
                .uponReceiving("A request to producer endpoint")
                .headers(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
                .headers(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(IO_DATA)
                .path("/producer")
                .method(HttpMethod.POST.name())
                .willRespondWith()
                .status(HttpStatus.OK.value())
                .body(IO_DATA, MediaType.APPLICATION_JSON_UTF8_VALUE)
                .toPact();
    }

    @Pact(provider = "hellopact-producer", consumer = "hellopact-consumer2")
    public RequestResponsePact simpleCallToProducerForFoo(PactDslWithProvider builder) {
        return builder
                .given("simple call to producer to get foo document")
                .uponReceiving("A request to producer foo endpoint")
                .path("/producer/foo")
                .method(HttpMethod.GET.name())
                .willRespondWith()
                .status(HttpStatus.OK.value())
                .body(FOO_RESPONSE, MediaType.APPLICATION_JSON_UTF8_VALUE)
                .toPact();
    }

    @Test
    @PactVerification(fragment = "simpleCallToProducerForFoo")
    public void testConsumeFooEndpoint() {
        JsonPath expectedJson = new JsonPath(FOO_RESPONSE);

        given()
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_UTF8_VALUE)
                .when()
                .get("/consumer2/getfoo")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("", equalTo(expectedJson.getMap(""))); // This is to easier validate json response
    }
    @Test
    @PactVerification(fragment = "simpleCallToProducer")
    public void testConsumeEndpoint() {
        JsonPath expectedJson = new JsonPath(IO_DATA);

        given()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(IO_DATA)
                .when()
                .post("/consumer2")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("", equalTo(expectedJson.getMap(""))); // This is to easier validate json response
    }
}
