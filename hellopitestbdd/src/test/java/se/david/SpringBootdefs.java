package se.david;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BootApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(loader = SpringBootContextLoader.class)
@Ignore
public class SpringBootdefs {
    @LocalServerPort
    private int port;
    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;

    private String response;

    @Given("^all is well$")
    public void givenAllIsWell() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @When("^calling rest interface$")
    public void callRest() throws Exception {
        response = mvc.perform(get("/message/hello"))
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Then("^result should be ok$")
    public void checkResult() {
        assertEquals("hello", response);
    }
}
