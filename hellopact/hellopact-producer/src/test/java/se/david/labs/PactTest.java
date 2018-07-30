package se.david.labs;

import au.com.dius.pact.provider.junit.PactRunner;
import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.loader.PactBroker;
import au.com.dius.pact.provider.junit.target.HttpTarget;
import au.com.dius.pact.provider.junit.target.Target;
import au.com.dius.pact.provider.junit.target.TestTarget;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@RunWith(PactRunner.class)
@Provider("hellopact-producer")
@PactBroker(host = "${pactbroker.hostname:localhost}", port = "${pactbroker.port:80}")
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(loader = SpringBootContextLoader.class)
public class PactTest {
//    @ClassRule
//    public static SpringBootStarter appStarter = SpringBootStarter.builder()
//            .withApplicationClass(DemoApplication.class)
//            .withArgument("--spring.config.location=classpath:/application-pact.properties")
//            .withDatabaseState("address-collection", "/initial-schema.sql", "/address-collection.sql")
//            .build();

    @TestTarget // Annotation denotes Target that will be used for tests
    public final Target target = new HttpTarget(8082); // Out-of-the-box implementation of Target (for more information take a look at Test Target section)
}
