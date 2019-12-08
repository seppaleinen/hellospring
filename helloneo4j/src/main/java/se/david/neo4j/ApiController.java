package se.david.neo4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.david.neo4j.entity.Movie;

import java.util.List;
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

    @GetMapping(path = "/movies/search/findByTitleLike", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Movie> findByTitleLike(@RequestParam(value = "title") String title) {
        return movieService.findByTitleLike(String.format("*%s*", title));
    }

    @GetMapping(path = "/movies/search/findByTitle", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Movie> findByTitle(@RequestParam(value = "title") String title) {
        return movieService.findByTitle(title)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @GetMapping(path = "/graph", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public D3Format graph(@RequestParam(value = "limit", required = false, defaultValue = "100") Integer limit) {
        return movieService.graph(limit);
    }

}
