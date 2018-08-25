package se.david.labs.producer;

import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(loader = SpringBootContextLoader.class)
@Ignore("Only used by other classes")
public abstract class AbstractSpringRunner {
    @LocalServerPort
    private int port;

    @Before
    public void before() {
        RestAssured.port = port;
    }
}


