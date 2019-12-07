package se.david.neo4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ApiController {
    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MovieService movieService;

    @GetMapping(path = "/neo4j",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<String> triggerKafka() {
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/graph")
    public Map<String, Object> graph(@RequestParam(value = "limit",required = false, defaultValue = "100") Integer limit) {
        return movieService.graph(limit);
    }

}
