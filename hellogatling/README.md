# Gatling

This is for testing out gatling.

Gatling is a tool for setting up performance tests.

```bash
# To start spring-boot and run gatling against it
# Generated reports will be under build/reports/gatling/...
gradle gatlingRun

# To run regular tests
gradle clean build

# To run tests and gatling
mvn clean install -PgatlingRun

```

### TODO
* Verify Maven setup