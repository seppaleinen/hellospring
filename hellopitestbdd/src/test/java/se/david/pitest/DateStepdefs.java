package se.david.pitest;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Ignore;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

@Ignore
public class DateStepdefs {
	private String result;
	private DateCalculator calculator;

	@Given("^today is (.+)$")
	public void today_is(String date) {
		calculator = new DateCalculator(toDate(date));
	}

	@When("^I ask if (.+) is in the past$")
	public void I_ask_if_date_is_in_the_past(String date) {
		result = calculator.isDateInThePast(toDate(date));
	}

	@Then("^the result should be (.+)$")
	public void the_result_should_be(String expectedResult) {
		assertEquals(expectedResult, result);
	}

	private Date toDate(String date) {
		try {
			return new SimpleDateFormat("yyyy-MM-dd").parse(date);
		} catch (ParseException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}
}
