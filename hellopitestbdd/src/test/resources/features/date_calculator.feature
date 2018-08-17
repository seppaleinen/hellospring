Feature: Dates with different date formats
  This feature shows you can have different date formats, as long as you annotate the
  corresponding step definition method accordingly.

  Scenario Outline: determine dates
    Given today is 2011-01-20
    When I ask if <date> is in the past
    Then the result should be <result>
    Examples:
      | date       | result |
      | 2011-01-19 | yes    |
      | 2011-01-21 | no     |
