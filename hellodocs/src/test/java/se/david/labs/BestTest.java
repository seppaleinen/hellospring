package se.david.labs;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.ManualRestDocumentation;
import org.springframework.restdocs.restassured3.RestAssuredRestDocumentation;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(loader = SpringBootContextLoader.class)
public class BestTest {
    @LocalServerPort
    private int port;
    private ManualRestDocumentation restDocumentation = new ManualRestDocumentation("build/generated-snippets");
    private RequestSpecification documentationSpec;

    @Before
    public void before() {
        RestAssured.port = port;
        documentationSpec = new RequestSpecBuilder()
                .addFilter(RestAssuredRestDocumentation.documentationConfiguration(restDocumentation))
                .build();
    }

    @After
    public void tearDown() {
        this.restDocumentation.afterTest();
    }

    @Test
    public void testSwaggerEndpoint() {
        RequestResponse response = given().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(new RequestResponse("hiya"))
                .when().get("/swagger/swag")
                .then().statusCode(HttpStatus.OK.value())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
                .extract().body().as(RequestResponse.class);

        assertEquals("hiya", response.getMessage());
    }

    @Test
    public void testSpringRestDocsEndpoint() {
        this.restDocumentation.beforeTest(getClass(), "spring-rest-docs");

        given(documentationSpec)
                .filter(RestAssuredRestDocumentation.document("spring-rest-docs",
                        requestFields(
                                fieldWithPath("message").description("Message from user")),
                        responseFields(
                                fieldWithPath("message").description("Message from request being reused in response"))))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(new RequestResponse("hiya"))
                .when().get("/spring-rest-docs/spring")
                .then().statusCode(HttpStatus.OK.value())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body("message", CoreMatchers.equalTo("hiya"));
    }
}
