package se.david.labs.helidon;

import io.helidon.config.Config;
import io.helidon.webserver.Routing;
import io.helidon.webserver.ServerRequest;
import io.helidon.webserver.ServerResponse;
import io.helidon.webserver.Service;

import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicReference;

public class GreetService implements Service {

    private final AtomicReference<String> greeting = new AtomicReference<>();

    private static final JsonBuilderFactory JSON = Json.createBuilderFactory(Collections.emptyMap());

    GreetService(Config config) {
        greeting.set(config.get("app.greeting").asString().orElse("Ciao"));
    }

    @Override
    public void update(Routing.Rules rules) {
        rules.get("/", this::getDefaultMessageHandler)
                .get("/{name}", this::getMessageHandler);
    }

    private void getDefaultMessageHandler(ServerRequest request,
                                          ServerResponse response) {
        sendResponse(response, "World");
    }

    private void getMessageHandler(ServerRequest request,
                                   ServerResponse response) {
        String name = request.path().param("name");
        sendResponse(response, name);
    }

    private void sendResponse(ServerResponse response, String name) {
        String msg = String.format("%s %s!", greeting.get(), name);

        JsonObject returnObject = JSON.createObjectBuilder()
                .add("message", msg)
                .build();
        response.send(returnObject);
    }
}
