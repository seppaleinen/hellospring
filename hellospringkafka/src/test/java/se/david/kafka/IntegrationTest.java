package se.david.kafka;

import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.kafka.test.rule.KafkaEmbedded;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import se.david.kafka.consumer.Receiver;
import se.david.kafka.producer.Sender;

import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@ActiveProfiles("test")
public class IntegrationTest {
    @LocalServerPort
    private int port;
    @SpyBean
    private Sender sender;
    @SpyBean
    private Receiver receiver;
    @Value("${topic.receiver}")
    private String topic;

    @ClassRule
    public static KafkaEmbedded embeddedKafka = new KafkaEmbedded(1, true, "receiver.t");

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

        verify(sender).send(topic, "message");

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

