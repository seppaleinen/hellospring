# Hello Pact

Pact is a framework for setting up contract tests.  
Contract tests are used for verifying that the integrations between   
different services are actually synchronized so that there will be no  
unexpected behaviours after deployment.

Pact is a consumer driven test framework, which means that the consumer will  
define a contract, that the producer then will have to fulfill. 

Important to differentiate between contract tests, and functional tests.  
The Contract tests, are not to be used for validating actual logic around integrations,  
as that creates too much of a complexity.


 
```bash
# Start pact-broker
docker-compose up -d

# Build project and create contracts
gradle clean build
# Publish contracts to pact broker
gradle pactPublish
# Verify consumers against a running producer with the generated contracts, as a smoketest
gradle pactVerify
```

* http://localhost:8099 - Broker UI


### Read more
* https://hub.docker.com/r/dius/pact-broker/
* https://reflectoring.io/consumer-driven-contracts-with-pact-feign-spring-data-rest/
* https://github.com/pact-foundation/pact_broker

* https://github.com/DiUS/pact-jvm/tree/master/pact-jvm-provider-gradle#publishing-pact-files-to-a-pact-broker
* https://github.com/DiUS/pact-jvm/tree/master/pact-jvm-consumer-junit
* https://github.com/DiUS/pact-jvm/tree/master/pact-jvm-provider-junit


### Questions

* How to setup contract versioning
  - For example, when the consumer has created a new contract that the producer is not yet ready for.
* Pact lenient body validation
  - When verifying the contract, the actual structure is the thing that we want to verify, not the content.