package se.david.task;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.CoreMatchers;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = Application.class
)
@ContextConfiguration(loader = SpringBootContextLoader.class)
@Ignore("Let CucumberRunnerTest run test")
public class SpringBootdefs {
    @LocalServerPort
    private int port;
    private Method method ;
    private ValidatableResponse response;

    @Before
    public void before() {
        RestAssured.port = port;
    }

    @Given("^request method is (.*)$")
    public void requestMethodIs(String method) {
        this.method = Method.valueOf(method);
    }

    @When("^calling endpoint (.*)$")
    public void callRest(String endpoint) {
        response = given()
                .contentType(ContentType.JSON)
                .request(method, endpoint)
                .then();
    }

    @Then("^response status should be (\\d*)$")
    public void checkResult(int expectedStatusCode) {
        response.statusCode(expectedStatusCode);
    }

    @Then("^result should contain \"(.*)\"$")
    public void checkResultBody(String expectedBody) {
        response.body(CoreMatchers.containsString(expectedBody));
    }
}
