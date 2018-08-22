package se.david.kafka;

import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import se.david.kafka.consumer.Receiver;
import se.david.kafka.producer.Sender;

import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.CoreMatchers.is;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(loader = SpringBootContextLoader.class)
@ActiveProfiles("test")
@EmbeddedKafka(topics = "receiver.t", controlledShutdown = true, count = 1)
@DirtiesContext
public class IntegrationTest {
    @LocalServerPort
    private int port;
    @Autowired
    private Sender sender;
    @Autowired
    private Receiver receiver;
    @Value("${topic.receiver}")
    private String topic;

    @Before
    public void setup() {
        RestAssured.port = port;
    }

    @Test
    public void test() {
        given().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).
                when().get("/kafka/message")
                .then()
                .statusCode(HttpStatus.OK.value());

        await().atMost(500, TimeUnit.MILLISECONDS)
                .until(receiver::getReceivedMessage, is("message"));
    }

    @Test
    public void testReceive() {
        String message = "message";

        sender.send(topic, message);

        await()
                .pollDelay(50, TimeUnit.MILLISECONDS)
                .pollInterval(1, TimeUnit.MILLISECONDS)
                .atMost(100, TimeUnit.MILLISECONDS)
                .until(receiver::getReceivedMessage, is(message));
    }
}
