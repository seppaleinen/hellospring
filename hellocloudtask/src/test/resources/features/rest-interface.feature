Feature: Test spring boot integrations

  Scenario: Correct request method to ping
    Given request method is GET
    When calling endpoint /ping/dinky
    Then response status should be 200
    Then result should contain "PONG: dinky"

  Scenario: Wrong request method
    Given request method is POST
    When calling endpoint /ping/dinky
    Then response status should be 405
    Then result should contain ""error":"Method Not Allowed""
