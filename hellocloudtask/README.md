# Spring Cloud Task

Spring Cloud Task is supposed to be a nice way to setup processing engines.

It can lie dormant until cron-expression/http request triggers it.

And should have default handling of event statuses in db



### Commands 
```bash
gradle clean build

mvn clean install
```

### Todo

* Batch DB Integration
* Make server shut down after run
* Access JobConfiguration through API

### Docs

* https://docs.spring.io/spring-cloud-task/docs/2.0.0.RELEASE/reference/htmlsingle/#getting-started
* https://www.baeldung.com/spring-cloud-task