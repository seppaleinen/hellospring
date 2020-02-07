Feature: Test spring boot integrations

  Scenario: Do the stuff
    When calling /message/hello
    Then result should be hello
