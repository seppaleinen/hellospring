package se.david;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        format = "pretty",
        glue = "se/david",
        features = {"classpath:features/date_calculator.feature", "classpath:features/boottest.feature"})
public class CucumberRunnerTest {
}
