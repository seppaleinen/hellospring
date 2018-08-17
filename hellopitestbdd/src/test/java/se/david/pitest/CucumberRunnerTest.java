package se.david.pitest;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        format = "pretty",
        glue = "se.david.pitest",
        features = {"classpath:features/date_calculator.feature", "classpath:features/boottest.feature"})
public class CucumberRunnerTest {
}
