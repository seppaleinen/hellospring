# hello-consul

Testing out consul

What I want to do:
* Register one service to consul
* Call that service through ribbon, via consul service-name from another service
* Automate all steps

### Using
* Spring-boot 2
* Embedded consul

### Commands 
```bash
docker-compose up -d

gradle clean build

mvn clean install
```

### Endpoints

* Consul - http://localhost:8500 

#### Documentation

* https://cloud.spring.io/spring-cloud-static/spring-cloud-consul/2.0.0.RELEASE/multi/multi_spring-cloud-consul-discovery.html
* https://github.com/pszymczyk/embedded-consul