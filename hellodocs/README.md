# Hello Docs

This project is to try out and showcase the differences between swagger and spring-rest-docs  
who are both used as ways of documenting APIs, So that new users have an easier way of integrating.

There are some differences between them, that are worth to look into.

### Pros And Cons
##### Swagger/OpenAPI
* __Pros__
  - Language agnostic
  - Simple setup
  - OpenAPI schema usable for multiple frameworks
    - *E.g: Gateways*
* __Cons__
  - Clutters up source-code
  - Hard to customize
  - Hard to add additional information

##### Spring Rest Docs
* __Pros__
  - Automatic test on documentation
    - *E.g: If you've modified req/resp without updating docs, will fail*
  - Ease of customizing/adding additional information
  - Doesn't affect source-code
* __Cons__
  - Complex setup
  - No integration to other frameworks
  - Only available for java based applications
  
### Commands 
```bash
mvn clean install
java -jar target/hellodocs.jar

gradle clean build
java -jar build/libs/*.jar
```

### Urls

* Swagger Documentation 
  - http://localhost:8080/swagger-ui.html
* Spring Rest Docs
  - http://localhost:8080/docs/index.html

## Examples

#### Swagger
![Swagger](swagger-example.png)

#### Spring Rest Docs
![Swagger](spring-rest-docs-example.png)
