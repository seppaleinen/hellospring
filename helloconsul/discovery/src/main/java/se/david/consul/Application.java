package se.david.consul;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@SpringBootApplication
@RestController
@EnableDiscoveryClient
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    private RestTemplate restTemplate;

    @LoadBalanced
    @Bean
    public RestTemplate create() {
        return new RestTemplate();
    }

    @GetMapping(path = "/ping/{message}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<String> ping(@PathVariable("message") @Valid @NotEmpty String message) {
        String response = restTemplate.getForObject("http://newNode/ping/message", String.class);

        System.out.println("RESPONSE: " + response);
        return ResponseEntity.ok(String.format("{\"message\": \"%s\"}", message));
    }
}
