package se.david.pitest;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
		glue = "se.david.pitest",
		features = { "classpath:features/date_calculator.feature", "classpath:features/boottest.feature" })
public class CucumberRunnerTest {
}
