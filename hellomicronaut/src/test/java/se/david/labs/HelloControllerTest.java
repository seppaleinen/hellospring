package se.david.labs;

import io.micronaut.context.ApplicationContext;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.runtime.server.EmbeddedServer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class HelloControllerTest {

    private static EmbeddedServer server;
    private static HttpClient client;

    @BeforeClass
    public static void setupServer() {
        server = ApplicationContext.run(EmbeddedServer.class);
        client = HttpClient.create(server.getURL());
    }

    @AfterClass
    public static void stopServer() {
        if (server != null) {
            server.stop();
        }
        if (client != null) {
            client.stop();
        }
    }

    @Test
    public void testCreate() {
        HttpRequest<Object> request = HttpRequest.POST("/hello/name", Void.class);
        DbEntity body = client.toBlocking().retrieve(request, DbEntity.class);
        assertNotNull(body);
        assertEquals("name", body.getName());
    }

    @Test
    public void testFindAll() {
        HttpStatus savedStatus = client.toBlocking()
                .exchange(HttpRequest.POST("/hello/name2", Void.class), DbEntity.class)
                .getStatus();
        assertEquals(HttpStatus.OK, savedStatus);

        String body = client.toBlocking().retrieve(HttpRequest.GET("/hello"), String.class);
        assertNotNull(body);
        assertTrue(body.contains("name2"));
    }

    @Test
    public void healthEndpoint() {
        HttpRequest<Object> request = HttpRequest.GET("/health");
        String response = client.toBlocking().retrieve(request);
        assertEquals("{\"status\":\"UP\"}", response);
    }
}
