package se.david.labs.consumer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
            value = "/consume",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<ResponseDto> call(@RequestBody RequestDto request) {
        HttpEntity<RequestDto> httpEntity = new HttpEntity<>(request, createHeaders());
        ResponseEntity<ResponseDto> response = REST_TEMPLATE.postForEntity(producerEndpoint, httpEntity, ResponseDto.class);
        return ResponseEntity.ok(response.getBody());
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_UTF8_VALUE);
        return headers;
    }

}
