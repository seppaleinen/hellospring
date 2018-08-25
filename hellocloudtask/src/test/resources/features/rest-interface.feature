Feature: Test spring boot integrations

  Scenario: Health Endpoint Should Be Up
    Given request method is GET
    When calling endpoint /health
    Then response status should be 200
    Then result should contain "{"status": "UP"}"
