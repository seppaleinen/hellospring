package se.david.labs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .forCodeGeneration(true)
                .apiInfo(new ApiInfoBuilder()
                        .title("Swagger Controller")
                        .description("This might actually be the best controller ever written since the dawn of mankind")
                        .build())
                .select()
                // Only generate docs for RestController class
                .apis(RequestHandlerSelectors.basePackage("se.david.labs.swagger"))
                .build();
    }

}
