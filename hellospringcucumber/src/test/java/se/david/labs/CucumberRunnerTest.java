package se.david.labs;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        format = "pretty",
        glue = "se/david/labs",
        features = {"classpath:features/rest-interface.feature"})
public class CucumberRunnerTest {
}
