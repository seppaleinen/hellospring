package se.david.labs.helidon;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import io.helidon.config.Config;
import io.helidon.health.HealthSupport;
import io.helidon.health.checks.HealthChecks;
import io.helidon.media.jsonp.server.JsonSupport;
import io.helidon.metrics.MetricsSupport;
import io.helidon.webserver.Routing;
import io.helidon.webserver.ServerConfiguration;
import io.helidon.webserver.WebServer;

public final class Main {
    private static final Logger log = Logger.getLogger(Main.class.getName());

    private Main() { }

    public static void main(final String[] args) throws IOException {
        startServer();
    }

    static WebServer startServer() throws IOException {

        setupLogging();

        Config config = Config.create();

        ServerConfiguration serverConfig =
                ServerConfiguration.create(config.get("server"));

        WebServer server = WebServer.create(serverConfig, createRouting(config));

        server.start()
            .thenAccept(ws -> {
                log.info("Web server is up at http://localhost:" + ws.port());
                ws.whenShutdown().thenRun(()
                    -> log.info("Web server is down."));
                })
            .exceptionally(t -> {
                log.severe("Startup failed: " + t.getMessage());
                t.printStackTrace(System.err);
                return null;
            });

        return server;
    }

    private static Routing createRouting(Config config) {
        MetricsSupport metrics = MetricsSupport.create();
        GreetService greetService = new GreetService(config);
        HealthSupport health = HealthSupport.builder()
                .addLiveness(HealthChecks.healthChecks())   // Adds a convenient set of checks
                .build();

        return Routing.builder()
                .register(JsonSupport.create())
                .register(health)                   // Health at "/health"
                .register(metrics)                  // Metrics at "/metrics"
                .register("/greet", greetService)
                .build();
    }

    private static void setupLogging() throws IOException {
        try (InputStream is = Main.class.getResourceAsStream("/logging.properties")) {
            LogManager.getLogManager().readConfiguration(is);
        }
    }

}
