package se.david.labs.consumer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
class Controller {

    private static final RestTemplate REST_TEMPLATE = new RestTemplateBuilder().build();

    @Value("${producer.endpoint}")
    private String producerEndpoint;

    @PostMapping(
            value = "/consumer2",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<RequestResponseDto> call(@RequestBody RequestResponseDto request) {
        HttpEntity<RequestResponseDto> httpEntity = new HttpEntity<>(request, createHeaders());
        ResponseEntity<RequestResponseDto> response = REST_TEMPLATE.postForEntity(
                producerEndpoint + "/producer", httpEntity, RequestResponseDto.class);
        if (response.getBody() == null) {
            throw new IllegalStateException("No body returned from producer");
        }
        return ResponseEntity.ok(response.getBody());
    }
    @GetMapping("/consumer2/getfoo")
    String getFoo() {
        return REST_TEMPLATE.getForObject(producerEndpoint + "/producer/foo", String.class);
    }
    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_UTF8_VALUE);
        return headers;
    }
}
